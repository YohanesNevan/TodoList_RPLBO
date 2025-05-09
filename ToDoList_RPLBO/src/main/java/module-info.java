module org.example.todolist_rplbo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.todolist_rplbo.Controller to javafx.fxml;

    // Buka akses untuk FXML (misal controller)
    opens org.example.todolist_rplbo.App to javafx.fxml;

    // Tambahkan baris ini agar JavaFX bisa akses class Main
    exports org.example.todolist_rplbo.App;
}
