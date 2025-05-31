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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.Service.TaskManager;
import org.example.todolist_rplbo.Util.UserSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Predicate;

public class DashboardController {

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> colNama;

    @FXML
    private TableColumn<Task, String> colTanggal;

    @FXML
    public TableColumn<Task, String> colTanggalSelesai;

    @FXML
    private TableColumn<Task, String> colStatus;

    @FXML
    private TableColumn<Task, String> colAksi;

    @FXML
    private TableColumn<Task, String> colPrioritas;

    @FXML
    private TableColumn<Task, String> colKategori;

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
    }

    @FXML
    private void handleRiwayat() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/riwayat-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
                    UserSession.endSession();
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

    @FXML
    private void handleTambahTugas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/tambahtugas-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleTambahKategori(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/kategori-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        colNama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNama()));

        // Format deadline: "tglMulai jamMulai - tglSelesai jamSelesai"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        colTanggalSelesai.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            String deadline = "-";
            if (task.getWaktuMulai() != null && task.getWaktuSelesai() != null) {
                deadline = task.getWaktuMulai().format(formatter) + " - " + task.getWaktuSelesai().format(formatter);
            } else if (task.getWaktuSelesai() != null) {
                deadline = task.getWaktuSelesai().format(formatter);
            }
            return new SimpleStringProperty(deadline);
        });

        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colPrioritas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrioritas()));
        colKategori.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKategori()));

        colAksi.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Hapus");
            private final Button selesaiButton = new Button("Selesai");
            private final Label repetitionLabel = new Label();

            {
                editButton.setStyle("-fx-background-color: gold; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
                selesaiButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
                repetitionLabel.setStyle("-fx-font-weight: bold; -fx-padding: 0 5;");

                editButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    handleEdit(task);
                });

                deleteButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());

                    // Show confirmation dialog
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Konfirmasi Hapus");
                    alert.setHeaderText("Hapus Tugas");
                    alert.setContentText("Anda yakin ingin menghapus tugas '" + task.getNama() + "'?");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                TaskManager taskManager = new TaskManager();
                                if (taskManager.deleteTask(task.getId())) {
                                    // Remove from the underlying list
                                    ObservableList<Task> sourceList = (ObservableList<Task>) filteredTasks.getSource();
                                    sourceList.remove(task);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                showAlert("Error", "Gagal menghapus tugas: " + e.getMessage());
                            }
                        }
                    });
                });

                selesaiButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());

                    // Tampilkan dialog konfirmasi
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle("Konfirmasi");
                    confirmAlert.setHeaderText("Tandai Tugas Selesai");
                    confirmAlert.setContentText("Anda yakin ingin menandai tugas '" + task.getNama() + "' sebagai selesai?");

                    confirmAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                TaskManager taskManager = new TaskManager();
                                // Panggil method untuk update di database
                                boolean success = taskManager.markTaskAsSelesai(task.getId());

                                if (success) {
                                    // Update UI hanya jika berhasil di database
                                    task.setStatus("Selesai");
                                    task.setTanggalSelesai(LocalDate.now());
                                    taskTable.refresh();

                                    new Alert(Alert.AlertType.INFORMATION, "Tugas berhasil ditandai sebagai selesai!").show();

                                    // Optional: Reload data dari database untuk memastikan konsistensi
                                    reloadTasksFromDatabase();
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "Gagal memperbarui status tugas di database.").show();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                new Alert(Alert.AlertType.ERROR, "Terjadi kesalahan: " + e.getMessage()).show();
                            }
                        }
                    });
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Task task = getTableView().getItems().get(getIndex());

                    HBox actionBox = new HBox(5); // selalu buat baru agar tidak reuse salah
                    actionBox.setStyle("-fx-alignment: CENTER_LEFT;");

                    Label repetitionLabel = new Label(); // pastikan label selalu dibuat ulang
                    // Tambahkan label pengulangan jika ada
                    if (task.getPengulangan() != null) {
                        switch (task.getPengulangan()) {
                            case "Harian":
                                repetitionLabel.setText("🔄(Harian)");
                                repetitionLabel.setTooltip(new Tooltip("Tugas Harian"));
                                break;
                            case "Mingguan":
                                repetitionLabel.setText("🔄(Mingguan)");
                                repetitionLabel.setTooltip(new Tooltip("Tugas Mingguan"));
                                break;
                            case "Bulanan":
                                repetitionLabel.setText("🔄(Bulanan)");
                                repetitionLabel.setTooltip(new Tooltip("Tugas Bulanan"));
                                break;
                            default:
                                repetitionLabel.setText("");
                        }
                    } else {
                        repetitionLabel.setText("");
                    }

                    String status = task.getStatus();
                    boolean isSelesai = "Selesai".equalsIgnoreCase(status);
                    boolean isTerlambat = task.getWaktuSelesai() != null && task.getWaktuSelesai().isBefore(LocalDateTime.now()) && !isSelesai;

                    if (isSelesai) {
                        Label statusLabel = new Label("Selesai");
                        statusLabel.setStyle("-fx-text-fill: green; -fx-font-style: italic;");
                        actionBox.getChildren().addAll(deleteButton, statusLabel);
                    } else if (isTerlambat) {
                        Label statusLabel = new Label("Terlambat");
                        statusLabel.setStyle("-fx-text-fill: red; -fx-font-style: italic;");
                        actionBox.getChildren().addAll(deleteButton, statusLabel);
                    } else {
                        actionBox.getChildren().addAll(editButton, deleteButton, selesaiButton);
                    }

                    // Tambahkan label pengulangan jika ada (di semua kondisi)
                    if (!repetitionLabel.getText().isEmpty()) {
                        actionBox.getChildren().add(repetitionLabel);
                    }

                    setGraphic(actionBox);
                }
            }
        });

        try {
            TaskManager taskManager = new TaskManager();
            int userId = UserSession.getUserId();
            filteredTasks = new FilteredList<>(FXCollections.observableArrayList(taskManager.getAllTasksByUser(userId)), p -> true);
            taskTable.setItems(filteredTasks);

            // Check for overdue tasks
            for (Task task : filteredTasks) {
                if (!"Selesai".equalsIgnoreCase(task.getStatus())) {
                    LocalDate deadline = task.getTanggalSelesaiAsLocalDate();
                    if (deadline != null && LocalDate.now().isAfter(deadline)) {
                        task.setStatus("Terlambat");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (searchBox != null) {
            searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredTasks.setPredicate(createTaskPredicate(newValue));
            });
        }

        taskTable.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Task clickedTask = row.getItem();
                    showTaskDetail(clickedTask);
                }
            });
            return row;
        });
    }

    private void reloadTasksFromDatabase() throws SQLException {
        TaskManager taskManager = new TaskManager();
        int userId = UserSession.getUserId();
        filteredTasks = new FilteredList<>(FXCollections.observableArrayList(taskManager.getAllTasksByUser(userId)), p -> true);
        taskTable.setItems(filteredTasks);
    }

    private void showTaskDetail(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/detail-view.fxml"));
            Parent root = loader.load();
            DetailController controller = loader.getController();
            controller.setTask(task);
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Predicate<Task> createTaskPredicate(String searchText) {
        return task -> {
            if (searchText == null || searchText.isEmpty()) return true;
            String lower = searchText.toLowerCase();
            return task.getNama().toLowerCase().contains(lower)
                    || task.getTanggalSelesaiString().toLowerCase().contains(lower)
                    || task.getStatus().toLowerCase().contains(lower);
        };
    }

    @FXML
    public void bersihkansearch(ActionEvent event) {
        if (searchBox != null) {
            searchBox.clear();
        }
    }

    private void handleEdit(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/edittugas-view.fxml"));
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