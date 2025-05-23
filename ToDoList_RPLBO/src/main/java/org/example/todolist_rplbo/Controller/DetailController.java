package org.example.todolist_rplbo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailController {

    @FXML private TextField namaTugasField;
    @FXML private TextField deadlineField;
    @FXML private TextField prioritasField;
    @FXML private TextField statusField;
    @FXML private TextField kategoriField;
    @FXML private TextArea deskripsiArea;

    public void setTask(Task tugas) {
        namaTugasField.setText(tugas.getNama());
        deadlineField.setText(tugas.getTanggalSelesai());
        prioritasField.setText(tugas.getPrioritas());
        statusField.setText(tugas.getStatus());
        kategoriField.setText(tugas.getKategori());
        deskripsiArea.setText(tugas.getDeskripsi());
    }

    @FXML
    private void handleEdit() {
        // navigasi ke halaman edit
    }

    @FXML
    private void handleHapus() {
        // konfirmasi lalu hapus
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

