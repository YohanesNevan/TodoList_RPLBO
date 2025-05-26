package org.example.todolist_rplbo.Util;

public class UserSession {
    private static int userId;
    private static String username;
    private static boolean loggedIn = false;

    public static synchronized void startSession(int id, String user) {
        userId = id;
        username = user;
        loggedIn = true;
        PersistentSession.saveSession(id, user); // Tambahkan baris ini
        }

    public static synchronized void endSession() {
        userId = 0;
        username = null;
        loggedIn = false;
        PersistentSession.clearSession(); // Tambahkan baris ini
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

}