package com.example.expensetracker.ui.book;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class IncomeBookAdapter extends RecyclerView.Adapter<IncomeBookAdapter.IncomeViewHolder> {
        private Cursor cursor;
        private Context context;
        private DatabaseHelper dbHelper;
        private String curruser;

        public interface OnEditClickListener {
            void onEditClicked(Intent editIntent);

        }

        private OnEditClickListener editClickListener;


        public IncomeBookAdapter(Context context, Cursor cursor, DatabaseHelper dbHelper, String curruser, OnEditClickListener listener){
            this.context = context;
            this.cursor = cursor;
            this.dbHelper = dbHelper;
            this.curruser = curruser;
            this.editClickListener = listener;
        }

        @NonNull
        @Override
        public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View view = LayoutInflater.from(context).inflate(R.layout.item_income, parent, false);
            return new IncomeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position){
                if (cursor.moveToPosition(position)) {
                    String id = cursor.getString(0);
                    String amount = cursor.getString(1);
                    String date = cursor.getString(2);
                    String type = cursor.getString(3);
                    String source = cursor.getString(4);

                    // Set data for Income
                    holder.incAmount.setText("₹ " + amount);
                    holder.incDate.setText(date);
                    holder.incType.setText(type);
                    holder.incSource.setText(source);

                    holder.editBtn.setOnClickListener(v -> {
                        Toast.makeText(context, "Edit Btn Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, EditActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("type",1);
                        intent.putExtra("date",date);
                        intent.putExtra("amount",amount);
                        intent.putExtra("typeOrCat",type);
                        intent.putExtra("noteOrSource",source);
                        editClickListener.onEditClicked(intent);
                    });

                    holder.deleteBtn.setOnClickListener(v -> {
                        new AlertDialog.Builder(context)
                                .setTitle("Confirm Delete")
                                .setMessage("Are you sure you want to delete this income?")
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    boolean deletedRows = dbHelper.deleteIncome(id);
                                    if (deletedRows) {
                                        Toast.makeText(context, "Income Deleted Successfully", Toast.LENGTH_LONG).show();
                                        Cursor newCur = dbHelper.viewIncomes(curruser);
                                        swapCursor(newCur);
                                    } else {
                                        Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", ((dialog, which) -> dialog.dismiss()))
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


        static class IncomeViewHolder extends RecyclerView.ViewHolder {
            TextView incAmount, incDate, incType, incSource;
            ImageButton editBtn, deleteBtn;

            public IncomeViewHolder(@NonNull View itemView){
                super(itemView);

                incAmount = itemView.findViewById(R.id.inc_amount);
                incDate = itemView.findViewById(R.id.inc_date);
                incType = itemView.findViewById(R.id.inc_type);
                incSource = itemView.findViewById(R.id.inc_source);
                editBtn = itemView.findViewById(R.id.action_button_edit);
                deleteBtn = itemView.findViewById(R.id.action_button_delete);
            }
        }
}
