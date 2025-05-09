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

// Connection to SQLite database - Maik
import org.example.todolist_rplbo.database.SQLiteConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private void register() {
        String nameText = name.getText();
        String user = username.getText();
        String pass = password.getText();

        // Simpan data ke database / file (dummy validasi dulu)
        if (!nameText.isEmpty() && !user.isEmpty() && !pass.isEmpty()) {
            try (Connection conn = SQLiteConnection.getConnection()) {
            String query = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nameText);
            stmt.setString(2, user);
            stmt.setString(3, pass); // NOTE: sebaiknya pakai hashing untuk password nanti
        
            stmt.executeUpdate();
            showAlert("Register Successful", "Account created for " + user + "!");

            // Arahkan ke login setelah berhasil register
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) name.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();

            } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                showAlert("Register Failed", "Username already exists.");
            } else {
                e.printStackTrace();
                showAlert("Register Failed", "Database error occurred.");
            }
            } catch (IOException e) {
            e.printStackTrace();
            showAlert("Register Failed", "Error loading login page.");
            }
        } else {
            showAlert("Register Failed", "Please fill all fields.");
        }
        }

    @FXML
    private void showLoginStage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
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


