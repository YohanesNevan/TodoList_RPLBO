package org.example.todolist_rplbo.Model;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private String status; // "Berlangsung", "Selesai", dll
    private LocalDateTime dueDate;
    private boolean reminderShown = false;

    public Task(String title, String status, LocalDateTime dueDate) {
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public boolean isReminderShown() {
        return reminderShown;
    }

    public void setReminderShown(boolean reminderShown) {
        this.reminderShown = reminderShown;
    }

}