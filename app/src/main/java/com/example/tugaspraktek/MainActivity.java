package com.example.tugaspraktek;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.tugaspraktek.R;

import com.example.tugaspraktek.databinding.ActivityMainBinding;
import com.example.tugaspraktek.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new SessionManager(getApplicationContext());

        replaceFragment(new HomeFragment());
        binding.bottomNavBar.setBackground(null);

        binding.bottomNavBar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            }else if (itemId == R.id.add) {
                replaceFragment(new AddFragment());
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });


        String fragment = getIntent().getStringExtra("FRAGMENT");
        if (fragment != null && fragment.equals("HomeFragment")) {
            displayHomeFragment();
        }
    }

    private void displayHomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, new HomeFragment())
                .commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}