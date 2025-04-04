package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.expensetracker.ui.edit.EditExpenseFragment;
import com.example.expensetracker.ui.edit.EditIncomeFragment;

import org.jetbrains.annotations.Nullable;

public class EditActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_TYP_OR_CAT = "typeOrCat";
    public static final String EXTRA_AMT = "amount";
    public static final String EXTRA_NOTE_OR_SOURCE = "noteOrSource";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        int type = intent.getIntExtra(EXTRA_TYPE, 0);
        String id = intent.getStringExtra(EXTRA_ID);

        Fragment fragment;
        if (type == 0){
            fragment = new EditExpenseFragment();
        } else {
            fragment = new EditIncomeFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        bundle.putString(EXTRA_ID, id);
        bundle.putString(EXTRA_AMT,intent.getStringExtra(EXTRA_AMT));
        bundle.putString(EXTRA_TYP_OR_CAT,intent.getStringExtra(EXTRA_TYP_OR_CAT));
        bundle.putString(EXTRA_DATE,intent.getStringExtra(EXTRA_DATE));
        bundle.putString(EXTRA_NOTE_OR_SOURCE,intent.getStringExtra(EXTRA_NOTE_OR_SOURCE));
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.edit_fragment_container, fragment);
        transaction.commit();
    }
}
