package org.example.todolist_rplbo.Model;

public class Category {
    private int id;
    private String name;
    private int user_id;
    private int is_active;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name, int user_id, int is_active) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.is_active = is_active;
    }

    public int getUser_id() {return user_id;}

    public void setUser_id(int user_id) {this.user_id = user_id;}

    public int getIs_active() {return is_active;}

    public void setIs_active(int is_active) {this.is_active = is_active;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // penting untuk ditampilkan di ComboBox
    }
}
