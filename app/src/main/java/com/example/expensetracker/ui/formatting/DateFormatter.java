package com.example.expensetracker.ui.formatting;

import java.util.Calendar;

//For formatting date related stuff which have a lot of repetitive code

public class DateFormatter {
    private final String currentDate;
    private final int currentDay;
    private final int currentMonth;
    private final int currentYear;

    public DateFormatter(){
        final Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        String validDay = currentDay > 9 ? "" + currentDay : "0" + currentDay;
        String validMonth = currentMonth + 1 > 9 ? "/" + (currentMonth + 1) : "/0" + (currentMonth + 1);
        currentDate = validDay + validMonth + "/" + currentYear;
    }

    public String getCurrentDate(){
        return currentDate;
    }

    public int getCurrentDay(){
        return currentDay;
    }

    public int getCurrentMonth(){
        return currentMonth;
    }

    public int getCurrentYear(){
        return currentYear;
    }

    public String formatDate(int day, int month, int year){
        String validDay = day > 9 ? "" + day : "0" + day;
        String validMonth = month + 1 > 9 ? "/" + (month + 1) : "/0" + (month + 1);
        return validDay + validMonth + "/" + year;
    }

}
