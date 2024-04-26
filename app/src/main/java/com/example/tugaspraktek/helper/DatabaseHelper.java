package com.example.tugaspraktek.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String dbName = "tugas_praktek.db";
    public DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   name TEXT, " +
                    "   email TEXT UNIQUE, " +
                    "   password TEXT" +
                    ");";

    private static final String CREATE_TABLE_BOOKS =
            "CREATE TABLE books (" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   title TEXT, " +
                    "   author TEXT, " +
                    "   tahun INTEGER" +
                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS books");
        onCreate(db);
    }
}
