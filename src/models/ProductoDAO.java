package models;

import BDconnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class ProductoDAO {
    private DBConnection dbConnection;

    public ProductoDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto ORDER BY id_producto";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Producto producto = mapearProducto(rs);
                productos.add(producto);
            }
        } catch (Exception e) {
            System.err.println("Error en obtenerTodosLosProductos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
        return productos;
    }

    public boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO producto (nombre, descripcion, unidad, stock_max, stock_min, stock_actual, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setString(3, producto.getUnidad());
            stmt.setInt(4, producto.getStockMax());
            stmt.setInt(5, producto.getStockMin());
            stmt.setInt(6, producto.getStockActual());
            
            if (producto.getImagenData() != null) {
                stmt.setBlob(7, new ByteArrayInputStream(producto.getImagenData()), producto.getImagenData().length);
            } else {
                stmt.setNull(7, java.sql.Types.BLOB);
            }

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.err.println("Error en insertarProducto: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, unidad = ?, stock_max = ?, stock_min = ?, stock_actual = ?, imagen = ? WHERE id_producto = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setString(3, producto.getUnidad());
            stmt.setInt(4, producto.getStockMax());
            stmt.setInt(5, producto.getStockMin());
            stmt.setInt(6, producto.getStockActual());
            
            if (producto.getImagenData() != null) {
                stmt.setBlob(7, new ByteArrayInputStream(producto.getImagenData()), producto.getImagenData().length);
            } else {
                stmt.setNull(7, java.sql.Types.BLOB);
            }
            
            stmt.setInt(8, producto.getIdProducto());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.err.println("Error en actualizarProducto: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean actualizarImagenProducto(int idProducto, byte[] imagenData) {
        String sql = "UPDATE producto SET imagen = ? WHERE id_producto = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            if (imagenData != null) {
                stmt.setBlob(1, new ByteArrayInputStream(imagenData), imagenData.length);
            } else {
                stmt.setNull(1, java.sql.Types.BLOB);
            }
            
            stmt.setInt(2, idProducto);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.err.println("Error en actualizarImagenProducto: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProducto);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.err.println("Error en eliminarProducto: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando PreparedStatement: " + e.getMessage());
            }
        }
    }

    public Producto obtenerProductoPorId(int idProducto) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        Producto producto = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProducto);

            rs = stmt.executeQuery();
            if (rs.next()) {
                producto = mapearProducto(rs);
            }
        } catch (Exception e) {
            System.err.println("Error en obtenerProductoPorId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
        return producto;
    }

    public boolean existeProducto(String nombre) {
        String sql = "SELECT COUNT(*) FROM producto WHERE nombre = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);

            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.err.println("Error en existeProducto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
        return false;
    }

    private Producto mapearProducto(ResultSet rs) throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(rs.getInt("id_producto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setUnidad(rs.getString("unidad"));
        producto.setStockMax(rs.getInt("stock_max"));
        producto.setStockMin(rs.getInt("stock_min"));
        producto.setStockActual(rs.getInt("stock_actual"));
        
        // Obtener datos de la imagen BLOB
        java.sql.Blob blob = rs.getBlob("imagen");
        if (blob != null) {
            byte[] imagenData = blob.getBytes(1, (int) blob.length());
            producto.setImagenData(imagenData);
            
            // Convertir a Image de JavaFX
            if (imagenData != null && imagenData.length > 0) {
                try {
                    Image imagenFX = new Image(new ByteArrayInputStream(imagenData));
                    producto.setImagenFX(imagenFX);
                } catch (Exception e) {
                    System.err.println("Error convirtiendo imagen BLOB a Image: " + e.getMessage());
                }
            }
        }
        
        return producto;
    }

    // Método auxiliar para convertir File a byte[]
    public byte[] fileToBytes(File file) throws IOException {
        byte[] fileData = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileData);
        }
        return fileData;
    }
}