package com.example.expensetracker.ui.dashboard;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;

public class RecentExpenseAdapter extends RecyclerView.Adapter<RecentExpenseAdapter.ExpenseViewHolder> {
    private Context context;
    private Cursor cursor;

    public RecentExpenseAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    public void updateData(Cursor newCursor){
        if (cursor != null){
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense_recent, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position){
        if (cursor.moveToPosition(position)){
            String amount = cursor.getString(1);
            String category = cursor.getString(3);
            String note = cursor.getString(4);

            holder.expAmount.setText("₹ " + amount);
            holder.expCategory.setText(category);
            holder.expNote.setText(note);
        }
    }

    @Override
    public int getItemCount(){
        return (cursor == null) ? 0 : cursor.getCount();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder{
        TextView expAmount, expCategory, expNote;


        public ExpenseViewHolder(@NonNull View itemView){
            super(itemView);
            expAmount = itemView.findViewById(R.id.exp_amount);
            expCategory = itemView.findViewById(R.id.exp_category);
            expNote = itemView.findViewById(R.id.exp_note);
        }
    }

}
