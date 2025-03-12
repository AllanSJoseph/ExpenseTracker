package com.example.expensetracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.R;

public class DashboardFragment extends Fragment {

    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        SharedPreferences sharedPref = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentusername = sharedPref.getString("username","");


        TextView greetingView = view.findViewById(R.id.dash_greet);
        String greetingText = "Welcome " + currentusername + "!";
        greetingView.setText(greetingText);


        return view;
    }
}
