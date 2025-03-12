package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        String userId = sharedPreferences.getString("userid","");
        String username = sharedPreferences.getString("username","");
        String email = sharedPreferences.getString("email","");

        if (isLoggedIn || userId.isEmpty() || username.isEmpty() || email.isEmpty()){
            Intent toLogin = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(toLogin);
        }else{
            Intent toSession = new Intent(MainActivity.this, SessionActivity.class);
            startActivity(toSession);
        }

        finish();
    }
}
