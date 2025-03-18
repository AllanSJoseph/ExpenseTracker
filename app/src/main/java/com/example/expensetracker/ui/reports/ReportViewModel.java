package com.example.expensetracker.ui.reports;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.ui.formatting.DateFormatter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Calendar;

public class ReportViewModel extends AndroidViewModel{
    private MutableLiveData<Cursor> categoryShare = new MutableLiveData<>();
    private MutableLiveData<Cursor> dExpTrend = new MutableLiveData<>();
    private MutableLiveData<String> uid = new MutableLiveData<>();

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USERID = "userid";


    private MutableLiveData<String> date = new MutableLiveData<>();



    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DatabaseHelper dbHelper;

    public ReportViewModel(Application application){
        super(application);
        SharedPreferences sharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        uid.setValue(sharedPreferences.getString(KEY_USERID,""));
        dbHelper = new DatabaseHelper(application.getApplicationContext());

        DateFormatter dateFormatter = new DateFormatter();
        date.setValue(dateFormatter.getCurrentDate());
    }

    public MutableLiveData<String> getUid(){
        return uid;
    }

    public MutableLiveData<Cursor> getCategoryShare(){
        return categoryShare;
    }

    public MutableLiveData<Cursor> getDailyExpenseTrend(){
        return dExpTrend;
    }


    public void loadReportData(){
        executorService.execute(() ->{
            Cursor data = dbHelper.getCategoryShare(uid.getValue());
            Cursor data2 = dbHelper.getDailyExpenseTrend(uid.getValue());
            categoryShare.postValue(data);
            dExpTrend.postValue(data2);
        });
    }
}

