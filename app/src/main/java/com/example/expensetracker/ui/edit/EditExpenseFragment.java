package com.example.expensetracker.ui.edit;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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

public class EditExpenseFragment extends Fragment{

    private EditText editExpDate;
    private EditText editExpAmt;
    private Spinner editExpCat;
    private EditText editExpNote;

    private String[] categories;

    private Button editExpBtn, cancelBtn;

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
        View view = inflater.inflate(R.layout.fragment_edit_expense, container, false);

        editExpDate = view.findViewById(R.id.edit_date);
        editExpAmt = view.findViewById(R.id.edit_amt);
        editExpCat = view.findViewById(R.id.edit_exp_cat_spinner);
        editExpNote = view.findViewById(R.id.edit_note);

        editExpBtn = view.findViewById(R.id.edit_exp_btn);
        cancelBtn = view.findViewById(R.id.cancel_exp_btn);
        categories = getResources().getStringArray(R.array.expense_categories);
        String id = getArguments().getString(EditActivity.EXTRA_ID);


        // Populate Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.expense_categories, android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editExpCat.setAdapter(adapter);

        editExpDate.setOnClickListener(v -> showDatePickerDialog());

        populateForm();

        editExpBtn.setOnClickListener(v -> {
            String date = editExpDate.getText().toString();
            String amt = editExpAmt.getText().toString();
            String cat = editExpCat.getSelectedItem().toString();
            String note = editExpNote.getText().toString();


            if(date.isEmpty() || amt.isEmpty() || cat.isEmpty() || note.isEmpty()){
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                if(dbHelper.editExpense(id,amt,date,cat,note)){
                    Toast.makeText(requireContext(), "Expense Edited Successfully", Toast.LENGTH_SHORT).show();
                    Bundle result = new Bundle();
                    result.putString("editedExpenseId", id); // Or any other relevant data

                    // Set the result with a request key
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("editedExpenseId", id);
                    requireActivity().setResult(Activity.RESULT_OK, resultIntent);
                    requireActivity().finish();
                }else{
                    Toast.makeText(requireContext(), "Failed to edit expense", Toast.LENGTH_SHORT).show();
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
            editExpAmt.setText(getArguments().getString(EditActivity.EXTRA_AMT));
            editExpNote.setText(getArguments().getString(EditActivity.EXTRA_NOTE_OR_SOURCE));
            editExpDate.setText(getArguments().getString(EditActivity.EXTRA_DATE));
            String selectedCat = getArguments().getString(EditActivity.EXTRA_TYP_OR_CAT);
            for (int i = 0; i < categories.length; i++){
                if (categories[i].equals(selectedCat)){
                    editExpCat.setSelection(i);
                    break;
                }
            }
        }
    }


    private void showDatePickerDialog(){
        int year = dateFormatter.getCurrentYear();
        int month = dateFormatter.getCurrentMonth();
        int day = dateFormatter.getCurrentDay();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
            editExpDate.setText(dateFormatter.formatDate(selectedDay,selectedMonth,selectedYear));
        }, year, month, day);

        datePickerDialog.show();
    }
}
