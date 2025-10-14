package models;

import BDconnection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {
    
    public Usuario login(String username, String password) {
        Usuario user = null;
        String sql = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contraseña = ?";

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contraseña"),
                    rs.getString("rol")
                );
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}