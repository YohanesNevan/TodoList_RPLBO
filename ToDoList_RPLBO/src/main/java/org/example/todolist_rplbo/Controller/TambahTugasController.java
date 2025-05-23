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
    @FXML private ComboBox<String> prioritasComboBox;
    @FXML private ComboBox<String> kategoriComboBox;
    @FXML private ToggleGroup pengulanganGroup;

    @FXML private RadioButton rbTidak, rbHarian, rbMingguan, rbBulanan;


    private Task taskBeingEdited;



    @FXML
    private void initialize() {
        kategoriComboBox.setItems(KategoriController.getKategoriList());
        kategoriComboBox.setEditable(true); // jika ingin user bisa input manual

        prioritasComboBox.getItems().addAll("Rendah", "Sedang", "Tinggi");

        // Create and assign ToggleGroup
        ToggleGroup pengulanganGroup = new ToggleGroup();
        rbTidak.setToggleGroup(pengulanganGroup);
        rbHarian.setToggleGroup(pengulanganGroup);
        rbMingguan.setToggleGroup(pengulanganGroup);
        rbBulanan.setToggleGroup(pengulanganGroup);

        // Set default selection
        rbTidak.setSelected(true);
    }

    public void setEditMode(Task task) {
        this.taskBeingEdited = task;
        judulField.setText(task.getNama());
        deskripsiArea.setText(task.getDeskripsi());
        tanggalMulaiPicker.setValue(LocalDate.parse(task.getTanggal()));
        tanggalSelesaiPicker.setValue(LocalDate.parse(task.getTanggalSelesai()));
        prioritasComboBox.setValue(task.getPrioritas());
        kategoriComboBox.setValue(task.getKategori());  // <- ini yang harus diubah

        // Set radio button pengulangan sesuai nilai task
        String pengulangan = task.getPengulangan();
        if (pengulangan == null || pengulangan.isEmpty()) {
            rbTidak.setSelected(true);
        } else if (pengulangan.equalsIgnoreCase("Harian")) {
            rbHarian.setSelected(true);
        } else if (pengulangan.equalsIgnoreCase("Mingguan")) {
            rbMingguan.setSelected(true);
        } else if (pengulangan.equalsIgnoreCase("Bulanan")) {
            rbBulanan.setSelected(true);
        } else {
            rbTidak.setSelected(true); // default fallback
        }
    }

    @FXML
    public void handleSimpan(ActionEvent event) {
        String judul = judulField.getText();
        String deskripsi = deskripsiArea.getText();
        LocalDate tanggalMulai = tanggalMulaiPicker.getValue();
        LocalDate tanggalSelesai = tanggalSelesaiPicker.getValue();
        String prioritas = prioritasComboBox.getValue();
        String kategori = kategoriComboBox.getValue();
        String pengulangan = null;

        // Get pengulangan value
        if (rbHarian.isSelected()) {
            pengulangan = "Harian";
        } else if (rbMingguan.isSelected()) {
            pengulangan = "Mingguan";
        } else if (rbBulanan.isSelected()) {
            pengulangan = "Bulanan";
        }
        // If none selected, pengulangan remains null (Tidak Berulang)

        // Validation
        if (judul.isEmpty() || deskripsi.isEmpty() || tanggalMulai == null || tanggalSelesai == null) {
            new Alert(Alert.AlertType.ERROR, "Semua field harus diisi.").show();
            return;
        }

        if (tanggalSelesai.isBefore(tanggalMulai)) {
            new Alert(Alert.AlertType.ERROR, "Tanggal selesai tidak boleh sebelum tanggal mulai.").show();
            return;
        }

        if (taskBeingEdited != null) {
            // Update existing task
            taskBeingEdited.setNama(judul);
            taskBeingEdited.setDeskripsi(deskripsi);
            taskBeingEdited.setTanggal(tanggalMulai.toString());
            taskBeingEdited.setTanggalSelesai(tanggalSelesai.toString());
            taskBeingEdited.setPrioritas(prioritas);
            taskBeingEdited.setKategori(kategori);
            taskBeingEdited.setPengulangan(pengulangan);  // Add this line
        } else {
            // Create new task
            Task newTask = new Task(judul, tanggalMulai.toString(), "Belum Dikerjakan",
                    deskripsi, tanggalSelesai.toString(), prioritas, kategori);
            newTask.setPengulangan(pengulangan);  // Add this line
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
        prioritasComboBox.setValue(null);
        kategoriComboBox.setValue(null);
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

    public void handleKembali(ActionEvent event) {
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
