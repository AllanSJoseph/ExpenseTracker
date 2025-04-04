package com.example.expensetracker.ui;

import android.content.Context;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.ui.dashboard.DashboardViewModel;
import com.example.expensetracker.ui.dashboard.RecentExpenseAdapter;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TextView todayExpenseAmt;
    private TextView incomeBalanceAmt;
    private RecyclerView recentExpList;
    private CardView addExpenseCard, addIncomeCard;
    private LinearLayout noRecentExpense, yesRecentExpense;
    private RecentExpenseAdapter expenseAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        todayExpenseAmt = view.findViewById(R.id.most_recent_amt);
        incomeBalanceAmt = view.findViewById(R.id.most_spent_amt);
        recentExpList = view.findViewById(R.id.recent_exp_list);
        addExpenseCard = view.findViewById(R.id.add_expense_quickAccess);
        addIncomeCard = view.findViewById(R.id.add_income_quickAccess);
        noRecentExpense = view.findViewById(R.id.no_recent_expense);
        yesRecentExpense = view.findViewById(R.id.yes_recent_expense);

        addExpenseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBtnFragment addBtn = new AddBtnFragment();

                // Get FragmentManager from parent activity
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.fragment_container, addBtn);
                transaction.addToBackStack(null);  // Add to back stack
                transaction.commit();
            }
        });

        addIncomeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBtnFragment addBtn = new AddBtnFragment();

                Bundle args = new Bundle();
                args.putInt("PAGE_INDEX", 1);
                addBtn.setArguments(args);

                FragmentTransaction transaction = getActivity().
                        getSupportFragmentManager().
                        beginTransaction();

                transaction.replace(R.id.fragment_container, addBtn);
                transaction.addToBackStack(null);  // Add to back stack
                transaction.commit();
            }
        });


        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        dashboardViewModel.getDashFirstSummary().observe(getViewLifecycleOwner(), summary -> {
            String todayExpense = "₹ " + summary.get("expSummary");
            String incomeBalance = "₹ " + summary.get("incBalance");
            todayExpenseAmt.setText(todayExpense);
            incomeBalanceAmt.setText(incomeBalance);
        });


        dashboardViewModel.getRecentExpenses().observe(getViewLifecycleOwner(), expenses -> {
            if (expenses != null && expenses.getCount() > 0) {
                yesRecentExpense.setVisibility(View.VISIBLE);
                noRecentExpense.setVisibility(View.GONE);
                expenseAdapter.updateData(expenses);
            }else{
                yesRecentExpense.setVisibility(View.GONE);
                noRecentExpense.setVisibility(View.VISIBLE);

            }
        });

        recentExpList.setLayoutManager(new LinearLayoutManager(getContext()));
        expenseAdapter = new RecentExpenseAdapter(getContext(), null);
        recentExpList.setAdapter(expenseAdapter);

        dashboardViewModel.loadDashboardData();


        return view;
    }
}
