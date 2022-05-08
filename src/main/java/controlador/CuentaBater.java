package controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelo.Cuenta;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CuentaBater extends Application {

    Funcionalidad funcionalidad;

    public CuentaBater(){
        funcionalidad = new Funcionalidad();

    }

    private ArrayList<Cuenta> arrayCuentas = new ArrayList<>();



    Funcionalidad f1 = new Funcionalidad();

    @Override
    public void start(Stage stage) throws IOException {

        ResultSet sql;
        try {
            sql = funcionalidad.consulta("SELECT * FROM CUENTAS");
            while (sql.next()){
                Integer numCuenta = sql.getInt(1);
                String titularCuenta = sql.getString(2);
                String fechaAperturaCuenta = sql.getString(3);
                Double saldo = sql.getDouble(4);
                String nacionalidad = sql.getString(5);
                Cuenta c = new Cuenta(numCuenta,titularCuenta,fechaAperturaCuenta,saldo,nacionalidad);
                arrayCuentas.add(c);
            }
        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        FXMLLoader fxmlLoader = new FXMLLoader(CuentaBater.class.getResource("/vista/VisualizaCuentas.fxml"));
        Parent root = fxmlLoader.load();

        VisualizaCuentasController controlador = fxmlLoader.getController();
        controlador.iniciaLista(arrayCuentas);
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image(CuentaBater.class.getResourceAsStream("/vista/logo.png")));
        stage.setTitle("Aplicación Cuentas Bancarias");
        scene.getStylesheets().add(getClass().getResource("/vista/VisorStyle.css").toExternalForm());
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}