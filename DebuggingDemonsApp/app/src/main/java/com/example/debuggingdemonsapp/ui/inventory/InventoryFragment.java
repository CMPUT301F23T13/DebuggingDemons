package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.model.Tag;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class InventoryFragment extends Fragment implements EquipTagsFragment.OnFragmentInteractionListener, SortByItemFragment.SortByItemListener {

    private FragmentInventoryBinding binding;
    private InventoryViewModel inventoryViewModel;
    private ItemAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel = new InventoryViewModel(((MainActivity)getActivity()).current_user);
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        inventoryViewModel.getItems().observe(getViewLifecycleOwner(), newItems -> {
            adapter.setItems(newItems);
            adapter.notifyDataSetChanged();
            updateTotalEstimatedValue();
        });

        binding.tagButton.setOnClickListener(v -> openEquipTagsDialog());

        binding.deleteButton.setOnClickListener(v -> deleteSelectedItems());

        binding.sortbyButton.setOnClickListener(v -> showSortByDialog());

        binding.addButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_inventoryFragment_to_addInventoryFragment);
        });

        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterFragment();
            }
        });

        return root;
    }

    /**
     * This creates a new EquipTagsFragment and displays it
     */
    private void openEquipTagsDialog() {
        EquipTagsFragment newFragment = new EquipTagsFragment();
        newFragment.show(getChildFragmentManager(), "equip_tags");
    }


    /**
     * Deletes the selected items from the inventory.
     *
     * This method first retrieves a list of selected items from the adapter. If no items are selected,
     * it displays a Snackbar message indicating that no items are selected for deletion. If there are
     * selected items, it iterates through each item and attempts to delete them using the
     * {@link InventoryViewModel#deleteItem} method. Upon deletion, it either shows a confirmation
     * message for successful deletion or an error message with a retry option in case of failure.
     */
    private void deleteSelectedItems() {
        ArrayList<Item> selectedItems = adapter.getSelectedItems();

        if (selectedItems.isEmpty()) {
            // Display the message when user didn't select any items and click the delete button
            Snackbar.make(getView(), "No items selected to be deleted.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Deleting the item from the database and the RecycleView
        for (Item item : selectedItems) {
            inventoryViewModel.deleteItem(item, new InventoryViewModel.DeletionListener() {
                @Override
                public void onDeletionSuccessful() {
                    // Snackbar to confirm deletion
                    Snackbar.make(getView(), "Item deleted successfully", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onDeletionFailed() {
                    // Snackbar to inform about the failure and retry
                    Snackbar.make(getView(), "Failed to delete item", Snackbar.LENGTH_LONG)
                            .setAction("Retry", v -> deleteSelectedItems()) // Provide a retry button
                            .show();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * This equips given Tags (that were selected by user) to currently selected Items in adapter
     * @param selectedTags
     *     List of selected Tags from a EquipTagsFragment
     */
    @Override
    public void onEquipTags(ArrayList<Tag> selectedTags) {
        ArrayList<Item> selectedItems = adapter.getSelectedItems();
        for (Item item : selectedItems) {
            for (Tag tag : selectedTags) {
                item.addTag(tag);
            }
            Query query = inventoryViewModel.getItemsRef().whereEqualTo("description", item.getDescription());
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        document.getReference().update("tagNames", item.getTagNames());
                    }
                }
            });
        }
    }

    /**
     * Updates the display of the total estimated value of all items in the inventory.
     * This method retrieves the total estimated value from the adapter and sets this value
     * in the appropriate TextView in the UI.
     */
    private void updateTotalEstimatedValue() {
        double totalValue = adapter.getTotalEstimatedValue(); // Retrieve the total estimated value from the adapter
        binding.totalEstimatedValue.setText("Total Estimated Value: " + totalValue); // Update the TextView with the new value
    }

    /**
     * Opens the FilterFragment for filtering items by description and date range.
     * This method initializes the FilterFragment, sets listeners for filtering by description and date range
     * and displays the FilterFragment dialog.
     */
    private void openFilterFragment() {
        // Initialize FilterFragment
        FilterFragment filterFragment = new FilterFragment();

        // Set listener for filtering by description
        filterFragment.setFilterByDescriptionListener(new FilterFragment.OnFilterByDescriptionListener() {
            @Override
            public void filterByDescription(String keyword) {
                // Invoke ViewModel method to filter items by keyword
                inventoryViewModel.filterItemByKeyword(keyword);
            }
        });

        // Set listener for filtering by date range
        filterFragment.setFilterByDateListener(new FilterFragment.OnFilterByDateListener() {
            @Override
            public void filterByDate(Date startDate, Date endDate) {
                // Invoke ViewModel method to filter items by date range
                inventoryViewModel.filterItemByDate(startDate, endDate);
            }
        });
        // Show the FilterFragment dialog
        filterFragment.show(getChildFragmentManager(), "FilterFragment");
    }




    /**
     * Displays the dialog to select sorting criteria and order for inventory items.
     * This method creates an instance of SortByItemFragment, sets the current class as the listener,
     * and displays the fragment.
     */
    private void showSortByDialog() {
        SortByItemFragment sortByFragment = new SortByItemFragment();
        sortByFragment.setSortByItemListener(this);
        sortByFragment.show(getChildFragmentManager(), "sort_by_dialog");
    }

    /**
     * Callback method invoked when a sorting option is selected in the SortByItemFragment.
     * This method is responsible for calling the method to sort inventory items based on the selected criteria.
     *
     * @param sortBy The criterion by which to sort (e.g., date, description, make, value).
     * @param isAscending The order of sorting: true for ascending, false for descending.
     */
    @Override
    public void onSortSelected(String sortBy, boolean isAscending) {
        // Assuming you have a method to sort inventory items
        sortInventoryItems(sortBy, isAscending);
    }

    /**
     * Sorts the inventory items based on the specified criterion and order.
     * This method retrieves the current list of items from the adapter, sorts them based on the
     * selected criteria and order, and then updates the adapter with the sorted list.
     *
     * @param sortBy The criterion by which to sort (e.g., date, description, make, value).
     * @param isAscending The order of sorting: true for ascending, false for descending.
     */
    private void sortInventoryItems(String sortBy, boolean isAscending) {
        List<Item> itemList = adapter.getItems(); // Assuming your adapter has a method to get the current list of items

        Comparator<Item> comparator = null;
        switch (sortBy) {
            case "date":
                comparator = Comparator.comparing(Item::getDateOfPurchase); // Replace getDateOfPurchase with the appropriate getter method
                break;
            case "description":
                comparator = Comparator.comparing(Item::getDescription);
                break;
            case "make":
                comparator = Comparator.comparing(Item::getMake);
                break;
            case "value":
                comparator = Comparator.comparing(Item::getEstimatedValue); // Ensure this method returns a Comparable type
                break;
            case "tag":
                comparator = Comparator.comparing(Item::compareTagNames);
                break;
        }

        if (comparator != null) {
            if (!isAscending) {
                comparator = comparator.reversed();
            }
            Collections.sort(itemList, comparator);
            adapter.setItems(itemList); // Update the adapter's data
            adapter.notifyDataSetChanged(); // Notify that data has changed
        }
    }


}
