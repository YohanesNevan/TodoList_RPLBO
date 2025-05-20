package org.example.todolist_rplbo.App;
//

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import org.example.todolist_rplbo.database.DBInitializer; // Adjust the package path if needed
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Initialize the database (uncomment if needed)
        DBInitializer.initializeDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


