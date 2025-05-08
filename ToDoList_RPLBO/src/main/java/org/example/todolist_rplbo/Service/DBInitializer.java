package org.example.todolist_rplbo.Service;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import org.example.todolist_rplbo.Service.DBConnection;

public class DBInitializer {

    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                );
            """;

            String createTasks = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    description TEXT,
                    due_date TEXT,
                    priority TEXT,
                    category TEXT,
                    status TEXT,
                    is_repeated INTEGER,
                    repeat_type TEXT,
                    FOREIGN KEY(user_id) REFERENCES users(id)
                );
            """;

            stmt.execute(createUsers);
            stmt.execute(createTasks);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

