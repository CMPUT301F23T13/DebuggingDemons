package com.example.debuggingdemonsapp.ui.inventory;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.debuggingdemonsapp.model.Item;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class InventoryViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference itemsRef = db.collection("items");

    public InventoryViewModel() {
        items.setValue(new ArrayList<>());
        fetchItems();
    }


    public interface DeletionListener {
        void onDeletionSuccessful();
        void onDeletionFailed();
    }

    public void deleteItem(Item item, DeletionListener listener) {
        // Deleting the item in Firebase
        itemsRef.document(item.getId()).delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // if successful delete the item from the LiveData
                        List<Item> currentItems = items.getValue();
                        if (currentItems != null) {
                            currentItems.remove(item);
                            items.setValue((ArrayList<Item>) currentItems);
                            listener.onDeletionSuccessful();
                        }
                    } else {
                        // Deletion failed, show error message and provide a retry option
                        // Notify listener of failure
                        listener.onDeletionFailed();
                    }
                });
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
                    Item item = document.toObject(Item.class);
                    item.setId(document.getId()); // Set the ID from the document
                    newItems.add(item);
                }
                items.postValue(newItems);
            }
        });
    }
}
