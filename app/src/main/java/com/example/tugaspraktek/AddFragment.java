package com.example.tugaspraktek;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tugaspraktek.controller.BookController;
import com.example.tugaspraktek.databinding.FragmentAddBinding;
import com.example.tugaspraktek.model.Book;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    private BookController bookController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        bookController = new BookController(getActivity());

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });

        return view;
    }

    public void saveBook() {
        String title = binding.etJudul.getText().toString();
        String author = binding.etPencipta.getText().toString();
        String tahun = binding.etTahun.getText().toString();

        if (title.isEmpty() || author.isEmpty() || tahun.isEmpty()) {
            Toast.makeText(getActivity(), "Mohon di isi semua inputan", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(tahun);
        Book newBook = new Book(title, author, year);

        if(bookController.add(newBook)) {
            Toast.makeText(getActivity(), "Buku berhasil di tambah", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Buku gagal di tambah", Toast.LENGTH_SHORT).show();
        }
    }
}