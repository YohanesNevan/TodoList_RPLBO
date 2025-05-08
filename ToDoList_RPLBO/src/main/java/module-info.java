module org.example.todolist_rplbo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.todolist_rplbo to javafx.fxml;
    exports org.example.todolist_rplbo;
}