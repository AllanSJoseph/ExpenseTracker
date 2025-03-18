package com.example.expensetracker.ui.add;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.R;
import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.ui.formatting.DateFormatter;


public class AddExpenseFragment extends Fragment{

    private EditText expDate;
    private EditText expAmt;
    private Spinner expCat;
    private EditText expNote;

    private DatabaseHelper dbHelper;
    private DateFormatter dateFormatter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
        dateFormatter = new DateFormatter();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        expDate = view.findViewById(R.id.add_date);
        expAmt = view.findViewById(R.id.amt_emt);
        expCat = view.findViewById(R.id.exp_cat_spinner);
        expNote = view.findViewById(R.id.note);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUserid = sharedPref.getString("userid","");

        // Populate Spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.expense_categories, android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expCat.setAdapter(adapter);

        expDate.setOnClickListener(v -> showDatePickerDialog());

        view.findViewById(R.id.add_exp_btn).setOnClickListener(v -> {
            String date = expDate.getText().toString();
            String amt = expAmt.getText().toString();
            String cat = expCat.getSelectedItem().toString();
            String note = expNote.getText().toString();

            if(date.isEmpty() || amt.isEmpty() || cat.isEmpty() || note.isEmpty()){
                Toast.makeText(requireParentFragment().requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                if(dbHelper.addExpense(amt,date,cat,note,currentUserid)){
                    Toast.makeText(requireParentFragment().requireContext(), "Expense Added Successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                }else{
                    Toast.makeText(requireParentFragment().requireContext(), "Failed to add expense", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }

    private void clearFields(){
        expDate.setText("");
        expAmt.setText("");
        expCat.setSelection(0);
        expNote.setText("");
    }



    private void showDatePickerDialog(){
        int year = dateFormatter.getCurrentYear();
        int month = dateFormatter.getCurrentMonth();
        int day = dateFormatter.getCurrentDay();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
            expDate.setText(dateFormatter.formatDate(selectedDay,selectedMonth,selectedYear));
        }, year, month, day);

        datePickerDialog.show();
    }
}
