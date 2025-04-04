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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.DatabaseHelper;

public class ViewIncomeFragment extends Fragment {
    private ExpenseBookViewModel expenseBookViewModel;
    private LinearLayout noIncome, yesIncome;
    private ActivityResultLauncher<Intent> editIncomeLauncher;

    private RecyclerView recyclerView;
    private IncomeBookAdapter adapter;
    private Cursor cur;

    private DatabaseHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_incomes, container, false);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUserid = sharedPref.getString("userid", "");

        expenseBookViewModel = new ViewModelProvider(this).get(ExpenseBookViewModel.class);

        noIncome = view.findViewById(R.id.income_no_table);
        yesIncome = view.findViewById(R.id.income_yes_table);

        recyclerView = view.findViewById(R.id.inc_book_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cur = dbHelper.viewIncomes(currentUserid);

        expenseBookViewModel.getIncomeBookData().observe(getViewLifecycleOwner(), incomes -> {
            if (incomes != null && incomes.getCount() > 0) {
                noIncome.setVisibility(View.GONE);
                yesIncome.setVisibility(View.VISIBLE);
                adapter.swapCursor(incomes);
            } else {
                noIncome.setVisibility(View.VISIBLE);
                yesIncome.setVisibility(View.GONE);
            }
        });

        adapter = new IncomeBookAdapter(requireContext(), cur, dbHelper,currentUserid, intent -> editIncomeLauncher.launch(intent));
        recyclerView.setAdapter(adapter);

        expenseBookViewModel.loadExpenseBookData();

        editIncomeLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String editedId = result.getData().getStringExtra("editedIncomeId");
                        Log.d("ViewIncomeFragment", "Edited ID: " + editedId);
                        Cursor newCursor = dbHelper.viewIncomes(currentUserid);
                        adapter.swapCursor(newCursor);
                    }
                }
        );

        return view;
    }
}
