    module org.example.todolist_rplbo {
        requires javafx.controls;
        requires javafx.fxml;

        requires org.controlsfx.controls;
        requires org.kordamp.bootstrapfx.core;
        requires java.sql;

        opens org.example.todolist_rplbo to javafx.fxml;
        exports org.example.todolist_rplbo;
    }