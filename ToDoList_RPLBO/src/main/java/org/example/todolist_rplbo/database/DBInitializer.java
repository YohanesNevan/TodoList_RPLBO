package org.example.todolist_rplbo.database;

import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
    public static void initializeDatabase() {
        try (Connection conn = SQLiteConnection.getConnection();
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
                            nama TEXT NOT NULL,
                            tanggal_dibuat TEXT NOT NULL,
                            waktu_mulai TEXT,           -- Tambahkan ini
                            tanggal_selesai TEXT NOT NULL,
                            waktu_selesai TEXT,         -- Tambahkan ini
                            status TEXT DEFAULT 'Belum Dikerjakan',
                            deskripsi TEXT,
                            prioritas TEXT,
                            kategori TEXT,
                            pengulangan TEXT,
                            FOREIGN KEY (user_id) REFERENCES users(id)
                        );
                    """;

            String createCategories = """
                        CREATE TABLE IF NOT EXISTS categories (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL UNIQUE
                        );
                    """;

            stmt.execute(createUsers);
            stmt.execute(createCategories);
            stmt.execute(createTasks);


            System.out.println("Database berhasil dibuat dan tabel sudah ada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
