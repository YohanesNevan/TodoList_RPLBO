package org.example.todolist_rplbo.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:../ToDoList_RPLBO/todolist.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
