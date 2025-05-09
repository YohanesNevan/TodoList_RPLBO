package org.example.todolist_rplbo.Service;

import org.example.todolist_rplbo.Model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Connection conn;

    public TaskManager() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public boolean createTask(Task task) {
        String sql = "INSERT INTO tasks (user_id, name, description, due_date, priority, category, isRepeated) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, task.getUserId());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getDueDate().toString());
            stmt.setString(5, task.getPriority());
            stmt.setString(6, task.getCategory());
            stmt.setInt(7, task.isRepeated() ? 1 : 0);

            int affected = stmt.executeUpdate();
            return affected > 0;
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

            while(rs.next()) {
                Task task = Task.fromResultSet(rs);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public Task getTaskById(int id) {
        Task task = null;
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                task = Task.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public boolean updateTask(Task task) {
        String sql = "UPDATE FROM tasks SET name = ?, description = ?, due_date = ?, priority = ?, status = ?, isRepeat = ?, repeatType = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDueDate().toString());
            stmt.setString(4, task.getPriority());
            stmt.setString(5, task.getStatus());
            stmt.setInt(6, task.isRepeated() ? 1 : 0);
            stmt.setString(7, task.getRepeatType());

            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
