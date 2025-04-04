package com.example.expensetracker.ui.book;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.DatabaseHelper;

public class ViewExpenseFragment extends Fragment{
    private ExpenseBookViewModel expenseBookViewModel;
    private LinearLayout noExpense, yesExpense;
    private ActivityResultLauncher<Intent> editExpenseLauncher;

    private RecyclerView recyclerView;
    private ExpenseBookAdapter adapter;
    private Cursor cur;

    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_view_expenses, container, false);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUserid = sharedPref.getString("userid","");

        expenseBookViewModel = new ViewModelProvider(this).get(ExpenseBookViewModel.class);

        noExpense = view.findViewById(R.id.expense_no_table);
        yesExpense = view.findViewById(R.id.expense_yes_table);

        recyclerView = view.findViewById(R.id.exp_book_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cur = dbHelper.viewExpenses(currentUserid);

        expenseBookViewModel.getExpenseBookData().observe(getViewLifecycleOwner(), expenses -> {
            if (expenses != null && expenses.getCount() > 0){
                noExpense.setVisibility(View.GONE);
                yesExpense.setVisibility(View.VISIBLE);
                adapter.swapCursor(expenses);
            } else {
                noExpense.setVisibility(View.VISIBLE);
                yesExpense.setVisibility(View.GONE);
            }
        });


        adapter = new ExpenseBookAdapter(requireContext(), cur, dbHelper, currentUserid, intent -> editExpenseLauncher.launch(intent));
        recyclerView.setAdapter(adapter);

        expenseBookViewModel.loadExpenseBookData();

        editExpenseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String editedId = result.getData().getStringExtra("editedExpenseId");
                        Log.d("ViewExpenseFragment", "Edited ID: " + editedId);
                        Cursor newCursor = dbHelper.viewExpenses(currentUserid);
                        adapter.swapCursor(newCursor);
                    }
                }
        );

        return view;
    }

}