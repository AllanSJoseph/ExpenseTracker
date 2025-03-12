package com.example.expensetracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;

import com.example.expensetracker.LogInActivity;
import com.example.expensetracker.R;

import com.example.expensetracker.DatabaseHelper;

public class UserFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentuseremail = sharedPref.getString("email", "");

        Cursor cur = dbHelper.getUserDetails(currentuseremail);

        if (cur.moveToFirst()) {
            String username = cur.getString(1);
            String email = cur.getString(2);
            String phone = cur.getString(3);

            TextView usernameTextView = view.findViewById(R.id.displayusername);
            TextView emailTextView = view.findViewById(R.id.displayemail);
            TextView phoneTextView = view.findViewById(R.id.displayphone);

            usernameTextView.setText(username);
            emailTextView.setText(email);
            phoneTextView.setText(phone);
        }


        Button logoutButton = view.findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear SharedPreferences
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Clear stored user data
                editor.apply();

                // Redirect to LogInActivity
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);

                // Close the parent activity to prevent returning
                requireActivity().finish();
            }
        });

        return view;
    }
}
