package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.LogInActivity;
import com.example.expensetracker.R;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();

        EditText usernameInput = findViewById(R.id.uname);
        EditText emailInput = findViewById(R.id.email);
        EditText phoneInput = findViewById(R.id.phone);
        EditText passwordInput = findViewById(R.id.passwd);
        EditText confpsswdInput = findViewById(R.id.confpsswd);
        dbHelper = new DatabaseHelper(this);

        Button loginBtn = findViewById(R.id.log_inn);
        Button signUpBtn = findViewById(R.id.sign_uppp);

        signUpBtn.setOnClickListener(v -> {
            String uname = usernameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim();
            String psswd = passwordInput.getText().toString().trim();
            String confpsswd = confpsswdInput.getText().toString().trim();

            if (uname.isEmpty() || email.isEmpty() || phone.isEmpty() || psswd.isEmpty() || confpsswd.isEmpty()){
                Toast.makeText(SignUpActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            }else if (!uname.matches("[a-zA-Z]+")) {
                Toast.makeText(SignUpActivity.this, "Username should only contain alphabets", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(SignUpActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
            } else if (psswd.length() < 3) {
                Toast.makeText(SignUpActivity.this, "Password must be at least 3 characters long", Toast.LENGTH_SHORT).show();
            } else {
                // Insert user into the database
                boolean isUserInserted = dbHelper.newUser(uname,email,phone,psswd);

                if (isUserInserted) {
                    Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    finish();  // Close the SignUpActivity after successful sign-up
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBtn.setOnClickListener(v ->{
            Intent switchToLogIn = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(switchToLogIn);
        });

    }
}
