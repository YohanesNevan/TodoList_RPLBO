package org.example.todolist_rplbo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
                            waktu_mulai TEXT,           
                            tanggal_selesai TEXT NOT NULL,
                            waktu_selesai TEXT,         
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
                            name TEXT NOT NULL,
                            user_id INTEGER,
                            is_active INTEGER DEFAULT 1,
                            UNIQUE(name, user_id),
                            FOREIGN KEY (user_id) REFERENCES users(id)
                        );
                    
                    """;

            stmt.execute(createUsers);
            stmt.execute(createCategories);
            stmt.execute(createTasks);

            String[] defaultCategories = {"Work", "Personal", "Study", "Home", "Urgent"};

            for (String category : defaultCategories) {
                String insertCategory = """
                            INSERT INTO categories (name, user_id, is_active)
                            SELECT ?, NULL, 1
                            WHERE NOT EXISTS (
                                SELECT 1 FROM categories WHERE name = ? AND user_id = NULL
                            );
                        """;
                PreparedStatement stmt1 = conn.prepareStatement(insertCategory);
                stmt1.setString(1, category);
                stmt1.setString(2, category);
                stmt1.executeUpdate();
            }

            System.out.println("Database berhasil dibuat dan tabel sudah ada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
