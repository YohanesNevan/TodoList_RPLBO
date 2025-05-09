package org.example.todolist_rplbo.Controller;

import java.util.UUID;

public class Task {
    private String id;
    private String nama;
    private String tanggal;
    private String status;
    private String deskripsi;
    private String tanggalSelesai;

    public Task(String nama, String tanggal, String status, String deskripsi, String tanggalSelesai) {
        this.id = UUID.randomUUID().toString();
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
        this.deskripsi = deskripsi;
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public String getTanggalSelesai() { return tanggalSelesai; }
    public void setTanggalSelesai(String tanggalSelesai) { this.tanggalSelesai = tanggalSelesai; }
}