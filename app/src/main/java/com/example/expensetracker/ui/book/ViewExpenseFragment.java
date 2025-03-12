package com.example.expensetracker.ui.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;

import com.example.expensetracker.R;
import com.example.expensetracker.DatabaseHelper;

public class ViewExpenseFragment extends Fragment {

    private LinearLayout noExpense, yesExpense;
    private TableLayout tablelayout;

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

        noExpense = view.findViewById(R.id.expense_no_table);
        yesExpense = view.findViewById(R.id.expense_yes_table);

        tablelayout = view.findViewById(R.id.expenses_table);

        Cursor cur = dbHelper.viewExpenses(currentUserid);

        if (cur.getCount() == 0){
            noExpense.setVisibility(View.VISIBLE);
            yesExpense.setVisibility(View.GONE);
        } else {
            loadTableData(cur);
            noExpense.setVisibility(View.GONE);
            yesExpense.setVisibility(View.VISIBLE);
        }



        return view;
    }

    private void loadTableData(Cursor cur){

        if(cur != null && cur.moveToFirst()){
            do {
                String id = cur.getString(0);
                String amount = cur.getString(1);
                String date = cur.getString(2);
                String category = cur.getString(3);
                String note = cur.getString(4);

                TableRow row = new TableRow(requireContext());

                TextView idExp = new TextView(requireContext());
                idExp.setText(id);
                idExp.setPadding(8,8,8,8);

                TextView amountExp = new TextView(requireContext());
                amountExp.setText(amount);
                amountExp.setPadding(8,8,8,8);

                TextView dateExp = new TextView(requireContext());
                dateExp.setText(date);
                dateExp.setPadding(8,8,8,8);

                TextView categoryExp = new TextView(requireContext());
                categoryExp.setText(category);
                categoryExp.setPadding(8,8,8,8);

                TextView noteExp = new TextView(requireContext());
                noteExp.setText(note);
                noteExp.setPadding(8,8,8,8);


                row.addView(idExp);
                row.addView(amountExp);
                row.addView(dateExp);
                row.addView(categoryExp);
                row.addView(noteExp);

                tablelayout.addView(row);


            }while (cur.moveToNext());
        }

        if(cur != null){
            cur.close();
        }

    }


}
