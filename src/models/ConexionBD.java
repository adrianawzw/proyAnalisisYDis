
package models;

import java.sql.*;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto_diseño";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
