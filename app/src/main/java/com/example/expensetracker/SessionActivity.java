package com.example.expensetracker;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

import com.example.expensetracker.R;

//Importing the fragments
import com.example.expensetracker.ui.DashboardFragment;
import com.example.expensetracker.ui.ExpenseBookFragment;
import com.example.expensetracker.ui.AddBtnFragment;
import com.example.expensetracker.ui.ReportsFragment;
import com.example.expensetracker.ui.UserFragment;

import java.util.Objects;

public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        Objects.requireNonNull(getSupportActionBar()).hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashboardFragment())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_dashboard) {
                    selectedFragment = new DashboardFragment();
                } else if (item.getItemId() == R.id.nav_expense_book) {
                    selectedFragment = new ExpenseBookFragment();
                } else if (item.getItemId() == R.id.nav_add) {
                    selectedFragment = new AddBtnFragment();
                } else if (item.getItemId() == R.id.nav_reports) {
                    selectedFragment = new ReportsFragment();
                } else if (item.getItemId() == R.id.nav_user) {
                    selectedFragment = new UserFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });
    }
}
