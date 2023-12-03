package com.example.debuggingdemonsapp.ui.inventory;

import android.text.TextUtils;

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
    private CollectionReference itemsRef;

    public InventoryViewModel(String current_user) {
        itemsRef = db.collection("users"+"/"+current_user +"/"+ "items");
        items.setValue(new ArrayList<>());
        fetchItems();
    }

    /**
     * Interface for handling the result of an item deletion operation.
     */
    public interface DeletionListener {
        /**
         * Called when an item is successfully deleted.
         */
        void onDeletionSuccessful();
        /**
         * Called when an item deletion operation fails.
         */
        void onDeletionFailed();
    }

    /**
     * Deletes a specified item and notifies the result through a listener.
     *
     * This method attempts to delete an item from a Firebase database. Upon completion
     * of the deletion operation, it either removes the item from the current LiveData
     * if successful, or notifies of a failure through the provided listener.
     *
     * @param item The item to be deleted.
     * @param listener The listener that will be notified of the deletion result.
     */
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

    /**
     * This sets InventoryViewModel's items to newItems
     * @param newItems
     *     List of items to update InventoryViewModel
     */
    public void setItems(ArrayList<Item> newItems) {
        items.setValue(newItems);
    }

    public void addItem(Item item) {
        ArrayList<Item> currentItems = new ArrayList<>(items.getValue());
        currentItems.add(item);
        items.setValue(currentItems);
    }

    /**
     * This fetches Items from database and updates it into InventoryViewModel's items
     */
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

    /**
     * This gets the collection reference to the items in the database
     * @return
     *     Reference to item collection in database
     */
    public CollectionReference getItemsRef() {
        return itemsRef;
    }

    public void filterItemByKeyword(String keyword) {
        if(TextUtils.isEmpty(keyword)) {
            fetchItems();
        } else {
            itemsRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ArrayList<Item> filteredItems = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Item item = document.toObject(Item.class);
                        if (item.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                            filteredItems.add(item);
                        }
                    }
                    items.postValue(filteredItems);
                }
            });
        }
    }
}
