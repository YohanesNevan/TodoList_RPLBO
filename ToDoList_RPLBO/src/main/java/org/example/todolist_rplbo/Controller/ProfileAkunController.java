package org.example.todolist_rplbo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Util.UserSession;

import java.io.IOException;

public class ProfileAkunController {

    @FXML
    private Label namaLabel;

    @FXML
    private void initialize() {
        String username = UserSession.getUsername();
        if (username != null) {
            namaLabel.setText("Halo, " + username);
        } else {
            namaLabel.setText("Halo, pengguna!");
        }
    }

    @FXML
    void handleDashboard(ActionEvent event) {
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
}
