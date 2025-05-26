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
import org.example.todolist_rplbo.Util.UserSession;
import org.example.todolist_rplbo.Util.KategoriProvider;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class TambahTugasController {
    @FXML private TextField judulField;
    @FXML private TextArea deskripsiArea;
    @FXML private DatePicker tanggalMulaiPicker;
    @FXML private DatePicker tanggalSelesaiPicker;
    @FXML private ComboBox<String> prioritasComboBox;
    @FXML private ComboBox<String> kategoriComboBox;
    @FXML private RadioButton rbTidak, rbHarian, rbMingguan, rbBulanan;

    private Task taskBeingEdited;

    @FXML
    private void initialize() {
        kategoriComboBox.setItems(KategoriProvider.getKategoriList());
        kategoriComboBox.setEditable(true);
        prioritasComboBox.getItems().addAll("Rendah", "Sedang", "Tinggi");

        ToggleGroup pengulanganGroup = new ToggleGroup();
        rbTidak.setToggleGroup(pengulanganGroup);
        rbHarian.setToggleGroup(pengulanganGroup);
        rbMingguan.setToggleGroup(pengulanganGroup);
        rbBulanan.setToggleGroup(pengulanganGroup);
        rbTidak.setSelected(true);
    }

    public void setEditMode(Task task) {
        this.taskBeingEdited = task;
        judulField.setText(task.getNama());
        deskripsiArea.setText(task.getDeskripsi());
        tanggalMulaiPicker.setValue(task.getTanggalMulaiAsLocalDate());
        tanggalSelesaiPicker.setValue(task.getTanggalSelesaiAsLocalDate());
        prioritasComboBox.setValue(task.getPrioritas());
        kategoriComboBox.setValue(task.getKategori());

        switch (task.getPengulangan() != null ? task.getPengulangan() : "") {
            case "Harian" -> rbHarian.setSelected(true);
            case "Mingguan" -> rbMingguan.setSelected(true);
            case "Bulanan" -> rbBulanan.setSelected(true);
            default -> rbTidak.setSelected(true);
        }
    }

    @FXML
    public void handleSimpan(ActionEvent event) {
        String judul = judulField.getText().trim();
        String deskripsi = deskripsiArea.getText().trim();
        LocalDate tanggalMulai = tanggalMulaiPicker.getValue();
        LocalDate tanggalSelesai = tanggalSelesaiPicker.getValue();
        String prioritas = prioritasComboBox.getValue();
        String kategori = kategoriComboBox.getValue();
        String pengulangan = null;

        if (rbHarian.isSelected()) pengulangan = "Harian";
        else if (rbMingguan.isSelected()) pengulangan = "Mingguan";
        else if (rbBulanan.isSelected()) pengulangan = "Bulanan";

        if (judul.isEmpty() || deskripsi.isEmpty() || tanggalMulai == null || tanggalSelesai == null || prioritas == null || kategori == null) {
            new Alert(Alert.AlertType.ERROR, "Semua field harus diisi.").show();
            return;
        }

        if (tanggalSelesai.isBefore(tanggalMulai)) {
            new Alert(Alert.AlertType.ERROR, "Tanggal selesai tidak boleh sebelum tanggal mulai.").show();
            return;
        }

        try {
            TaskManager taskManager = new TaskManager();

            if (taskBeingEdited != null) {
                // Edit task
                taskBeingEdited.setNama(judul);
                taskBeingEdited.setDeskripsi(deskripsi);
                taskBeingEdited.setTanggal(tanggalMulai);
                taskBeingEdited.setTanggalSelesai(tanggalSelesai);
                taskBeingEdited.setPrioritas(prioritas);
                taskBeingEdited.setKategori(kategori);
                taskBeingEdited.setPengulangan(pengulangan);
                taskManager.updateTask(taskBeingEdited);
            } else {
                // Tambah task baru
                int userId = UserSession.getUserId();
                Task newTask = new Task(judul, userId, tanggalMulai, "Belum Dikerjakan", deskripsi, tanggalSelesai, prioritas, kategori);
                newTask.setPengulangan(pengulangan);
                taskManager.createTask(newTask);
            }

            goToDashboard();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Gagal menyimpan ke database.").show();
        }
    }

    @FXML
    public void handleBatal(ActionEvent event) {
        judulField.clear();
        deskripsiArea.clear();
        tanggalMulaiPicker.setValue(null);
        tanggalSelesaiPicker.setValue(null);
        prioritasComboBox.setValue(null);
        kategoriComboBox.setValue(null);
        rbTidak.setSelected(true);
    }

    public void handleKembali(ActionEvent event) {
        goToDashboard();
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
}
