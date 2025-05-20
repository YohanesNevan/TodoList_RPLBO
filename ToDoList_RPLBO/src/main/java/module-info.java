module org.example.todolist_rplbo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;

    opens org.example.todolist_rplbo.Controller to javafx.fxml;

    // Buka akses untuk FXML (misal controller)
    opens org.example.todolist_rplbo.App to javafx.fxml;

    // Tambahkan baris ini agar JavaFX bisa akses class Main
    exports org.example.todolist_rplbo.App;

    // Tambahkan baris ini agar JavaFX bisa akses class Controller
    exports org.example.todolist_rplbo.Controller;

    // Tambahkan baris ini agar JavaFX bisa akses class Util
    exports org.example.todolist_rplbo.Util;

    // Tambahkan baris ini agar JavaFX bisa akses class database
    exports org.example.todolist_rplbo.database;
    

}
