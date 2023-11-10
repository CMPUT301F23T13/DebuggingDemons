package com.example.debuggingdemonsapp.ui.inventory;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
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

        // uncheck on item deletion
        holder.itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing to do
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.itemCheckbox.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing to do
            }
        });

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
                            bundle.putString("image1", item.getImage1());
                            bundle.putString("image2", item.getImage2());
                            bundle.putString("image3", item.getImage3());
                            Navigation.findNavController(v).navigate(R.id.navigation_editItem, bundle);
                        }
                    })
                    .create()
                    .show();
        });
    }

    private String createItemDetailMessage(Item item) {
        StringBuilder tagList = new StringBuilder();
        for (String tagName : item.getTagNames()) {
            tagList.append("- ").append(tagName).append("\n");
        }

        return "Description: " + item.getDescription() + "\n" +
                "Date of Purchase: " + item.getDateOfPurchase() + "\n" +
                "Make: " + item.getMake() + "\n" +
                "Model: " + item.getModel() + "\n" +
                "Serial Number: " + item.getSerialNumber() + "\n" +
                "Estimated Value: " + item.getEstimatedValue() + "\n" +
                "Comment: " + item.getComment() +  "\n" +
                "Tags:" + "\n" + tagList +
                "Images" + " ...";
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

    public double getTotalEstimatedValue() {
        double totalValue = 0;
        for (Item item : items) {
            try {
                double value = Double.parseDouble(item.getEstimatedValue());
                totalValue += value;
            } catch (NumberFormatException e) {
                Log.e("ItemAdapter", "not able to estimate value: " + item.getEstimatedValue());
            }
        }
        return totalValue;
    }
}
