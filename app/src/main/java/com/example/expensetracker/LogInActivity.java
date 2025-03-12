package com.example.expensetracker;


import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.SharedPreferences;
import com.example.expensetracker.SignUpActivity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.DatabaseHelper;

import java.util.HashMap;
import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String USER_KEY = "user_key";
    DatabaseHelper dbHelper;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState){
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        TextView emailInput = findViewById(R.id.emailadd);
        TextView passwdInput = findViewById(R.id.password);

        Button SignUpBtn = findViewById(R.id.sign_upp);
        Button LogInBtn = findViewById(R.id.login);

        SignUpBtn.setOnClickListener(v ->{
            Intent switchToSignUp = new Intent(LogInActivity.this,SignUpActivity.class);
            startActivity(switchToSignUp);
        });

        LogInBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String passwd = passwdInput.getText().toString().trim();
            HashMap<String,String> user = dbHelper.validateUser(email, passwd);

            if (user != null){
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("userid", user.get("uid"));
                editor.putString("username", user.get("uname"));
                editor.putString("email", email);
                editor.apply();

                Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogInActivity.this, SessionActivity.class)); // Redirect to ExpenseActivity
                finish();
            } else {
                Toast.makeText(LogInActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }


}