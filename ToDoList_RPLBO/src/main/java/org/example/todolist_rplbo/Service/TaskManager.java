package org.example.todolist_rplbo.Service;

import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.database.SQLiteConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Connection conn;

    public TaskManager() throws SQLException {
        this.conn = SQLiteConnection.getConnection();
    }

    public boolean createTask(Task task) {
        String sql = """
            INSERT INTO tasks (user_id, nama, tanggal_dibuat, waktu_mulai, status, deskripsi, 
                               tanggal_selesai, waktu_selesai, prioritas, kategori, pengulangan)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, task.getUserId());
            stmt.setString(2, task.getNama());
            stmt.setString(3, task.getTanggalMulaiString());
            stmt.setString(4, task.getWaktuMulaiString()); // waktu_mulai
            stmt.setString(5, task.getStatus());
            stmt.setString(6, task.getDeskripsi());
            stmt.setString(7, task.getTanggalSelesaiString());
            stmt.setString(8, task.getWaktuSelesaiString()); // waktu_selesai
            stmt.setString(9, task.getPrioritas());
            stmt.setString(10, task.getKategori());
            stmt.setString(11, task.getPengulangan());

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
            SET nama = ?, tanggal_dibuat = ?, waktu_mulai = ?, status = ?, deskripsi = ?,
                tanggal_selesai = ?, waktu_selesai = ?, prioritas = ?, kategori = ?, pengulangan = ?
            WHERE id = ?;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getNama());
            stmt.setString(2, task.getTanggalMulaiString());
            stmt.setString(3, task.getWaktuMulaiString()); // waktu_mulai
            stmt.setString(4, task.getStatus());
            stmt.setString(5, task.getDeskripsi());
            stmt.setString(6, task.getTanggalSelesaiString());
            stmt.setString(7, task.getWaktuSelesaiString()); // waktu_selesai
            stmt.setString(8, task.getPrioritas());
            stmt.setString(9, task.getKategori());
            stmt.setString(10, task.getPengulangan());
            stmt.setInt(11, task.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean markTaskAsSelesai(int taskId) {
        String sql = "UPDATE tasks SET status = 'Selesai' WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taskId);
            if (stmt.executeUpdate() > 0) {
                Task selesaiTask = getTaskById(taskId);
                if (selesaiTask.getPengulangan() != null) {
                    generateUlangTask(selesaiTask); // Langsung buat ulang
                }
                return true;
            }
            return false;
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

    public boolean generateUlangTask(Task lama) {
        if (lama.getPengulangan() == null || lama.getWaktuSelesai() == null) return false;

        LocalDateTime baru = lama.kapanUlang(lama.getWaktuSelesai());

        Task baruTask = new Task(
                lama.getNama(),
                lama.getUserId(),
                LocalDate.now(),
                "Belum Dikerjakan",
                lama.getDeskripsi(),
                baru.toLocalDate(),
                lama.getPrioritas(),
                lama.getKategori()
        );
        baruTask.setWaktuMulai(lama.kapanUlang(lama.getWaktuMulai()));
        baruTask.setWaktuSelesai(baru);
        baruTask.setPengulangan(lama.getPengulangan());

        if (baru.isBefore(LocalDateTime.now())) return false;

        return createTask(baruTask);
    }

}
