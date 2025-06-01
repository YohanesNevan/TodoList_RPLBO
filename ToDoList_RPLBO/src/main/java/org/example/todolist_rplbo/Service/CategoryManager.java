package org.example.todolist_rplbo.Service;

import org.example.todolist_rplbo.Model.Category;
import org.example.todolist_rplbo.database.SQLiteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private Connection conn;

    public CategoryManager() throws SQLException {
        conn = SQLiteConnection.getConnection();
    }

    public boolean addCategory(String name, int user_id) {
        String sql = "INSERT INTO categories (name, user_id, is_active) VALUES (?, ?, 1)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, user_id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Category> getAllCategories(int userId) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE is_active= 1 AND (user_id = ? OR user_id IS NULL)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("user_id"), rs.getInt("is_active"));
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteCategory(int id) {
        String sql = "UPDATE categories SET is_active = 0 WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void copyDefaultCategories(int userId) throws SQLException {
        String selectDefaultSql = "SELECT name FROM categories WHERE user_id IS NULL AND is_active = 1";
        String insertUserSql = "INSERT OR IGNORE INTO categories (name, is_active, user_id) VALUES (?, 1, ?)";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectDefaultSql);
             ResultSet rs = selectStmt.executeQuery();
             PreparedStatement insertStmt = conn.prepareStatement(insertUserSql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                insertStmt.setString(1, name);
                insertStmt.setInt(2, userId);
                insertStmt.executeUpdate();
            }
        }
    }

}
