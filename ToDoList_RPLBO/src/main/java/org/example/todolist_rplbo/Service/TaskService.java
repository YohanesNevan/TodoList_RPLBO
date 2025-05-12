package org.example.todolist_rplbo.Service;

import org.example.todolist_rplbo.Model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Belajar PBO", "Berlangsung", LocalDateTime.now().plusMinutes(9)));
        tasks.add(new Task("Kerjakan Laporan", "Selesai", LocalDateTime.now().minusDays(1)));
        return tasks;
    }

    public List<Task> getRiwayatTasks() {
        List<Task> riwayat = new ArrayList<>();
        for (Task task : getAllTasks()) {
            if ("Selesai".equalsIgnoreCase(task.getStatus())) {
                riwayat.add(task);
            }
        }
        return riwayat;
    }

}