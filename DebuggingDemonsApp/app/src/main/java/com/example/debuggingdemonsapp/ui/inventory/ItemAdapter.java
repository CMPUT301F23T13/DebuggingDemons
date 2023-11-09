package com.example.debuggingdemonsapp.ui.inventory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
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
        //holder.itemCheckbox.setChecked();

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setTitle("Item Details")
                    .setMessage(createItemDetailMessage(item))
                    .setPositiveButton("OK", null)
                    .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = new Bundle();
                            bundle.putString("doP",item.getDateOfPurchase());
                            bundle.putString("description", item.getDescription());
                            bundle.putString("make", item.getMake());
                            bundle.putString("model", item.getModel());
                            bundle.putString("serialNumber", item.getSerialNumber());
                            bundle.putString("estimatedValue", item.getEstimatedValue());
                            bundle.putString("comment", item.getComment());
                            Navigation.findNavController(v).navigate(R.id.navigation_editItem, bundle);
                        }
                    })
                    .create()
                    .show();
        });
        holder.itemCheckbox.setOnCheckedChangeListener(null); // Prevent callback conflicts.
        holder.itemCheckbox.setChecked(items.get(position).isSelected()); // Update the checkbox from the item data.
        holder.itemCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            items.get(position).setSelected(isChecked); // Update the item's selected status.
        });
    }

    private String createItemDetailMessage(Item item) {
        return "Description: " + item.getDescription() + "\n" +
                "Date of Purchase: " + item.getDateOfPurchase() + "\n" +
                "Make: " + item.getMake() + "\n" +
                "Model: " + item.getModel() + "\n" +
                "Serial Number: " + item.getSerialNumber() + "\n" +
                "Estimated Value: " + item.getEstimatedValue() + "\n" +
                "Comment: " + item.getComment() +  "\n" +
                "Images" + "...";
    }

    // Add a method to retrieve all selected items.
    public List<Item> getSelectedItems() {
        List<Item> selectedItems = new ArrayList<>();
        for (Item item : items) {
            if (item.isSelected()) { // Make sure the item has been selected
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    // Add a method to delete all selected items.
    public void deleteSelectedItems() {
        List<Item> selectedItems = getSelectedItems();
        items.removeAll(selectedItems);
        notifyDataSetChanged(); // Update the adapter
    }


    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(ArrayList<Item> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
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
