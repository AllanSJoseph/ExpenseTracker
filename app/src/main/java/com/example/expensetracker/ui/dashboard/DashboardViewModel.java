package com.example.expensetracker.ui.dashboard;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.ui.formatting.DateFormatter;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DashboardViewModel extends AndroidViewModel {
    //To get the current user
    private MutableLiveData<String> uid = new MutableLiveData<>();
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USERID = "userid";

    //Stores and updates the data to load in the dashboard

    private MutableLiveData<String> date = new MutableLiveData<>();

    private MutableLiveData<HashMap<String,String>> dashFirstSummary = new MutableLiveData<>();
    private MutableLiveData<Cursor> expCatShareToday = new MutableLiveData<>();
    private MutableLiveData<Cursor> recentExpenses = new MutableLiveData<>();


    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private DatabaseHelper dbHelper;


    public DashboardViewModel(Application application){
        super(application);
        SharedPreferences sharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        uid.setValue(sharedPreferences.getString(KEY_USERID,""));
        Log.d("DashboardViewModel", "User ID: " + uid.getValue());
        dbHelper = new DatabaseHelper(application.getApplicationContext());

        DateFormatter dateFormatter = new DateFormatter();
        date.setValue(dateFormatter.getCurrentDate());
    }

    public MutableLiveData<String> getUid(){
        return uid;
    }

    public MutableLiveData<HashMap<String,String>> getDashFirstSummary(){
        return dashFirstSummary;
    }


    public MutableLiveData<Cursor> getExpCatShareToday(){
        return expCatShareToday;
    }

    public MutableLiveData<Cursor> getRecentExpenses(){
        return recentExpenses;
    }


    public void loadDashboardData(){
        executorService.execute(() ->{
            //Cursor data = dbHelper.getRecentExpenses(uid.toString());
            //Cursor expCatShareTodayy = dbHelper.getCategoryShareToday(uid.toString(),date.toString());
            //HashMap<String,String> summary = dbHelper.getDashFirstSummary(uid.toString(),date.toString());
            dashFirstSummary.postValue(dbHelper.getDashFirstSummary(uid.getValue(),date.getValue()));
            recentExpenses.postValue(dbHelper.getRecentExpenses(uid.getValue()));
            expCatShareToday.postValue(dbHelper.getCategoryShareToday(uid.toString(),date.toString()));
        });
    }


}
