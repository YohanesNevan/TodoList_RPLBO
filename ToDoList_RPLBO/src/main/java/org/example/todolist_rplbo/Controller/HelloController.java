package org.example.todolist_rplbo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.todolist_rplbo.Service.TaskService;
import org.example.todolist_rplbo.Util.ReminderScheduler;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    private ReminderScheduler reminderScheduler;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TaskService taskService = new TaskService();
        reminderScheduler = new ReminderScheduler(taskService);
        reminderScheduler.start();
    }
}