package com.example.debuggingdemonsapp.ui.inventory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.debuggingdemonsapp.model.Item;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class InventoryViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference itemsRef = db.collection("items");

    public InventoryViewModel() {
        items.setValue(new ArrayList<>());
        fetchItems();
    }

    public LiveData<ArrayList<Item>> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> newItems) {
        items.setValue(newItems);
    }

    public void addItem(Item item) {
        ArrayList<Item> currentItems = new ArrayList<>(items.getValue());
        currentItems.add(item);
        items.setValue(currentItems);
    }

    public void fetchItems() {
        itemsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Item> newItems = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    newItems.add(document.toObject(Item.class));
                }
                items.setValue(newItems);
            }
        });
    }
}
