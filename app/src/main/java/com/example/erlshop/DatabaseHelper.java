package com.example.erlshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SignLog.db";
    private static final int DATABASE_VERSION = 3; // Incremented for new columns
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NIP = "nip";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DIVISION = "division";
    private static final String COLUMN_PROFILE_PIC = "profile_pic";
    private static final String TABLE_LINKS = "links";
    private static final String COLUMN_LINK_ID = "id";
    private static final String COLUMN_URL_PRODUK = "url_produk";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE =
                "CREATE TABLE " + TABLE_USERS + " (" +
                        COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_NIP + " TEXT, " +
                        COLUMN_GENDER + " TEXT, " +
                        COLUMN_EMAIL + " TEXT UNIQUE, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_DIVISION + " TEXT, " +
                        COLUMN_PROFILE_PIC + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_LINKS_TABLE =
                "CREATE TABLE " + TABLE_LINKS + " (" +
                        COLUMN_LINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_URL_PRODUK + " TEXT UNIQUE)"; // Ensure URL is unique
        db.execSQL(CREATE_LINKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINKS);
        onCreate(db);
    }

    public boolean insertData(String name, String nip, String gender, String email, String password, String division, String profilePicUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_NIP, nip);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_DIVISION, division);
        contentValues.put(COLUMN_PROFILE_PIC, profilePicUri);

        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getUserById(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, null, COLUMN_USER_ID + " = ?", new String[]{userId}, null, null, null);
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

    // Fetch all links from the database
    public List<String> getAllLinks() {
        List<String> links = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_URL_PRODUK + " FROM " + TABLE_LINKS, null);

        if (cursor.moveToFirst()) {
            do {
                String url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL_PRODUK));
                links.add(url);
                Log.d("DatabaseHelper", "Retrieved URL: " + url);
            } while (cursor.moveToNext());
        } else {
            Log.d("DatabaseHelper", "No URLs found in database.");
        }

        cursor.close();
        db.close();
        return links;
    }


    public void addLink(String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL_PRODUK, link); // Correct column reference
        db.insert(TABLE_LINKS, null, values); // Insert into the correct table
        db.close();
    }

    public void clearLinks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_LINKS); // Use the correct table name
        db.close();
    }
    // Inside DatabaseHelper class
    public Cursor getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
    }




    // Function to check if a URL already exists in the links table
    public boolean doesUrlExist(String url_produk) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LINKS + " WHERE " + COLUMN_URL_PRODUK + " = ?", new String[]{url_produk});
        boolean exists = cursor.getCount() > 0; // Check if cursor has any results
        cursor.close();
        db.close();
        return exists;
    }
}
