package org.example.todolist_rplbo.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Model.User;
import org.example.todolist_rplbo.Service.UserManager;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML
    private void register() {
        String usernameText = username.getText().trim();
        String pass = password.getText().trim();


        if (usernameText.isEmpty() || pass.isEmpty()) {
            showAlert("Register Gagal", "Semua field harus diisi.");
            return;
        }

        if (pass.length() < 6) {
            showAlert("Password Lemah", "Password minimal 6 karakter.");
            return;
        }

        try {
            UserManager userManager = new UserManager();
            boolean success = userManager.registerUser(usernameText, pass);

            if (success) {
                showAlert("Sukses", "Akun berhasil dibuat untuk " + usernameText + "!");
                goToLogin();
            } else {
                showAlert("Gagal", "Username sudah digunakan.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat akses database.");
        }
    }

    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) username.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showLoginStage(MouseEvent event) {
        goToLogin();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
