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
import javafx.event.ActionEvent;
import org.example.todolist_rplbo.Model.User;
import org.example.todolist_rplbo.Service.UserManager;
import org.example.todolist_rplbo.Util.UserSession;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML
    private void login(ActionEvent event) {
        String userInput = username.getText().trim();
        String passInput = password.getText().trim();

        if (userInput.isEmpty() || passInput.isEmpty()) {
            showAlert("Login Gagal", "Username dan password tidak boleh kosong.");
            return;
        }

        try {
            UserManager userManager = new UserManager();
            User user = userManager.login(userInput, passInput);

            if (user != null) {
                UserSession.startSession(user.getId(), user.getUsername());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Dashboard");
                stage.show();
            } else {
                showAlert("Login Gagal", "Username atau password salah.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Terjadi kesalahan saat mengakses database.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Gagal memuat dashboard.");
        }
    }

    @FXML
    private void showRegisterStage(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/register-view.fxml"));
            Parent registerRoot = fxmlLoader.load();
            Scene registerScene = new Scene(registerRoot);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(registerScene);
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
