// TambahTugasController.java
package org.example.todolist_rplbo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class TambahTugasController {
    @FXML private TextField judulField;
    @FXML private TextArea deskripsiArea;
    @FXML private DatePicker tanggalMulaiPicker;
    @FXML private DatePicker tanggalSelesaiPicker;

    private Task taskBeingEdited;

    public void setEditMode(Task task) {
        this.taskBeingEdited = task;
        judulField.setText(task.getNama());
        deskripsiArea.setText(task.getDeskripsi());
        tanggalMulaiPicker.setValue(LocalDate.parse(task.getTanggal()));
        tanggalSelesaiPicker.setValue(LocalDate.parse(task.getTanggalSelesai()));
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
            taskBeingEdited.setNama(judul);
            taskBeingEdited.setDeskripsi(deskripsi);
            taskBeingEdited.setTanggal(tanggalMulai.toString());
            taskBeingEdited.setTanggalSelesai(tanggalSelesai.toString());
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
