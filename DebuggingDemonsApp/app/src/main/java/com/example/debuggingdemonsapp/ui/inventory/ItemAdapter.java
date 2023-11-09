package com.example.debuggingdemonsapp.ui.inventory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Item> items;
    private ArrayList<Item> selectedItems;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
        selectedItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getDescription());

        // keep track of currently selected Items
        holder.itemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !selectedItems.contains(item)) {
                    selectedItems.add(item);
                }
                if (!isChecked && selectedItems.contains(item)) {
                    selectedItems.remove(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(ArrayList<Item> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public ArrayList<Item> getSelectedItems() {
        return selectedItems;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public CheckBox itemCheckbox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemCheckbox = itemView.findViewById(R.id.item_checkbox);
        }
    }
}