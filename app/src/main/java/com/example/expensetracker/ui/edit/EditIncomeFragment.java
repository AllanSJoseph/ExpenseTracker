package com.example.expensetracker.ui.edit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.EditActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.ui.formatting.DateFormatter;

public class EditIncomeFragment extends Fragment {
    private EditText editIncDate;
    private EditText editIncAmt;
    private EditText editIncSource;
    private Spinner editIncType;

    private String[] types;

    private Button editIncBtn, cancelBtn;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_edit_income, container, false);

        editIncDate = view.findViewById(R.id.edit_inc_date);
        editIncAmt = view.findViewById(R.id.edit_inc_amt);
        editIncSource = view.findViewById(R.id.edit_inc_source);
        editIncType = view.findViewById(R.id.edit_inc_typ_spinner);

        editIncBtn = view.findViewById(R.id.add_inc_btn);
        cancelBtn = view.findViewById(R.id.cancel_inc_btn);
        types = getResources().getStringArray(R.array.income_types);
        String id = getArguments().getString(EditActivity.EXTRA_ID);

        // Populate Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.income_types, android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editIncType.setAdapter(adapter);

        editIncDate.setOnClickListener(v -> showDatePickerDialog());
        populateForm();

        editIncBtn.setOnClickListener(v -> {

            String date = editIncDate.getText().toString();
            String amt = editIncAmt.getText().toString();
            String source = editIncSource.getText().toString();
            String type = editIncType.getSelectedItem().toString();

            if(date.isEmpty() || amt.isEmpty() || source.isEmpty() || type.isEmpty()){
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                if(dbHelper.editIncome(id,amt,date,source,type)){
                    Toast.makeText(requireContext(), "Income Edited Successfully", Toast.LENGTH_SHORT).show();
                    Bundle result = new Bundle();
                    result.putString("editedIncomeId", id); // Or any other relevant data

                    // Set the result with a request key
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("editedIncomeId", id);
                    requireActivity().setResult(Activity.RESULT_OK, resultIntent);
                    requireActivity().finish();
                }else {
                    Toast.makeText(requireContext(), "Failed to edit income", Toast.LENGTH_SHORT).show();
                }
            }

        });

        cancelBtn.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        });


        return view;
    }

    private void populateForm(){
        assert getArguments() != null;
        if (!getArguments().isEmpty()){
            editIncAmt.setText(getArguments().getString(EditActivity.EXTRA_AMT));
            editIncSource.setText(getArguments().getString(EditActivity.EXTRA_NOTE_OR_SOURCE));
            editIncDate.setText(getArguments().getString(EditActivity.EXTRA_DATE));
            String selectedCat = getArguments().getString(EditActivity.EXTRA_TYP_OR_CAT);
            for (int i = 0; i < types.length; i++){
                if (types[i].equals(selectedCat)){
                    editIncType.setSelection(i);
                    break;
                }
            }
        }
    }

    private void showDatePickerDialog() {
            int year = dateFormatter.getCurrentYear();
            int month = dateFormatter.getCurrentMonth();
            int day = dateFormatter.getCurrentDay();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(), (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                editIncDate.setText(dateFormatter.formatDate(selectedDay, selectedMonth, selectedYear));
            }, year, month, day);

            datePickerDialog.show();
    }

}
