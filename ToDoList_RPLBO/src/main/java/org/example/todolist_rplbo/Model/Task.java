package org.example.todolist_rplbo.Model;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    public Task(String title, String status, LocalDateTime dueDate) {
        this.title = title;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }
}