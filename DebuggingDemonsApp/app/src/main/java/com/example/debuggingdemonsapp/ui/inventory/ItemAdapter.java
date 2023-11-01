package com.example.debuggingdemonsapp.ui.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Item;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private ArrayList<Item> items;

    public ItemAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.inventory_content, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.description.setText(item.getDescription());
        holder.dateOfPurchase.setText(item.getDateOfPurchase());
        holder.make.setText(item.getMake());
        holder.model.setText(item.getModel());
        holder.serialNumber.setText(item.getSerialNumber());
        holder.estimatedValue.setText(item.getEstimatedValue());
        holder.comment.setText(item.getComment());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public TextView dateOfPurchase;
        public TextView make;
        public TextView model;
        public TextView serialNumber;
        public TextView estimatedValue;
        public TextView comment;

        public ItemViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            dateOfPurchase = itemView.findViewById(R.id.dateOfPurchase);
            make = itemView.findViewById(R.id.make);
            model = itemView.findViewById(R.id.model);
            serialNumber = itemView.findViewById(R.id.serialNumber);
            estimatedValue = itemView.findViewById(R.id.estimatedValue);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
