package org.example.todolist_rplbo.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.Task;
import org.example.todolist_rplbo.Service.TaskService;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class RiwayatController implements Initializable {
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
    private Label summaryLabel;

    private final TaskService taskService = new TaskService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        colTaskName.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getTitle())
        );

        colTanggalMulai.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getCreatedAt().format(formatter))
        );

        colTanggalSelesai.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getDueDate().format(formatter))
        );

        colStatus.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getStatus())
        );

        List<Task> riwayat = taskService.getRiwayatTasks();
        ObservableList<Task> data = FXCollections.observableArrayList(riwayat);
        riwayatTable.setItems(data);

        summaryLabel.setText("Total tugas selesai: " + riwayat.size());
    }

    @FXML
    private void handleDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/todolist_rplbo/View/dashboard-view.fxml"));
            Stage stage = (Stage) riwayatTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}