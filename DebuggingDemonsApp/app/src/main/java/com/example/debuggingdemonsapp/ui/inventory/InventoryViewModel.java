package com.example.debuggingdemonsapp.ui.inventory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class InventoryViewModel extends ViewModel {

    // LiveData for the list of items (assuming it's a list of Strings for simplicity)
    private final MutableLiveData<List<String>> items = new MutableLiveData<>();

    // Constructor (optional, but you can initialize data here if needed)
    public InventoryViewModel() {
        // For example: setItems(Arrays.asList("Item 1", "Item 2", "Item 3"));
    }

    // Getters for the LiveData
    public LiveData<List<String>> getItems() {
        return items;
    }

    // Methods to update the data
    public void setItems(List<String> newItems) {
        items.setValue(newItems);
    }

    // If needed, you can add other methods to manage other pieces of data or business logic
}
