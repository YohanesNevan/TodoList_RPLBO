package org.example.todolist_rplbo.Service;

import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.Service.SQLiteConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Connection conn;

    public TaskManager() throws SQLException {
        this.conn = SQLiteConnection.getConnection();
    }

    public boolean createTask(Task task) {
        String sql = """
            INSERT INTO tasks (user_id, nama, tanggal_dibuat, status, deskripsi, 
                               tanggal_selesai, prioritas, kategori, pengulangan)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, task.getUserId());
            stmt.setString(2, task.getNama());
            stmt.setString(3, task.getTanggalMulaiString());
            stmt.setString(4, task.getStatus());
            stmt.setString(5, task.getDeskripsi());
            stmt.setString(6, task.getTanggalSelesaiString());
            stmt.setString(7, task.getPrioritas());
            stmt.setString(8, task.getKategori());
            stmt.setString(9, task.getPengulangan());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Task> getAllTasksByUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(Task.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public boolean updateTask(Task task) {
        String sql = """
            UPDATE tasks
            SET nama = ?, tanggal_dibuat = ?, status = ?, deskripsi = ?,
                tanggal_selesai = ?, prioritas = ?, kategori = ?, pengulangan = ?
            WHERE id = ?;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getNama());
            stmt.setString(2, task.getTanggalMulaiString());
            stmt.setString(3, task.getStatus());
            stmt.setString(4, task.getDeskripsi());
            stmt.setString(5, task.getTanggalSelesaiString());
            stmt.setString(6, task.getPrioritas());
            stmt.setString(7, task.getKategori());
            stmt.setString(8, task.getPengulangan());
            stmt.setInt(9, task.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Task getTaskById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Task.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
