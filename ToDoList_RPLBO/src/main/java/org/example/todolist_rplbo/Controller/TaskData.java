package org.example.todolist_rplbo.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.todolist_rplbo.Model.Task;

public class TaskData {
    private static ObservableList<Task> tasks = FXCollections.observableArrayList();

    public static ObservableList<Task> getTasks() {
        return tasks;
    }

    public static void addTask(Task task) {
        tasks.add(task);
    }

    public static void removeTask(Task task) {
        tasks.remove(task);
    }

    public static Task findTaskById(String id) {
        return tasks.stream().filter(t -> Integer.valueOf(t.getId()).equals(id)).findFirst().orElse(null);
    }
}