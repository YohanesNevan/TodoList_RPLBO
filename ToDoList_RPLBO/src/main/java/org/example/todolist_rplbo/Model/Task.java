package org.example.todolist_rplbo.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Task {
    private int id;
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private String priority;
    private String category;
    private String status;
    private boolean isRepeated;
    private String repeatType;
    private int userId;

    public Task(String name, String description, LocalDateTime dueDate, String priority, String category, String status, boolean isRepeated, String repeatType, int userId) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.isRepeated = isRepeated;
        this.repeatType = repeatType;
        this.userId = userId;
    }

    public Task() {}

    public boolean isOverdue() { //apakah sudah lewat deadline
        return status.equalsIgnoreCase("Berlangsung") && dueDate.isBefore(LocalDateTime.now());
    }

    public String toString() { //buat jadiin string (bantu debugging juga)
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public static Task fromResultSet(ResultSet rs) throws SQLException { //buat ambil datanya dari resultset
        Task task = new Task();

        task.setId(rs.getInt("id"));
        task.setName(rs.getString("name"));
        task.setDescription(rs.getString("description"));
        task.setDueDate(rs.getTimestamp("due_date").toLocalDateTime());
        task.setPriority(rs.getString("priority"));
        task.setCategory(rs.getString("category"));
        task.setStatus(rs.getString("status"));
        task.setRepeated(rs.getBoolean("is_repeated"));
        task.setRepeatType(rs.getString("repeat_type"));
        task.setUserId(rs.getInt("user_id"));
        return task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
