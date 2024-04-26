package com.example.tugaspraktek;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tugaspraktek.adapter.BookAdapter;
import com.example.tugaspraktek.controller.BookController;
import com.example.tugaspraktek.controller.UserController;
import com.example.tugaspraktek.databinding.FragmentHomeBinding;
import com.example.tugaspraktek.databinding.FragmentProfileBinding;
import com.example.tugaspraktek.helper.DatabaseHelper;
import com.example.tugaspraktek.model.Book;
import com.example.tugaspraktek.model.User;
import com.example.tugaspraktek.session.SessionManager;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SessionManager session;
    private UserController userController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ListView listView = binding.lvBook;
        BookController bookController = new BookController(getActivity());


        session = new SessionManager(getActivity());
        userController = new UserController(getActivity());

        String email = session.getUserEmail();

        if (email != null) {
            User user = userController.getProfilUser(email);
            if (user != null) {
                binding.tvName.setText("Selamat datang, "+user.getName());
            } else {
                Toast.makeText(getActivity(), "User tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Error: No user email found in session", Toast.LENGTH_SHORT).show();
        }

        List<Book> books = bookController.getAllBooks();
        BookAdapter adapter = new BookAdapter(getActivity(), R.layout.item_book, books);
        listView.setAdapter(adapter);

        return view;
    }
}