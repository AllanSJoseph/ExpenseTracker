package com.example.expensetracker.ui.tabadapts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expensetracker.ui.add.AddExpenseFragment;
import com.example.expensetracker.ui.add.AddIncomeFragment;

public class TabAdapter_Add extends FragmentStateAdapter {

    public TabAdapter_Add(@NonNull Fragment fragment){
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AddExpenseFragment();
            case 1:
                return new AddIncomeFragment();
            default:
                return new AddExpenseFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }













}
