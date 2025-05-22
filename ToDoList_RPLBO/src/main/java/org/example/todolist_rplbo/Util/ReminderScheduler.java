package org.example.todolist_rplbo.Util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.Service.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final TaskManager taskManager;
    private final long reminderMinutes = 10;

    public ReminderScheduler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkTasks, 0, 1, TimeUnit.MINUTES);
    }

    private void checkTasks() {
        int userId = UserSession.getUserId();
        List<Task> tasks = taskManager.getAllTasksByUser(userId);
        LocalDateTime now = LocalDateTime.now();

        for (Task task : tasks) {
            if ("Berlangsung".equalsIgnoreCase(task.getStatus())) {
                Duration diff = Duration.between(now, task.getDueDate());
                long minutes = diff.toMinutes();

                if (minutes <= reminderMinutes && minutes >= 0 && !task.isReminderShown()) {
                    task.setReminderShown(true);
                    Platform.runLater(() -> showReminder(task));
                }
            }
        }
    }

    private void showReminder(Task task) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Pengingat Tugas");
        alert.setHeaderText("Tugas akan jatuh tempo!");
        alert.setContentText("Tugas: " + task.getName() + "\nDeadline: " + task.getDueDate());
        alert.show();
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}