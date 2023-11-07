package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.model.Tag;

import java.util.ArrayList;

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

        binding.addButton.setOnClickListener(v -> openAddItemDialog());

        binding.tagButton.setOnClickListener(v -> openEquipTagsDialog());

        return root;
    }

    private void openAddItemDialog() {
        AddInventoryFragment newFragment = new AddInventoryFragment();
        newFragment.show(getParentFragmentManager(), "add_item");
    }

    private void openEquipTagsDialog() {
        EquipTagsFragment newFragment = new EquipTagsFragment();
        newFragment.show(getChildFragmentManager(), "equip_tags");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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
