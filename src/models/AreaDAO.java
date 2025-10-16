package models;

import BDconnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {
    private DBConnection dbConnection;

    public AreaDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    public List<Area> obtenerTodasLasAreas() {
        List<Area> areas = new ArrayList<>();
        String sql = "SELECT * FROM area ORDER BY nombre_area";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Area area = new Area();
                area.setIdArea(rs.getInt("id_area"));
                area.setNombreArea(rs.getString("nombre_area"));
                area.setResponsable(rs.getString("responsable"));
                areas.add(area);
            }
        } catch (Exception e) {
            System.err.println("Error en obtenerTodasLasAreas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
        return areas;
    }

    public Area obtenerAreaPorId(int idArea) {
        String sql = "SELECT * FROM area WHERE id_area = ?";
        Area area = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idArea);

            rs = stmt.executeQuery();
            if (rs.next()) {
                area = new Area();
                area.setIdArea(rs.getInt("id_area"));
                area.setNombreArea(rs.getString("nombre_area"));
                area.setResponsable(rs.getString("responsable"));
            }
        } catch (Exception e) {
            System.err.println("Error en obtenerAreaPorId: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
        return area;
    }
}