package controlador;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import modelo.Cuenta;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class VisualizaCuentasController implements Initializable {

    public JFXTextField nacionalidad;
    List<Cuenta> listaCuentas = new ArrayList<>();


    protected int contadorCuentas = 0;
    protected boolean identBotNueva = false;

    @FXML
    protected Label etiExist;
    @FXML
    protected Label etiNueva;
    @FXML
    protected Label numExist;
    @FXML
    public Label num50000;
    @FXML
    protected TextField numero;
    @FXML
    protected TextField titular;
    @FXML
    protected TextField fecha;
    @FXML
    protected TextField saldo;
    @FXML
    protected Button siguiente;
    @FXML
    protected Button atras;
    @FXML
    protected Button nueva;
    @FXML
    protected Button aceptar;
    @FXML
    protected Button cancelar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializa el metodo
    }


    public Integer contarNumeroCuentasTotales() throws SQLException {
        Funcionalidad funcionalidad = new Funcionalidad() ;
        ResultSet sql = funcionalidad.consulta("SELECT * FROM CUENTAS");
        Integer contador = 0;
            while (sql.next()){
                Integer numCuenta = sql.getInt(1);
                String titularCuenta = sql.getString(2);
                String fechaAperturaCuenta = sql.getString(3);
                Double saldo = sql.getDouble(4);
                String nacionalidad = sql.getString(5);
                Cuenta c = new Cuenta(numCuenta,titularCuenta,fechaAperturaCuenta,saldo,nacionalidad);
                this.listaCuentas.add(c);

                contador++;
            }

        return contador;
    }

    public Integer cuentasSaldoMayor50000() throws SQLException {
        Funcionalidad funcionalidad = new Funcionalidad() ;
        ResultSet sql = funcionalidad.consulta("SELECT * FROM CUENTAS");
        Integer cont = 0;
        while (sql.next()){
                Integer numCuenta = sql.getInt(1);
                String titularCuenta = sql.getString(2);
                String fechaAperturaCuenta = sql.getString(3);
                Double saldo = sql.getDouble(4);
                String nacionalidad = sql.getString(5);
                Cuenta c = new Cuenta(numCuenta,titularCuenta,fechaAperturaCuenta,saldo,nacionalidad);
                this.listaCuentas.add(c);

            }

            for(Cuenta cuenta: listaCuentas){
                if (cuenta.getSaldo()>=50000){
                    cont++;
                }
            }

        //return listaCuentas.size();
        return cont;
    }

    public void iniciaLista(List<Cuenta> listaCuentas){
        this.listaCuentas = listaCuentas;
        contadorMayor50000();
        actualizarDatos();
        menuContextual();
    }

    public void accionSiguiente() {
        if (contadorCuentas == (listaCuentas.size()) - 2) {
            identBotNueva = true;
            siguiente.setVisible(false);
            siguiente.setDisable(true);
            nueva.setVisible(true);
            nueva.setDisable(false);
        }

        if (contadorCuentas < (listaCuentas.size()) - 1) {
            contadorCuentas++;
        }

        if (contadorCuentas > 0) {
            atras.setVisible(true);
            atras.setDisable(false);
        }

        actualizarDatos();
    }

    public void accionAtras() {
        if (identBotNueva) {
            siguiente.setVisible(true);
            siguiente.setDisable(false);
            nueva.setVisible(false);
            nueva.setDisable(true);
            identBotNueva = false;
        }

        if (contadorCuentas > 0) {
            contadorCuentas--;
        }

        if (contadorCuentas == 0) {
            atras.setVisible(false);
            atras.setDisable(true);
        }

        actualizarDatos();
    }

    public void accionNueva() {
        etiExist.setVisible(false);
        etiNueva.setVisible(true);

        numero.clear();
        titular.clear();
        fecha.clear();
        saldo.clear();
        nacionalidad.clear();

        numero.setEditable(true);
        titular.setEditable(true);
        fecha.setEditable(true);
        saldo.setEditable(true);
        nacionalidad.setEditable(true);

        nueva.setVisible(false);
        nueva.setDisable(true);
        atras.setVisible(false);
        atras.setDisable(true);

        aceptar.setVisible(true);
        aceptar.setDisable(false);
        cancelar.setVisible(true);
        cancelar.setDisable(false);
    }

    public void accionAceptar() {
        String redField = "-fx-background-color: #ed6d6d";

        numero.setStyle("");
        titular.setStyle("");
        fecha.setStyle("");
        saldo.setStyle("");
        nacionalidad.setStyle("");

        boolean isValid = true; //Boolean para indicar si el formato es correcto o no.

        if (!numero.getText().isEmpty() && !isNumeric(numero.getText())) {

            for (Cuenta cuenta : listaCuentas) {
                if (Integer.parseInt(numero.getText()) == cuenta.getNumeroCuenta()) {
                    isValid = false;
                    numero.setStyle(redField);
                    numero.setPromptText("El número de cuenta ya existe");
                }
            }
        } else {
            isValid = false;
            numero.setStyle(redField);

            numero.setPromptText("No es un número");
        }


        if (titulardigit(titular.getText())) {
            isValid = false;
            titular.setPromptText("el titular contiene número");
            titular.setStyle(redField);

        }

        if(titular.getText().isEmpty()) { // Se ejecuta si el campo está "Titular" vacío.
            isValid = false;
            titular.setPromptText("no puede estar vacío");
            titular.setStyle(redField);
        }

        fecha.setPromptText("yyyy/MM/dd");
        if (!validDate(fecha.getText())) {
            isValid = false;
            fecha.setStyle(redField);
            fecha.setPromptText("fecha incorrecta");

        }

        if(fecha.getText().isEmpty()) { // Se ejecuta si el campo está "Fecha" vacío.
            isValid = false;
            fecha.setPromptText("No puede estar vacío");
            fecha.setStyle(redField);
        }

        if (isNumeric(saldo.getText())) {
            isValid = false;
            saldo.setPromptText("Debe ser un numero");
            saldo.setStyle(redField);
        }

        if(saldo.getText().isEmpty()) {
            isValid = false;
            saldo.setPromptText("No puede estar vacío");
            saldo.setStyle(redField);
        }

        if (nacionalidad.getText().isEmpty()){
            isValid=false;
            nacionalidad.setPromptText("No puede estar vacío");
            nacionalidad.setStyle(redField);
        }

        if (isValid) {
            Integer numeroCuenta = Integer.parseInt(numero.getText());
            String titularCuenta = titular.getText();
            String fechaCuenta = fecha.getText();
            Double saldoCuenta = Double.parseDouble(saldo.getText());
            String nacionalidadCuenta = nacionalidad.getText();

            //Cuenta newCuenta = new Cuenta(Integer.parseInt(numero.getText()), titular.getText(), fecha.getText(), Double.parseDouble(saldo.getText()));
            Cuenta newCuenta = new Cuenta (numeroCuenta,titularCuenta,fechaCuenta,saldoCuenta,nacionalidadCuenta);

            try{
                Class.forName("org.mariadb.jdbc.Driver");
                String urlCon = "jdbc:mariadb://localhost:3306/DDI";
                Connection conexBd = DriverManager.getConnection(urlCon, "root", "root");
                Statement encapsulaCons = conexBd.createStatement();
                int filActualizadas = encapsulaCons.executeUpdate("INSERT INTO CUENTAS (NumeroCuenta, Titular, FechaApertura, Saldo, Nacionalidad) VALUES('" + numeroCuenta + "', '" + titularCuenta + "', '" + fechaCuenta + "', '" + saldoCuenta + "', '" + nacionalidadCuenta + "')");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            listaCuentas.add(newCuenta);
            contadorCuentas++;



            etiNueva.setVisible(false);
            etiExist.setVisible(true);

            numero.setEditable(false);
            titular.setEditable(false);
            fecha.setEditable(false);
            saldo.setEditable(false);
            nacionalidad.setEditable(false);



            aceptar.setVisible(false);
            aceptar.setDisable(true);
            cancelar.setVisible(false);
            cancelar.setDisable(true);

            atras.setVisible(true);
            atras.setDisable(false);
            nueva.setVisible(true);
            nueva.setDisable(false);

            contadorMayor50000();

            actualizarDatos();

        }
    }

    public void accionCancelar() {
        numero.setPromptText("");
        titular.setPromptText("");
        fecha.setPromptText("");
        saldo.setPromptText("");
        nacionalidad.setPromptText("");


        numero.setStyle("");
        titular.setStyle("");
        fecha.setStyle("");
        saldo.setStyle("");
        nacionalidad.setStyle("");

        etiNueva.setVisible(false);
        etiExist.setVisible(true);

        numero.setEditable(false);
        titular.setEditable(false);
        fecha.setEditable(false);
        saldo.setEditable(false);
        nacionalidad.setEditable(false);

        actualizarDatos();

        aceptar.setVisible(false);
        aceptar.setDisable(true);
        cancelar.setVisible(false);
        cancelar.setDisable(true);

        atras.setVisible(true);
        atras.setDisable(false);
        nueva.setVisible(true);
        nueva.setDisable(false);
    }

    /*
    public void contador() {
        int cont = 0;

        numExist.setText(String.valueOf(listaCuentas.size()));

        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getSaldo() >= 50000) {
                cont++;
            }
        }

        num50000.setText(String.valueOf(cont));
    }
     */

    public int contadorMayor50000() {
        int cont = 0;



        for (Cuenta cuenta : listaCuentas) {
            if (cuenta.getSaldo() >= 50000) {
                cont++;
            }
        }
        return cont;
    }

    public void actualizarDatos() {
        int contador = contadorMayor50000();
        numExist.setText(String.valueOf(listaCuentas.size()));
        num50000.setText(String.valueOf(contador));
        numero.setText(String.valueOf(listaCuentas.get(contadorCuentas).getNumeroCuenta()));
        titular.setText(listaCuentas.get(contadorCuentas).getTitular());
        fecha.setText(listaCuentas.get(contadorCuentas).getFechApertura());
        saldo.setText(String.valueOf(listaCuentas.get(contadorCuentas).getSaldo()));
        nacionalidad.setText(String.valueOf(listaCuentas.get(contadorCuentas).getNacionalidad()));
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return true;
        }

        return false;
    }
    public boolean titulardigit(String passCode){
        for (int i = 0; i < passCode.length(); i++) {
            if(Character.isDigit(passCode.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean validDate(String fecha) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public void menuContextual() {
        ContextMenu menuAtras = new ContextMenu(new MenuItem("Atrás"), new MenuItem("Back"), new MenuItem("Derrière"), new MenuItem("Atrás"));
        ContextMenu menuSiguiente = new ContextMenu(new MenuItem("Adelante"), new MenuItem("Next"), new MenuItem("Prochain"), new MenuItem("Seguinte"));

        menuSiguiente.setStyle("");
        siguiente.setContextMenu(menuSiguiente);
        atras.setContextMenu(menuAtras);
    }

    public void reporteSimple() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String urlCon = "jdbc:mariadb://127.0.0.1:3306/ddi";
            Connection conexBd = DriverManager.getConnection(urlCon, "root", "root");
            //String reportPath = "C:\\Users\\pablo\\JaspersoftWorkspace\\MyReports\\SimpleReport.jrxml";
            //String reportPath = "\\src\\main\\resources\\SimpleReport.jrxml";
            String reportPath = "src\\main\\resources\\SimpleReport.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conexBd);
            String ruta = "src\\main\\resources\\Reporte.pdf";
            JasperExportManager.exportReportToPdfFile(jp, ruta);

            JasperViewer.viewReport(jp,false);
            conexBd.close();

        } catch (SQLException | JRException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }



    public void reporteComplejo(ActionEvent actionEvent) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String urlCon = "jdbc:mariadb://127.0.0.1:3306/ddi";
            Connection conexBd = DriverManager.getConnection(urlCon, "root", "root");
            //String reportPath = "C:\\Users\\pablo\\JaspersoftWorkspace\\MyReports\\ComplexReport.jrxml";
            String reportPath = "src\\main\\resources\\ComplexReport.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conexBd);

            String ruta = "src\\main\\resources\\Reporte.html";
            JasperExportManager.exportReportToHtmlFile(jp, ruta);

            File htmlFile = new File(ruta);
            Desktop.getDesktop().browse(htmlFile.toURI());
            // JasperViewer.viewReport(jp);

            conexBd.close();

        } catch (SQLException | ClassNotFoundException | JRException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}