package controlador;

import java.sql.*;

public class Funcionalidad {

    public boolean insertar(String actualiza) {
        boolean control = false;

        try {
            Class.forName("org.mariadb.jdbc.Driver"); // Cargamos el driver en el programa
            String urlCon = "jdbc:mariadb://localhost:3306/DDI"; // Creamos la cadena de conexión
            Connection conexBd = DriverManager.getConnection(urlCon, "root", "root");
            Statement encapsulaCons = conexBd.createStatement();

            int filActualizadas = encapsulaCons.executeUpdate(actualiza); // Ejecutamos la actualización anterior
            control = filActualizadas > 0;

            encapsulaCons.close();
            conexBd.close();
        } catch (ClassNotFoundException | SQLException cnfe) {
            System.out.println(cnfe.getMessage());
        }
        return control;
    }

    public ResultSet consulta(String consulta) {
        ResultSet resulCons = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String urlCon = "jdbc:mariadb://localhost:3306/DDI";
            Connection conexBd = DriverManager.getConnection(urlCon, "root", "root");
            Statement encapsulaCons = conexBd.createStatement();

            resulCons = encapsulaCons.executeQuery(consulta);

            encapsulaCons.close();
            conexBd.close();
        } catch (ClassNotFoundException | SQLException cnfe) {
            System.out.println(cnfe.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resulCons;
    }
}
