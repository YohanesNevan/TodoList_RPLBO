package org.example.todolist_rplbo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.Category;
import org.example.todolist_rplbo.Service.CategoryManager;
import org.example.todolist_rplbo.Util.KategoriProvider;
import org.example.todolist_rplbo.Util.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class KategoriController {

    private CategoryManager categoryManager;

    @FXML private TextField kategoriField;
    @FXML private ListView<String> kategoriListView;

    @FXML
    private void initialize() {
        try {
            categoryManager = new CategoryManager();
            List<Category> categories = categoryManager.getAllCategories(UserSession.getUserId());

            KategoriProvider.getKategoriList().clear();
            for (Category c : categories) {
                KategoriProvider.tambahKategori(c.getName());
            }

            kategoriListView.setItems(KategoriProvider.getKategoriList());

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Gagal mengambil data kategori.");
        }
    }

    @FXML
    private void handleTambahKategori() {
        String newKategori = kategoriField.getText().trim();

        if (!newKategori.isEmpty() && !KategoriProvider.getKategoriList().contains(newKategori)) {
            boolean success = categoryManager.addCategory(newKategori, UserSession.getUserId());
            if (success) {
                KategoriProvider.tambahKategori(newKategori);
                new Alert(Alert.AlertType.INFORMATION, "Kategori berhasil ditambahkan!").show();
                kategoriField.clear();
            } else {
                showAlert("Gagal", "Kategori mungkin sudah ada.");
            }
        }
    }

    @FXML
    private void handleHapusKategori() {
        String selected = kategoriListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            List<Category> all = categoryManager.getAllCategories(UserSession.getUserId());
            Category target = all.stream().filter(c -> c.getName().equals(selected)).findFirst().orElse(null);

            if (target != null && categoryManager.deleteCategory(target.getId())) {
                KategoriProvider.hapusKategori(selected);
            } else {
                showAlert("Gagal", "Kategori gagal dihapus.");
            }
        }
    }

    public void handleKembali(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
