package org.example.todolist_rplbo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class TambahTugasController {

    @FXML
    private TextField judulField;

    @FXML
    private TextArea deskripsiArea;

    @FXML
    private DatePicker tanggalMulaiPicker;

    @FXML
    private DatePicker tanggalSelesaiPicker;

    @FXML
    public void handleSimpan(ActionEvent event) {
        // Ambil data dari form
        String judul = judulField.getText();
        String deskripsi = deskripsiArea.getText();
        LocalDate tanggalMulai = tanggalMulaiPicker.getValue();
        LocalDate tanggalSelesai = tanggalSelesaiPicker.getValue();

        // Tambahkan tugas ke TaskData
        Task newTask = new Task(judul, tanggalMulai.toString(), "Belum Dikerjakan");
        TaskData.addTask(newTask);

        // Untuk testing: tampilkan isi input di konsol
        System.out.println("Tugas Disimpan:");
        System.out.println("Judul: " + judul);
        System.out.println("Deskripsi: " + deskripsi);
        System.out.println("Tanggal Mulai: " + tanggalMulai);
        System.out.println("Tanggal Selesai: " + tanggalSelesai);

        // Setelah simpan, pindah ke dashboard
        try {
            // Load Dashboard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
            Parent dashboardLayout = loader.load();

            // Set scene baru di window
            Scene dashboardScene = new Scene(dashboardLayout);
            Stage stage = (Stage) judulField.getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void handleBatal(ActionEvent event) {
        // Kosongkan semua input
        judulField.clear();
        deskripsiArea.clear();
        tanggalMulaiPicker.setValue(null);
        tanggalSelesaiPicker.setValue(null);

        System.out.println("Form dibatalkan.");
    }

    public void handledashboard(ActionEvent event) {
        try {
            // Load Dashboard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
            Parent dashboardLayout = loader.load();

            // Set scene baru di window
            Scene dashboardScene = new Scene(dashboardLayout);
            Stage stage = (Stage) judulField.getScene().getWindow();
            stage.setScene(dashboardScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

