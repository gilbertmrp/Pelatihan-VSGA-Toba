package com.example.tugaspraktek;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tugaspraktek.controller.UserController;
import com.example.tugaspraktek.databinding.FragmentProfileBinding;
import com.example.tugaspraktek.model.User;
import com.example.tugaspraktek.session.SessionManager;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private SessionManager session;
    private UserController userController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        session = new SessionManager(getActivity());
        userController = new UserController(getActivity());

        updateProfileView();

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void updateProfileView() {
        String email = session.getUserEmail();
        if (email != null) {
            User user = userController.getProfilUser(email);
            if (user != null) {
                binding.tvName.setText(user.getName());
                binding.tvFullName.setText(user.getName());
                binding.tvEmail.setText(user.getEmail());
            } else {
                Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Error: No user email found in session", Toast.LENGTH_SHORT).show();
        }
    }
}