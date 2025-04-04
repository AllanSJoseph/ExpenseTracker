package com.example.expensetracker.ui.book;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.expensetracker.DatabaseHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseBookViewModel extends AndroidViewModel {
    private MutableLiveData<String> uid = new MutableLiveData<>();
    private MutableLiveData<Cursor> expenseBookData = new MutableLiveData<>();
    private MutableLiveData<Cursor> incomeBookData = new MutableLiveData<>();

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USERID = "userid";

    public ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DatabaseHelper dbHelper;

    public ExpenseBookViewModel(Application application){
        super(application);
        SharedPreferences sharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        uid.setValue(sharedPreferences.getString(KEY_USERID,""));

        Log.d("ExpenseBookViewModel", "User ID: " + uid.getValue());
        dbHelper = new DatabaseHelper(application.getApplicationContext());

    }

    public MutableLiveData<Cursor> getExpenseBookData() { return expenseBookData; }

    public MutableLiveData<Cursor> getIncomeBookData() { return incomeBookData; }

    public void loadExpenseBookData(){
        executorService.execute(() -> {
            expenseBookData.postValue(dbHelper.viewExpenses(uid.getValue()));
            incomeBookData.postValue(dbHelper.viewIncomes(uid.getValue()));
        });
    }



}

