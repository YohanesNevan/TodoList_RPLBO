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

    public boolean addCategory(String name) {
        String sql = "INSERT INTO categories (name, is_active) VALUES (?, 1)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE is_active= 1";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("is_active"));
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
}
