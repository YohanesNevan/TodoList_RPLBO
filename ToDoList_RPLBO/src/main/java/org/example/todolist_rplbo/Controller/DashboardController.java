package org.example.todolist_rplbo.Controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.Task;

import java.io.IOException;
import java.util.function.Predicate;

public class DashboardController {

    @FXML
    private TableView<Task> taskTable; // Change the type to Task

    @FXML
    private TableColumn<Task, String> colNama; // Set column type to Task and String

    @FXML
    private TableColumn<Task, String> colTanggal; // Set column type to Task and String

    @FXML
    public TableColumn <Task, String> colTanggalSelesai; // Set column type to Task and String

    @FXML
    private TableColumn<Task, String> colStatus; // Set column type to Task and String

    @FXML
    private TableColumn<Task, String> colAksi; // Set column type to Task and String

    @FXML
    private Region spacer;

    @FXML
    private FilteredList<Task> filteredTasks;

    @FXML
    private TextField searchBox;

    // Handler untuk menu sidebar
    @FXML
    private void handleDashboard() {
        System.out.println("Dashboard button clicked");
        // Logika untuk menampilkan dashboard
    }

    @FXML
    private void handleRiwayat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/riwayat-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // You might want to print the stack trace to get more information about the error.
        }
    }

    @FXML
    private void handleProfileAkun() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/profile-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // You might want to print the stack trace to get more information about the error.
        }
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText("Anda yakin ingin logout?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Kembali ke halaman login
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/login-view.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) taskTable.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Login");
                } catch (IOException e) {
                    showAlert("Error", "Gagal kembali ke halaman login");
                }
            }
        });
    }

    // Handler untuk tombol tambah tugas
    @FXML
    private void handleTambahTugas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/tambahtugas-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // You might want to print the stack trace to get more information about the error.
        }

    }

    @FXML
    private void initialize() {
        colNama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colTanggal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));
        colTanggalSelesai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        colAksi.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Hapus");

            {
                editButton.setStyle("-fx-background-color: gold; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

                editButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    handleEdit(task);
                });
                deleteButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    TaskData.removeTask(task);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, editButton, deleteButton));
                }
            }
        });

        // Inisialisasi filtered list
        filteredTasks = new FilteredList<>(TaskData.getTasks(), p -> true);
        taskTable.setItems(filteredTasks); // Hanya panggil sekali

        // Setup listener untuk search box
        if (searchBox != null) {
            searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredTasks.setPredicate(createTaskPredicate(newValue));
            });
        }
    }

    // Pindahkan method createTaskPredicate ke level class
    private Predicate<Task> createTaskPredicate(String searchText) {
        return task -> {
            if (searchText == null || searchText.isEmpty()) return true;

            String lowerCaseFilter = searchText.toLowerCase();
            return task.getName().toLowerCase().contains(lowerCaseFilter) ||
                    task.getDueDate().toLocalDate().toString().contains(lowerCaseFilter) ||
                    task.getStatus().toLowerCase().contains(lowerCaseFilter);
        };




//        taskTable.setItems(TaskData.getTasks());
    }

    @FXML
    public void bersihkansearch(ActionEvent event) {
        if (searchBox != null) {
            searchBox.clear();
        }
    }

    private void handleEdit(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/tambahtugas-view.fxml"));
            Parent root = loader.load();
            TambahTugasController controller = loader.getController();
            controller.setEditMode(task);
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
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

