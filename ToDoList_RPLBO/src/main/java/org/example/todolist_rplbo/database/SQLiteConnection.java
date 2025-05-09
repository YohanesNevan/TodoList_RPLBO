package org.example.todolist_rplbo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:todolist.db"; // Ganti dengan path ke database SQLite Anda

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
