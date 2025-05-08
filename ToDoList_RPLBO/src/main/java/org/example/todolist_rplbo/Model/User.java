package org.example.todolist_rplbo.Model;

public class User {
    private int id;
    private String username;
    private String password;

    public User(String password, String username) {
        this.username = username;
        if(password.length() >= 6) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Panjang password minimal 6 karakter");
        }
    }

    public User(){}

    public String toString() { //buat jadiin string (buat debugging juga)
        return "User {id =" +
                id + ", username = " +
                username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
