package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import BDconnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private DBConnection dbConnection;

    public ProveedorDAO() {
        this.dbConnection = DBConnection.getInstance();
    }

    // CREATE
    public boolean insertarProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO proveedor (nombre_prov, ruc, telefono, direccion, id_producto) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, proveedor.getNombreProv());
            pstmt.setString(2, proveedor.getRuc());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getDireccion());
            if (proveedor.getIdProducto() > 0) {
                pstmt.setInt(5, proveedor.getIdProducto());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar proveedor: " + e.getMessage());
            return false;
        }
    }

    // READ ALL con nombre de producto
    public List<Proveedor> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT p.*, prod.nombre as nombre_producto " +
                    "FROM proveedor p " +
                    "LEFT JOIN producto prod ON p.id_producto = prod.id_producto " +
                    "ORDER BY p.id_proveedor";
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Proveedor proveedor = mapearProveedor(rs);
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        }
        return proveedores;
    }

    // UPDATE
    public boolean actualizarProveedor(Proveedor proveedor) {
        String sql = "UPDATE proveedor SET nombre_prov = ?, ruc = ?, telefono = ?, direccion = ?, id_producto = ? WHERE id_proveedor = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, proveedor.getNombreProv());
            pstmt.setString(2, proveedor.getRuc());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getDireccion());
            if (proveedor.getIdProducto() > 0) {
                pstmt.setInt(5, proveedor.getIdProducto());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }
            pstmt.setInt(6, proveedor.getIdProveedor());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(rs.getInt("id_proveedor"));
        proveedor.setNombreProv(rs.getString("nombre_prov"));
        proveedor.setRuc(rs.getString("ruc"));
        proveedor.setTelefono(rs.getString("telefono"));
        proveedor.setDireccion(rs.getString("direccion"));
        proveedor.setIdProducto(rs.getInt("id_producto"));
        proveedor.setNombreProducto(rs.getString("nombre_producto"));
        return proveedor;
    }

    // Los demás métodos (eliminar, existeRuc, etc.) se mantienen igual
    public boolean eliminarProveedor(int idProveedor) {
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProveedor);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean existeRuc(String ruc) {
        String sql = "SELECT COUNT(*) FROM proveedor WHERE ruc = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ruc);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar RUC: " + e.getMessage());
        }
        return false;
    }
}