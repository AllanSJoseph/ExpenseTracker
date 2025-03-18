package com.example.expensetracker.ui.add;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.R;
import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.ui.formatting.DateFormatter;


import java.util.Objects;

public class AddIncomeFragment extends Fragment {

    private EditText incDate;
    private EditText incAmount;
    private EditText incSource;
    private Spinner incType;

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

        View view = inflater.inflate(R.layout.fragment_add_income, container, false);

        incDate = view.findViewById(R.id.add_inc_date);
        incAmount = view.findViewById(R.id.income_amt_entry);
        incSource = view.findViewById(R.id.inc_source_entry);
        incType = view.findViewById(R.id.inc_type_spinner);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUserid = sharedPref.getString("userid","");

        // Populate spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.income_types, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incType.setAdapter(adapter);

        incDate.setOnClickListener(v -> showDatePickerDialog());

        view.findViewById(R.id.add_inc_btn).setOnClickListener(v -> {
            String Date = incDate.getText().toString();
            String Amount = incAmount.getText().toString();
            String Source = incSource.getText().toString();
            String Type = incType.getSelectedItem().toString();

            if(Amount.isEmpty() || Source.isEmpty() || Type.isEmpty() || Date.isEmpty()){
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                if(dbHelper.addIncome(Amount,Date,Source,Type,currentUserid)) {
                    Toast.makeText(requireContext(), "Income Added Successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(requireContext(), "Failed to add income", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void clearFields(){
        incAmount.setText("");
        incSource.setText("");
        incType.setSelection(0);
    }

    private void showDatePickerDialog(){
        int year = dateFormatter.getCurrentYear();
        int month = dateFormatter.getCurrentMonth();
        int day = dateFormatter.getCurrentDay();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
//                    String digitalMonth = selectedMonth + 1 > 9 ? "/" : "/0";
//                    String selectedDate = selectedDay + digitalMonth + (selectedMonth + 1) + "/" + selectedYear;
                    incDate.setText(dateFormatter.formatDate(selectedDay,selectedMonth,selectedYear));
        }, year, month, day);

        datePickerDialog.show();
    }

}
