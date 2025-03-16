package com.example.expensetracker.ui.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.R;
import com.example.expensetracker.DatabaseHelper;

public class ViewIncomeFragment extends Fragment {

    private LinearLayout noIncome, yesIncome;
    private TableLayout tablelayout;

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

        noIncome = view.findViewById(R.id.income_no_table);
        yesIncome = view.findViewById(R.id.income_yes_table);

        tablelayout = view.findViewById(R.id.incomes_table);

        Cursor cur = dbHelper.viewIncomes(currentUserid);

        if (cur.getCount() == 0) {
            noIncome.setVisibility(View.VISIBLE);
            yesIncome.setVisibility(View.GONE);
        } else {
            loadTableData(cur);
            noIncome.setVisibility(View.GONE);
            yesIncome.setVisibility(View.VISIBLE);
        }


        return view;
    }

    private void loadTableData(Cursor cur){

        if (cur != null && cur.moveToFirst()){
            do{
                String id = cur.getString(0);
                String amount = cur.getString(1);
                String date = cur.getString(2);
                String source = cur.getString(3);
                String type = cur.getString(4);

                TableRow row = new TableRow(requireContext());

                TextView idInc = new TextView(requireContext());
                idInc.setText(id);
                idInc.setPadding(8,8,8,8);

                TextView amountInc = new TextView(requireContext());
                amountInc.setText(amount);
                amountInc.setPadding(8,8,8,8);

                TextView dateInc = new TextView(requireContext());
                dateInc.setText(date);
                dateInc.setPadding(8,8,8,8);

                TextView sourceInc = new TextView(requireContext());
                sourceInc.setText(source);
                sourceInc.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                sourceInc.setPadding(8,8,8,8);
                sourceInc.setSingleLine(false);
                sourceInc.setMaxLines(4);
                //sourceInc.setBreakStrategy(Layout.BREAK_STRATEGY_BALANCED);

                TextView typeInc = new TextView(requireContext());
                typeInc.setText(type);
                typeInc.setPadding(8,8,8,8);


                row.addView(idInc);
                row.addView(amountInc);
                row.addView(dateInc);
                row.addView(sourceInc);
                row.addView(typeInc);

                tablelayout.addView(row);


            }while (cur.moveToNext());
        }

        if (cur != null){
            cur.close();
        }

    }

}
