package com.example.expensetracker.ui.book;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.DatabaseHelper;
import com.example.expensetracker.EditActivity;
import com.example.expensetracker.R;

public class ExpenseBookAdapter extends RecyclerView.Adapter<ExpenseBookAdapter.ExpenseBookViewHolder> {
    private Cursor cursor;
    private Context context;
    private DatabaseHelper dbHelper;
    private String curruser;

    public interface OnEditClickListener {
        void onEditClicked(Intent editIntent);
    }

    private OnEditClickListener editClickListener;


    public ExpenseBookAdapter(Context context, Cursor cursor, DatabaseHelper dbHelper, String curruser, OnEditClickListener listener){
        this.context = context;
        this.cursor = cursor;
        this.dbHelper = dbHelper;
        this.curruser = curruser;
        this.editClickListener = listener;
    }



    @NonNull
    @Override
    public ExpenseBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        return new ExpenseBookViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseBookViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            String eid = cursor.getString(0);
            String amount = cursor.getString(1);
            String date = cursor.getString(2);
            String category = cursor.getString(3);
            String note = cursor.getString(4);

            // Set data
            holder.expAmount.setText("₹ " + amount);
            holder.expDate.setText(date);
            holder.expCategory.setText(category);
            holder.expNote.setText(note);

            holder.editBtn.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("id", eid);
                intent.putExtra("type",0);
                intent.putExtra("date",date);
                intent.putExtra("amount",amount);
                intent.putExtra("typeOrCat",category);
                intent.putExtra("noteOrSource",note);
                editClickListener.onEditClicked(intent);
            });

            holder.deleteBtn.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this expense?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            boolean deletedRows = dbHelper.deleteExpense(eid);
                            if (deletedRows) {
                                Toast.makeText(context, "Expense Deleted Successfully", Toast.LENGTH_LONG).show();
                                Cursor newCur = dbHelper.viewExpenses(curruser);
                                swapCursor(newCur);
                            } else {
                                Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            });

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount(){
        return (cursor == null) ? 0 : cursor.getCount();
    }


    public static class ExpenseBookViewHolder extends RecyclerView.ViewHolder {

        TextView expAmount, expDate, expCategory, expNote;
        ImageButton editBtn, deleteBtn;
        public ExpenseBookViewHolder(@NonNull View itemView){
            super(itemView);

            editBtn = itemView.findViewById(R.id.action_button_edit);
            deleteBtn = itemView.findViewById(R.id.action_button_delete);
            expAmount = itemView.findViewById(R.id.exp_amount);
            expDate = itemView.findViewById(R.id.exp_date);
            expCategory = itemView.findViewById(R.id.exp_category);
            expNote = itemView.findViewById(R.id.exp_note);
        }
    }

}
