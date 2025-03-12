package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ExpenseManager.db";

    private static final int DATABASE_VERSION = 4;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL("CREATE TABLE users (uid INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, phoneno TEXT, password TEXT)");
        db.execSQL("CREATE TABLE expenses (eid INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE NOT NULL, date TEXT, category TEXT, note TEXT, uid INTEGER, FOREIGN KEY(uid) REFERENCES users(uid) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE income (iid INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, source TEXT, type TEXT, uid INTEGER, FOREIGN KEY(uid) REFERENCES users(uid) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS expenses");
        db.execSQL("DROP TABLE IF EXISTS income");
    }

    public boolean newUser(String username, String email, String phoneno, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("phoneno", phoneno);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public Cursor getUserDetails(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String squery = "SELECT * FROM users WHERE email = '" + email + "'";
        return db.rawQuery(squery,null);
    }

    public HashMap<String,String> validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT uid,username FROM users WHERE email = ? AND password = ?",
                new String[]{email, password}
        );

        if (cursor.moveToFirst()) {
            String uid = cursor.getString(0);
            String uname = cursor.getString(1);
            cursor.close();
            HashMap<String, String> user = new HashMap<>();
            user.put("uid", uid);
            user.put("uname", uname);
            return user;
        }else{
            return null;
        }

    }

    //Expenses

    public boolean addExpense(String amt, String date, String cat, String note, String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amt);
        values.put("date",date);
        values.put("category", cat);
        values.put("note", note);
        values.put("uid", uid);
        long result = db.insert("expenses", null, values);
        return result != -1;
    }

    public Cursor viewExpenses(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        String squery = "SELECT * FROM expenses WHERE uid = '" + uid + "'";
        return db.rawQuery(squery,null);
    }





    //Income

    public boolean addIncome(String amt, String source, String type, String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amt);
        values.put("source", source);
        values.put("type", type);
        values.put("uid", uid);
        long result = db.insert("income", null, values);
        return result != -1;
    }

    public Cursor viewIncomes(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        String squery = "SELECT * FROM income WHERE uid = '" + uid + "'";
        return db.rawQuery(squery,null);
    }
}
