package org.example.todolist_rplbo.Controller;



import java.time.LocalDate;

public class Task {
    private String nama;
    private String tanggal;
    private String status;

    public Task(String nama, String tanggal, String status) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
    }

    // Getter dan Setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

