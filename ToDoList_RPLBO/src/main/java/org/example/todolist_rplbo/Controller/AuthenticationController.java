package org.example.todolist_rplbo.Controller;

import org.example.todolist_rplbo.Util.UserSession;
import org.example.todolist_rplbo.database.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationController {
    public boolean login(String inputUsername, String inputPassword) {
        String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";
    
        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, inputUsername);
            stmt.setString(2, inputPassword); 
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int userId = rs.getInt("id");
                String username = rs.getString("username");
    
                UserSession.startSession(userId, username);
                System.out.println("Login berhasil sebagai: " + username);
                return true;
            } else {
                System.out.println("Login gagal: username atau password salah.");
                return false;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void logout() {
        UserSession.endSession();
        System.out.println("Logout berhasil.");
    }
    
}
