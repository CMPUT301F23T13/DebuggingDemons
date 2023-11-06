package com.example.debuggingdemonsapp.ui.inventory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.debuggingdemonsapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class InventoryViewModel extends ViewModel {

    // LiveData for the list of items
    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();

    public InventoryViewModel() {
        // 初始化 items
        items.setValue(new ArrayList<>());
    }

    // Getters for the LiveData
    public LiveData<ArrayList<Item>> getItems() {
        return items;
    }

    // Methods to update the data
    public void setItems(ArrayList<Item> newItems) {
        items.setValue(newItems);
    }

    // Add method to add a single item
    public void addItem(Item item) {
        ArrayList<Item> currentItems = items.getValue();
        if (currentItems != null) {
            currentItems.add(item);
            items.setValue(currentItems);
        }
    }

    // Remove item, update item, etc. methods can be added as needed
}
