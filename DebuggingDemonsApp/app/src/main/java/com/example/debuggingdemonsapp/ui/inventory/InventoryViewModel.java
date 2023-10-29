package com.example.debuggingdemonsapp.ui.inventory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.debuggingdemonsapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class InventoryViewModel extends ViewModel {

    private final MutableLiveData<List<Item>> items;

    public InventoryViewModel() {
        items = new MutableLiveData<>(new ArrayList<Item>());
    }

    public LiveData<List<Item>> getItems() {
        return items;
    }

    public void addItem(Item item) {
        List<Item> currentItems = items.getValue();
        currentItems.add(item);
        items.setValue(currentItems);
    }
}
