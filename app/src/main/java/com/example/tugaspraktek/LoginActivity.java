package com.example.tugaspraktek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tugaspraktek.controller.UserController;
import com.example.tugaspraktek.databinding.ActivityLoginBinding;
import com.example.tugaspraktek.session.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(this);
        UserController userController = new UserController(this);

        binding.signupButton.setOnClickListener(v -> {
            String email = binding.signupEmail.getText().toString();
            String password = binding.signupPassword.getText().toString();
            String confirmPassword = binding.signupConfirmPassword.getText().toString();

            if(validateLogin(email, password, confirmPassword)) {
                if(password.equals(confirmPassword)) {
                    if(userController.login(email, password)) {
                        session.setLogin(true, email);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(LoginActivity.this, "Mohon isi semua data.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    private boolean validateLogin(String email, String password, String confirmPassword) {
        return !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() ;
    }
}