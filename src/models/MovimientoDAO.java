package models;

import BDconnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {
    private DBConnection dbConnection;

    public MovimientoDAO() {
        this.dbConnection = DBConnection.getInstance();
    }

    public boolean registrarMovimiento(Movimiento movimiento) {
        String sql = "INSERT INTO movimiento (fecha_mov, tipo_mov, cantidad, id_producto, id_usuario, id_area) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setTimestamp(1, new Timestamp(movimiento.getFechaMov().getTime()));
            stmt.setString(2, movimiento.getTipoMov());
            stmt.setInt(3, movimiento.getCantidad());
            stmt.setInt(4, movimiento.getIdProducto());
            stmt.setInt(5, movimiento.getIdUsuario());
            stmt.setInt(6, movimiento.getIdArea());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (Exception e) {
            System.err.println("Error en registrarMovimiento: " + e.getMessage());
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

    public List<Movimiento> obtenerTodosLosMovimientos() {
        List<Movimiento> movimientos = new ArrayList<>();
        String sql = "SELECT m.*, p.nombre as producto_nombre, u.nombre as usuario_nombre, a.nombre_area " +
                     "FROM movimiento m " +
                     "LEFT JOIN producto p ON m.id_producto = p.id_producto " +
                     "LEFT JOIN usuario u ON m.id_usuario = u.id_usuario " +
                     "LEFT JOIN area a ON m.id_area = a.id_area " +
                     "ORDER BY m.fecha_mov DESC";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Movimiento movimiento = mapearMovimiento(rs);
                movimientos.add(movimiento);
            }
        } catch (Exception e) {
            System.err.println("Error en obtenerTodosLosMovimientos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
        return movimientos;
    }

    private Movimiento mapearMovimiento(ResultSet rs) throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setIdMov(rs.getInt("id_mov"));
        movimiento.setFechaMov(rs.getTimestamp("fecha_mov"));
        movimiento.setTipoMov(rs.getString("tipo_mov"));
        movimiento.setCantidad(rs.getInt("cantidad"));
        movimiento.setIdProducto(rs.getInt("id_producto"));
        movimiento.setIdUsuario(rs.getInt("id_usuario"));
        movimiento.setIdArea(rs.getInt("id_area"));
        
        // Datos adicionales de las tablas relacionadas
        movimiento.setNombreProducto(rs.getString("producto_nombre"));
        movimiento.setNombreUsuario(rs.getString("usuario_nombre"));
        movimiento.setNombreArea(rs.getString("nombre_area"));
        
        return movimiento;
    }
}