package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.model.Tag;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment implements EquipTagsFragment.OnFragmentInteractionListener {

    private FragmentInventoryBinding binding;
    private InventoryViewModel inventoryViewModel;
    private ItemAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ItemAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        inventoryViewModel.getItems().observe(getViewLifecycleOwner(), newItems -> {
            adapter.setItems(newItems);
            adapter.notifyDataSetChanged();
        });

        binding.tagButton.setOnClickListener(v -> openEquipTagsDialog());

        binding.deleteButton.setOnClickListener(v -> deleteSelectedItems());

        binding.addButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_inventoryFragment_to_addInventoryFragment);
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

    private void deleteSelectedItems() {
        List<Item> selectedItems = adapter.getSelectedItems();
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
        }
    }
}
