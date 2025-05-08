package org.example.todolist_rplbo.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    public List<Task> getAllTasks() {
        // Contoh dummy data, nanti diganti ambil dari database SQLite
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Belajar PBO", "Berlangsung", LocalDateTime.now().plusMinutes(9)));
        tasks.add(new Task("Kerjakan Laporan", "Selesai", LocalDateTime.now().minusDays(1)));
        return tasks;
    }
}