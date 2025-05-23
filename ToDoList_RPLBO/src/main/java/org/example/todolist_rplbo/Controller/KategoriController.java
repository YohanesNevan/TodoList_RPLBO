package org.example.todolist_rplbo.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class KategoriController {

    private static final ObservableList<String> kategoriList = FXCollections.observableArrayList();

    @FXML private TextField kategoriField;
    @FXML private ListView<String> kategoriListView;

    @FXML
    private void initialize() {
        kategoriListView.setItems(kategoriList);
//        // Load kategori yang sudah ada
//        kategoriList.addAll("Pekerjaan", "Rumah Tangga", "Belajar");
    }

    @FXML
    private void handleTambahKategori() {
        String newKategori = kategoriField.getText().trim();
        if (!newKategori.isEmpty() && !kategoriList.contains(newKategori)) {
            kategoriList.add(newKategori);
            kategoriField.clear();
        }
    }

    @FXML
    private void handleHapusKategori() {
        String selected = kategoriListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            kategoriList.remove(selected);
        }
    }

    public static ObservableList<String> getKategoriList() {
        return kategoriList;
    }

    public void handleKembali(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
            Parent registerRoot = fxmlLoader.load();
            Scene registerScene = new Scene(registerRoot);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(registerScene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}