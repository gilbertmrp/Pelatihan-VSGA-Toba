package com.example.tugaspraktek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tugaspraktek.controller.UserController;
import com.example.tugaspraktek.databinding.ActivityRegisterBinding;
import com.example.tugaspraktek.model.User;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserController userController = new UserController(this);

        binding.signinButton.setOnClickListener(v -> {
            String fullname = binding.fullName.getText().toString();
            String email = binding.signinEmail.getText().toString();
            String password = binding.signinPassword.getText().toString();
            String confirmPassword = binding.signinConfirmPassword.getText().toString();

            if (validateRegistration(fullname, email, password, confirmPassword)) {
                if (password.equals(confirmPassword)) {
                    User newUser = new User(fullname, email, password);
                    if (userController.register(newUser)) {
                        Toast.makeText(RegisterActivity.this, "Pnedaftaran Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registrasi gagal. Email telah  tersedia", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(RegisterActivity.this, "Harap isi semua field diatas", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvMasuk.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateRegistration(String fullName, String email, String password, String confirmPassword) {
        return !fullName.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword);
    }
}