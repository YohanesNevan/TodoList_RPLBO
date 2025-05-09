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
import java.io.IOException;

// Connection to SQLite database - Maik
import org.example.todolist_rplbo.database.SQLiteConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private void login(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        // Connect to the database
        try (Connection connection = SQLiteConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            // Execute the query
            if (preparedStatement.executeQuery().next()) {
                try {
                    // Load file FXML dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
                    Parent root = loader.load();

                    // Dapatkan stage dari event
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    // Buat scene baru dan set ke stage
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Dashboard");
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to load dashboard.");
                }
            } else {
                showAlert("Login Failed", "Incorrect username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database connection failed.");
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
