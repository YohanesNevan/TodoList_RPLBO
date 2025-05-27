package org.example.todolist_rplbo.App;
//

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import org.example.todolist_rplbo.database.DBInitializer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.todolist_rplbo.Util.PersistentSession;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DBInitializer.initializeDatabase();
        FXMLLoader fxmlLoader;
        if (PersistentSession.isLoggedIn()) {
            fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/dashboard-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/todolist_rplbo/FXML/login-view.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(PersistentSession.isLoggedIn() ? "Dashboard" : "Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBInitializer.initializeDatabase();
        launch();
    }
}


