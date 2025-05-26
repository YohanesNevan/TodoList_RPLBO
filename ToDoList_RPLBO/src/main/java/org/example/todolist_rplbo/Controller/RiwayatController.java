package org.example.todolist_rplbo.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.Service.TaskManager;
import org.example.todolist_rplbo.Util.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.Predicate;

public class RiwayatController {

    @FXML
    private TableView<Task> riwayatTable;

    @FXML
    private TableColumn<Task, String> colTaskName;

    @FXML
    private TableColumn<Task, String> colTanggalMulai;

    @FXML
    private TableColumn<Task, String> colTanggalSelesai;

    @FXML
    private TableColumn<Task, String> colStatus;

    @FXML
    private TableColumn<Task, String> colPrioritas;

    @FXML
    private TableColumn<Task, String> colKategori;

    @FXML
    private Label summaryLabel;

    @FXML
    private TextField searchBox;

    private FilteredList<Task> filteredTasks;

    @FXML
    private void initialize() {
        // Configure columns to match Task model
        colTaskName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNama()));
        colTanggalMulai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTanggalMulaiString()));
        colTanggalSelesai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTanggalSelesaiString()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colPrioritas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrioritas()));
        colKategori.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKategori()));

        try {
            // Get tasks from TaskManager instead of TaskData
            TaskManager taskManager = new TaskManager();
            int userId = UserSession.getUserId();
            ObservableList<Task> allTasks = FXCollections.observableArrayList(taskManager.getAllTasksByUser(userId));

            // Filter only completed or overdue tasks
            ObservableList<Task> riwayatTasks = allTasks.filtered(task ->
                    "Selesai".equalsIgnoreCase(task.getStatus()) ||
                            "Terlambat".equalsIgnoreCase(task.getStatus())
            );

            // Check for overdue tasks
            for (Task task : riwayatTasks) {
                if (!"Selesai".equalsIgnoreCase(task.getStatus())) {
                    LocalDate deadline = task.getTanggalSelesaiAsLocalDate();
                    if (deadline != null && LocalDate.now().isAfter(deadline)) {
                        task.setStatus("Terlambat");
                        // You might want to update this in the database too
                    }
                }
            }

            filteredTasks = new FilteredList<>(riwayatTasks, p -> true);
            riwayatTable.setItems(filteredTasks);

            // Configure search
            if (searchBox != null) {
                searchBox.textProperty().addListener((observable, oldValue, newValue) ->
                        filteredTasks.setPredicate(createTaskPredicate(newValue))
                );
            }

            summaryLabel.setText("Jumlah tugas riwayat: " + riwayatTasks.size());
        } catch (SQLException e) {
            showAlert("Database Error", "Gagal memuat data riwayat: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Predicate<Task> createTaskPredicate(String searchText) {
        return task -> {
            if (searchText == null || searchText.isEmpty()) return true;

            String lowerCaseFilter = searchText.toLowerCase();
            return task.getNama().toLowerCase().contains(lowerCaseFilter)
                    || (task.getTanggalSelesaiString() != null && task.getTanggalSelesaiString().toLowerCase().contains(lowerCaseFilter))
                    || task.getStatus().toLowerCase().contains(lowerCaseFilter)
                    || (task.getPrioritas() != null && task.getPrioritas().toLowerCase().contains(lowerCaseFilter))
                    || (task.getKategori() != null && task.getKategori().toLowerCase().contains(lowerCaseFilter));
        };
    }

    @FXML
    private void bersihkansearch(ActionEvent event) {
        if (searchBox != null) {
            searchBox.clear();
        }
    }

    @FXML
    private void handleDashboard() {
        navigateTo("/org/example/todolist_rplbo/FXML/dashboard-view.fxml");
    }

    @FXML
    private void handleProfileAkun() {
        navigateTo("/org/example/todolist_rplbo/FXML/profile-view.fxml");
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText("Anda yakin ingin logout?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                UserSession.endSession();
                navigateTo("/org/example/todolist_rplbo/FXML/login-view.fxml");
            }
        });
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) riwayatTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Navigation Error", "Gagal memuat halaman: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}