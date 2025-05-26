package org.example.todolist_rplbo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.Service.TaskManager;


import java.io.IOException;

public class DetailController {

    @FXML private TextField namaTugasField;
    @FXML private TextField deadlineField;
    @FXML private TextField prioritasField;
    @FXML private TextField statusField;
    @FXML private TextField kategoriField;
    @FXML private TextArea deskripsiArea;
    private Task task;


    public void setTask(Task tugas) {
        this.task = tugas;

        if (tugas != null) {
            namaTugasField.setText(tugas.getNama());
            deadlineField.setText(tugas.getTanggalSelesaiString());
            prioritasField.setText(tugas.getPrioritas());
            statusField.setText(tugas.getStatus());
            kategoriField.setText(tugas.getKategori());
            deskripsiArea.setText(tugas.getDeskripsi());
        }
    }


    @FXML
    private void handleEdit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/tambahtugas-view.fxml"));
            Parent root = loader.load();

            // Kirim task ke TambahTugasController
            TambahTugasController controller = loader.getController();
            controller.setEditMode(task);

            Stage stage = (Stage) namaTugasField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleHapus() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText("Apakah kamu yakin ingin menghapus tugas ini?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    TaskManager manager = new TaskManager();
                    boolean success = manager.deleteTask(task.getId());

                    if (success) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Berhasil");
                        info.setHeaderText("Tugas berhasil dihapus.");
                        info.showAndWait();

                        // Kembali ke dashboard
                        handleKembali(null);
                    } else {
                        showAlert("Gagal", "Tugas gagal dihapus.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "Terjadi kesalahan saat menghapus.");
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    public void handleKembali(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) namaTugasField.getScene().getWindow(); // stage yang sama
            stage.setScene(new Scene(root));
            stage.show(); // Optional: pastikan stage tetap tampil
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

