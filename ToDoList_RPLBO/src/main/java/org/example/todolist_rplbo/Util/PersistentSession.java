package org.example.todolist_rplbo.Util;

import java.io.*;
import java.util.Properties;

public class PersistentSession {
    private static final String SESSION_FILE = "session.properties";

    public static void saveSession(int userId, String username) {
        try (FileOutputStream fos = new FileOutputStream(SESSION_FILE)) {
            Properties props = new Properties();
            props.setProperty("userId", String.valueOf(userId));
            props.setProperty("username", username);
            props.store(fos, "User Session");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getUserId() {
        Properties props = loadProps();
        return Integer.parseInt(props.getProperty("userId", "0"));
    }

    public static String getUsername() {
        Properties props = loadProps();
        return props.getProperty("username", null);
    }

    public static boolean isLoggedIn() {
        Properties props = loadProps();
        return props.getProperty("userId") != null && !props.getProperty("userId").equals("0");
    }

    public static void clearSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) file.delete();
    }

    private static Properties loadProps() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(SESSION_FILE)) {
            props.load(fis);
        } catch (IOException ignored) {}
        return props;
    }
}
