package com.example.expensetracker.ui.tabadapts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expensetracker.ui.book.ViewExpenseFragment;
import com.example.expensetracker.ui.book.ViewIncomeFragment;

public class TabAdapter_Book extends FragmentStateAdapter {

    public TabAdapter_Book(@NonNull Fragment fragment){
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ViewExpenseFragment();
            case 1:
                return new ViewIncomeFragment();
            default:
                return new ViewExpenseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
