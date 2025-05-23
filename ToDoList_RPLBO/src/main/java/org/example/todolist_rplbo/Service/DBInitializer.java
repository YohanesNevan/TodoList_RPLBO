package org.example.todolist_rplbo.Service;

import org.example.todolist_rplbo.Service.SQLiteConnection;

import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
    public static void initializeDatabase() {
        try (Connection conn = SQLiteConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL
                );
            """;

            String createTasks = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    nama TEXT NOT NULL,
                    tanggal_dibuat TEXT NOT NULL,
                    tanggal_selesai TEXT NOT NULL,
                    status TEXT DEFAULT 'Belum Dikerjakan',
                    deskripsi TEXT,
                    prioritas TEXT,
                    kategori TEXT,
                    pengulangan TEXT,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
            """;

            stmt.execute(createUsers);
            stmt.execute(createTasks);

            System.out.println("Database berhasil dibuat dan tabel sudah ada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
