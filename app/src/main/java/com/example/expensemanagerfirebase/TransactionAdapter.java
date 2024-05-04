package com.example.expensemanagerfirebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    Context context;
    ArrayList<TransactionModel> transactionModelArrayList;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> transactionModelArrayList) {
        this.context = context;
        this.transactionModelArrayList = transactionModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TransactionModel model = transactionModelArrayList.get(holder.getAdapterPosition());
        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());
        holder.note.setText(model.getNote());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                // Use holder.getAdapterPosition() here as well
                int adapterPosition = holder.getAdapterPosition();
                intent.putExtra("id", transactionModelArrayList.get(adapterPosition).getId());
                intent.putExtra("amount", transactionModelArrayList.get(adapterPosition).getAmount());
                intent.putExtra("note", transactionModelArrayList.get(adapterPosition).getNote());
                intent.putExtra("type", transactionModelArrayList.get(adapterPosition).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView note,amount,date;
        View priority;
        public MyViewHolder(View itemView){
            super(itemView);
            note=itemView.findViewById(R.id.note_one);
            amount=itemView.findViewById(R.id.amount_note);
            date=itemView.findViewById(R.id.date_one);
            priority=itemView.findViewById(R.id.priority_one);

        }
    }
}
