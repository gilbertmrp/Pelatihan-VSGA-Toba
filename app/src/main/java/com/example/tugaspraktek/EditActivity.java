package com.example.tugaspraktek;

import android.content.Intent;
import android.os.Bundle;

import com.example.tugaspraktek.controller.BookController;
import com.example.tugaspraktek.model.Book;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tugaspraktek.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {
    private ActivityEditBinding binding;
    private BookController bookController;
    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookController = new BookController(this);
        int bookId = getIntent().getIntExtra("BOOK_ID", -1);
        currentBook = bookController.getBookById(bookId);

        if (currentBook != null) {
            binding.etJudul.setText(currentBook.getTitle());
            binding.etPencipta.setText(currentBook.getAuthor());
            binding.etTahun.setText(String.valueOf(currentBook.getTahun()));
        } else {
            Toast.makeText(this, "Error: Book not found", Toast.LENGTH_LONG).show();
        }

        binding.btnSimpan.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        currentBook.setTitle(binding.etJudul.getText().toString());
        currentBook.setAuthor(binding.etPencipta.getText().toString());
        currentBook.setTahun(Integer.parseInt(binding.etTahun.getText().toString()));

        if (bookController.updateBook(currentBook)) {
            Toast.makeText(this, "Buku berhasil di edit", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditActivity.this, MainActivity.class);
            intent.putExtra("FRAGMENT", "HomeFragment");
            startActivity(intent);
        } else {
            Toast.makeText(this, "Buku gagal di edit", Toast.LENGTH_SHORT).show();
        }
    }
}