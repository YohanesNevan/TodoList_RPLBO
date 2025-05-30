package org.example.todolist_rplbo.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Task {
    private int id;
    private int userId;
    private String nama;
    private LocalDate tanggal;
    private String status;
    private String deskripsi;
    private LocalDate tanggalSelesai;
    private String prioritas;
    private String kategori;
    private String pengulangan;
    private LocalDateTime waktuMulai;
    private LocalDateTime waktuSelesai;

    public Task(String nama, int userId, LocalDate tanggal, String status, String deskripsi,
                LocalDate tanggalSelesai, String prioritas, String kategori) {
//        this.id = id;
        this.userId = userId;
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
        this.deskripsi = deskripsi;
        this.tanggalSelesai = tanggalSelesai;
        this.prioritas = prioritas;
        this.kategori = kategori;
        this.pengulangan = null;
    }

    public static Task fromResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setUserId(rs.getInt("user_id"));
        task.setNama(rs.getString("nama"));
        task.setTanggal(LocalDate.parse(rs.getString("tanggal_dibuat")));
        task.setTanggalSelesai(LocalDate.parse(rs.getString("tanggal_selesai")));
        // Ambil waktu mulai dan selesai jika ada
        String waktuMulaiStr = rs.getString("waktu_mulai");
        String waktuSelesaiStr = rs.getString("waktu_selesai");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        if (waktuMulaiStr != null && !waktuMulaiStr.isEmpty()) {
            task.setWaktuMulai(LocalDateTime.parse(waktuMulaiStr, formatter));
        }
        if (waktuSelesaiStr != null && !waktuSelesaiStr.isEmpty()) {
            task.setWaktuSelesai(LocalDateTime.parse(waktuSelesaiStr, formatter));
        }
        task.setDeskripsi(rs.getString("deskripsi"));
        task.setStatus(rs.getString("status"));
        task.setPrioritas(rs.getString("prioritas"));
        task.setKategori(rs.getString("kategori"));
        task.setPengulangan(rs.getString("pengulangan"));
        return task;
    }
    public Task() {}

    public LocalDateTime kapanUlang(LocalDateTime currentDate) { // cari kapan tasknya muncul lagi
        switch (pengulangan) {
            case "Harian":
                return currentDate.plusDays(1);
            case "Mingguan":
                return currentDate.plusWeeks(1);
            case "Bulanan":
                return currentDate.plusMonths(1);
            case null :
                return null;
            default:
                return currentDate;
        }
    }


    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPengulangan() {
        return pengulangan;
    }

    public void setPengulangan(String pengulangan) {
        this.pengulangan = pengulangan;
    }

    public String getPrioritas() {
        return prioritas;
    }

    public LocalDate getTanggalSelesaiAsLocalDate() {
        return tanggalSelesai;
    }

    public LocalDate getTanggalMulaiAsLocalDate() {
        return tanggal;
    }


    public void setPrioritas(String prioritas) {
        this.prioritas = prioritas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalMulaiString() {
        return tanggal != null ? tanggal.toString() : "";
    }


    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggalSelesaiString() {
        return tanggalSelesai != null ? tanggalSelesai.toString() : "";
    }

    public void setTanggalSelesai(LocalDate tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    // Tambahkan getter dan setter untuk waktuMulai dan waktuSelesai
    public LocalDateTime getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(LocalDateTime waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public LocalDateTime getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(LocalDateTime waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getWaktuMulaiString() {
        return waktuMulai != null ? waktuMulai.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "";
    }

    public String getWaktuSelesaiString() {
        return waktuSelesai != null ? waktuSelesai.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "";
    }
    
}
