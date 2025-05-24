package org.example.todolist_rplbo.Controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.function.Predicate;

public class DashboardController {

    @FXML
    private TableView<Task> taskTable; // Change the type to Task

    @FXML
    private TableColumn<Task, String> colNama; // Set column type to Task and String

    @FXML
    private TableColumn<Task, String> colTanggal; // Set column type to Task and String

    @FXML
    public TableColumn<Task, String> colTanggalSelesai; // Set column type to Task and String

    @FXML
    private TableColumn<Task, String> colStatus; // Set column type to Task and String

    @FXML
    private TableColumn<Task, String> colAksi; // Set column type to Task and String

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
                    org.example.todolist_rplbo.Util.UserSession.endSession();
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
    public void handleTambahKategori(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/kategori-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) taskTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // You might want to print the stack trace to get more information about the error.
        }
    }

    @FXML
    private void initialize() {
        colNama.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNama()));
        colTanggalSelesai.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTanggalSelesaiString()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        colPrioritas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrioritas()));
        colKategori.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKategori()));

        colAksi.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Hapus");
            //            private final Button detailButton = new Button("Detail");
            private final Label repetitionLabel = new Label();

            {
                editButton.setStyle("-fx-background-color: gold; -fx-text-fill: white; -fx-font-weight: bold;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
//                detailButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");

                // Style repetition label
                repetitionLabel.setStyle("-fx-font-weight: bold; -fx-padding: 0 5;");

                editButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    handleEdit(task);
                });

                editButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    handleEdit(task);
                });
                deleteButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    try {
                        TaskManager taskManager = new TaskManager();
                        if (taskManager.deleteTask(task.getId())) {
                            taskTable.getItems().remove(task);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

//                taskTable.setRowFactory(tv -> {
//                    TableRow<Task> row = new TableRow<>();
//                    row.setOnMouseClicked(event -> {
//                        if (!row.isEmpty() && event.getClickCount() == 1) {
//                            Task clickedTask = row.getItem();
//                            showTaskDetail(clickedTask);
//                        }
//                    });
//                    return row;
//                });

            }

//            private void showTaskDetail(Task task) {
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/detail-view.fxml"));
//                    Parent root = loader.load();
//
//                    DetailController controller = loader.getController();
//                    controller.setTask(task);
//
//                    Stage detailStage = new Stage();
//                    detailStage.setTitle("Detail Tugas");
//                    detailStage.setScene(new Scene(root));
//                    detailStage.show();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }


            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Task task = getTableView().getItems().get(getIndex());
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

                    HBox container = new HBox(5, editButton, deleteButton, repetitionLabel);
                    setGraphic(container);
                }
            }


        });

        try {
            TaskManager taskManager = new TaskManager();
            int userId = UserSession.getUserId();
            filteredTasks = new FilteredList<>(FXCollections.observableArrayList(taskManager.getAllTasksByUser(userId)), p -> true);
            taskTable.setItems(filteredTasks);
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

