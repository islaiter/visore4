package controlador;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class VisualizaCuentasControllerTest {

    VisualizaCuentasController vcc = new VisualizaCuentasController();


    @Test
    public void numeroCuentasTotales() throws SQLException {
        assertEquals(11,vcc.contarNumeroCuentasTotales());
    }

    @Test
    public void numeroCuentasMayor50000() throws SQLException {
        Integer numeroCuentas = vcc.cuentasSaldoMayor50000();
        assertEquals(7,numeroCuentas);
    }

}