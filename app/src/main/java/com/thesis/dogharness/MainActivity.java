package com.thesis.dogharness;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.MakMoinee.library.dialogs.MyDialog;
import com.thesis.dogharness.databinding.ActivityMainBinding;
import com.thesis.dogharness.models.Users;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MyDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pDialog = new MyDialog(MainActivity.this);
        setListeners();
    }

    private void setListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            pDialog.show();
            String email = binding.editEmail.getText().toString().trim();
            String password = binding.editPassword.getText().toString().trim();

            if (email.equals("") || password.equals("")) {
                Toast.makeText(MainActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
            } else {
                Users users = new Users.UserBuilder()
                        .setEmail(email)
                        .setPassword(password)
                        .build();
            }
            pDialog.dismiss();
        });
    }
}