// TambahTugasController.java
package org.example.todolist_rplbo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.Service.TaskManager;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TambahTugasController {
    @FXML private TextField judulField;
    @FXML private TextArea deskripsiArea;
    @FXML private DatePicker tanggalMulaiPicker;
    @FXML private DatePicker tanggalSelesaiPicker;

    private Task taskBeingEdited;
    private TaskManager taskManager;

    public TambahTugasController() {
        try {
            taskManager = new TaskManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEditMode(Task task) {
        this.taskBeingEdited = task;
        judulField.setText(task.getName());
        deskripsiArea.setText(task.getDescription());
        tanggalMulaiPicker.setValue((task.getDueDate().toLocalDate()));
        tanggalSelesaiPicker.setValue((task.getDueDate().toLocalDate()));
    }

    @FXML
    public void handleSimpan(ActionEvent event) {
        String judul = judulField.getText();
        String deskripsi = deskripsiArea.getText();
        LocalDate tanggalMulai = tanggalMulaiPicker.getValue();
        LocalDate tanggalSelesai = tanggalSelesaiPicker.getValue();

        if (judul.isEmpty() || deskripsi.isEmpty() || tanggalMulai == null || tanggalSelesai == null) {
            new Alert(Alert.AlertType.ERROR, "Semua field harus diisi.").show();
            return;
        }

        if (taskBeingEdited != null) {
            taskBeingEdited.setName(judul);
            taskBeingEdited.setDescription(deskripsi);
            taskBeingEdited.setDueDate(LocalDateTime.parse(tanggalMulai.toString()));
            taskBeingEdited.setDueDate(LocalDateTime.parse(tanggalSelesai.toString()));
        } else {
            Task newTask = new Task(judul, tanggalMulai.toString(), "Belum Dikerjakan", deskripsi, tanggalSelesai.toString());
            TaskData.addTask(newTask);
        }

        goToDashboard();
    }

    @FXML
    public void handleBatal(ActionEvent event) {
        judulField.clear();
        deskripsiArea.clear();
        tanggalMulaiPicker.setValue(null);
        tanggalSelesaiPicker.setValue(null);
    }

    private void goToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) judulField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handledashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) judulField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
