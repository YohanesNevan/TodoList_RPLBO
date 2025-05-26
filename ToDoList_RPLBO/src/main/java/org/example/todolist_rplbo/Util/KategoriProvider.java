package org.example.todolist_rplbo.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KategoriProvider {
    private static final ObservableList<String> kategoriList = FXCollections.observableArrayList();

    public static ObservableList<String> getKategoriList() {
        return kategoriList;
    }

    public static void tambahKategori(String kategori) {
        if (!kategoriList.contains(kategori)) {
            kategoriList.add(kategori);
        }
    }

    public static void hapusKategori(String kategori) {
        kategoriList.remove(kategori);
    }

    public static void setAllKategori(ObservableList<String> listBaru) {
        kategoriList.setAll(listBaru);
    }
}
