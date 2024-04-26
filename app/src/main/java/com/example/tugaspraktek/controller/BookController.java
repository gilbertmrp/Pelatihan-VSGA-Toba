package com.example.tugaspraktek.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.tugaspraktek.helper.DatabaseHelper;
import com.example.tugaspraktek.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private DatabaseHelper dbHelper;

    public BookController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean add(Book book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", book.getTitle());
        contentValues.put("author", book.getAuthor());
        contentValues.put("tahun", book.getTahun());

        try {
            long result = db.insertOrThrow("books", null, contentValues);
            return result != -1;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM books", null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("author")),
                        cursor.getInt(cursor.getColumnIndex("tahun"))
                );
                book.setId(cursor.getInt(cursor.getColumnIndex("id")));
                books.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return books;
    }

    public Book getBookById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("books", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Book book = null;
        if (cursor != null && cursor.moveToFirst()) {
            book = new Book(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("author")),
                    cursor.getInt(cursor.getColumnIndex("tahun"))
            );
            book.setId(cursor.getInt(cursor.getColumnIndex("id")));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return book;
    }

    public boolean updateBook(Book book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", book.getTitle());
        contentValues.put("author", book.getAuthor());
        contentValues.put("tahun", book.getTahun());

        try {
            int rowsAffected = db.update("books", contentValues, "id = ?", new String[]{String.valueOf(book.getId())});
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deleteBook(int bookId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            int rowsDeleted = db.delete("books", "id = ?", new String[]{String.valueOf(bookId)});
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

}
