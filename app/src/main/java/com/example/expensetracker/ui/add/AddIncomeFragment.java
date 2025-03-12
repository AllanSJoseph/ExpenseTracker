package com.example.expensetracker.ui.add;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.R;
import com.example.expensetracker.DatabaseHelper;

import java.util.Objects;

public class AddIncomeFragment extends Fragment {

    private EditText expAmount;
    private EditText expSource;
    private Spinner expType;

    private DatabaseHelper dbHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_income, container, false);

        expAmount = view.findViewById(R.id.income_amt_entry);
        expSource = view.findViewById(R.id.inc_source_entry);
        expType = view.findViewById(R.id.inc_type_spinner);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String currentUserid = sharedPref.getString("userid","");

        // Populate spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.income_types, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expType.setAdapter(adapter);

        view.findViewById(R.id.add_inc_btn).setOnClickListener(v -> {
            String Amount = expAmount.getText().toString();
            String Source = expSource.getText().toString();
            String Type = expType.getSelectedItem().toString();

            if(Amount.isEmpty() || Source.isEmpty() || Type.isEmpty()){
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                if(dbHelper.addIncome(Amount,Source,Type,currentUserid)) {
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
        expAmount.setText("");
        expSource.setText("");
        expType.setSelection(0);
    }

}
