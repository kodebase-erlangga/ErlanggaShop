package com.example.erlshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SignLog.db";
    private static final int DATABASE_VERSION = 2;
    // Tabel dan kolom untuk tabel "users"
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NIP = "nip";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_LINKS = "links";
    private static final String COLUMN_LINK_ID = "id";
    private static final String COLUMN_URL_PRODUK = "url_produk";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Buat tabel "users"
        String CREATE_USERS_TABLE =
                "CREATE TABLE " + TABLE_USERS + " (" +
                        COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_NIP + " TEXT, " +
                        COLUMN_GENDER + " TEXT, " +
                        COLUMN_EMAIL + " TEXT UNIQUE, " +
                        COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Buat tabel "links"
        String CREATE_LINKS_TABLE =
                "CREATE TABLE " + TABLE_LINKS + " (" +
                        COLUMN_LINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_URL_PRODUK + " TEXT)";
        db.execSQL(CREATE_LINKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Hapus tabel jika ada versi baru, lalu buat tabel kembali
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINKS);
        onCreate(db);
    }

    // Fungsi untuk menyisipkan data pengguna ke tabel "users"
    public boolean insertData(String name, String nip, String gender, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_NIP, nip);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;
    }

    // Fungsi untuk memeriksa apakah email sudah terdaftar
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Fungsi untuk memeriksa apakah email dan password cocok
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Fungsi untuk menyisipkan link URL produk ke tabel "links"
    public void insertLink(String url_produk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL_PRODUK, url_produk);
        db.insert(TABLE_LINKS, null, values);
        db.close();
    }

    // Fungsi untuk mengambil semua link dari tabel "links"
    public List<String> getAllLinks() {
        List<String> links = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_URL_PRODUK + " FROM " + TABLE_LINKS, null);

        if (cursor.moveToFirst()) {
            do {
                String url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_PRODUK));
                links.add(url);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return links;
    }
}
