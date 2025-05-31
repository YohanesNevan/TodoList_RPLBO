# TodoList_RPLBO

📋 To-Do List App – RPLBO Project
Deskripsi
Proyek ini adalah aplikasi To-Do List sederhana yang dikembangkan menggunakan bahasa pemrograman Java untuk memenuhi tugas mata kuliah Rekayasa Perangkat Lunak Berorientasi Objek (RPLBO). Aplikasi ini menerapkan prinsip-prinsip Object-Oriented Programming (OOP) seperti encapsulation, inheritance, dan composition.

Fitur Utama
✅ Menambahkan tugas baru

📝 Mengedit detail tugas

🗑️ Menghapus tugas

📌 Menandai tugas sebagai selesai/belum selesai

💾 Penyimpanan data secara lokal (opsional: SQLite/JSON)

Teknologi yang Digunakan
Bahasa: Java

GUI: JavaFX,CSS

Build Tool: Maven

Penyimpanan Data: File system atau SQLite



Pembagian Tugas:
Anggota 1 (71230989	- Yohanes Nevan Adventus Wibawa): UI Design dan Implementasi FXML 
Mengatur antarmuka pengguna menggunakan FXML dan CSS
Membuat halaman login, dashboard, dan form untuk menambah atau mengedit tugas
Integrasi antarmuka pengguna dengan controller yang ada
Menambahkan validasi input untuk form tugas dan pengguna

Anggota 2 (71230978	- Jonathan Satriani Gracio Andrianto) : Data Model, Manajemen Tugas, dan Database SQLite
Mengerjakan class Task, User, dan RepeatedTask untuk mendefinisikan model data tugas dan pengguna
Menyiapkan dan mengelola database SQLite (todolist.db), membuat dan menginisialisasi tabel-tabel (seperti users, tasks)
Membuat class SQLiteConnection untuk menangani koneksi database
Menulis operasi CRUD (Create, Read, Update, Delete) untuk tugas di TaskService
Mengintegrasikan aplikasi dengan database (misalnya, menambah tugas baru ke database)

Anggota 3 (71220884	- Aglend Eka Martua): Fitur Pengingat Tugas, Riwayat Tugas, dan Testing
Mengimplementasikan fitur pengingat tugas (notifikasi waktu tertentu sebelum deadline)
Mengelola riwayat tugas yang telah selesai (misalnya, menampilkan tugas yang sudah selesai di UI)
Mengimplementasikan fitur untuk menandai tugas sebagai selesai dan memindahkannya ke riwayat
Menyiapkan testing untuk aplikasi (unit testing untuk CRUD, notifikasi, dan pengingat)
Memastikan aplikasi berjalan dengan baik pada berbagai kondisi dan memberikan feedback untuk perbaikan

Anggota 4 (71230977 - Michael Hosea): Autentikasi Pengguna dan Manajemen Akun
Mengelola login dan registrasi pengguna, termasuk validasi kredensial
Menambahkan kemampuan untuk menyimpan data pengguna ke dalam database (users table)
Mengimplementasikan fitur logout dan switch akun
Memastikan sesi pengguna tetap aktif dan mengelola status login
