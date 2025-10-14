package BDconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection unicaInstancia;
    private Connection connection;

    // Datos de conexión (puedes modificarlos según tu BD)
    private final String url = "jdbc:mysql://localhost:3306/sistemainventario";
    private final String username = "root";
    private final String password = "";

    private DBConnection() {
        conectar();
    }

    // Método que establece la conexión
    private void conectar() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión establecida correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // singleton
    public static DBConnection getInstance() {
        if (unicaInstancia == null) {
            synchronized (DBConnection.class) {
                if (unicaInstancia == null) {
                    unicaInstancia = new DBConnection();
                }
            }
        }
        return unicaInstancia;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("La conexión estaba cerrada. Reabriendo conexión...");
                conectar();
            }
        } catch (SQLException e) {
            System.err.println("Error al comprobar el estado de la conexión: " + e.getMessage());
            conectar();
        }
        return connection;
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
