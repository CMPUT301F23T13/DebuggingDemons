package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;

public class InventoryFragment extends Fragment {

    private FragmentInventoryBinding binding;
    private InventoryViewModel inventoryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel =
                new ViewModelProvider(this).get(InventoryViewModel.class);

        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        inventoryViewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            ItemAdapter adapter = new ItemAdapter(getContext(), items);
            recyclerView.setAdapter(adapter);
        });

        binding.addButton.setOnClickListener(v -> openAddItemDialog());

        binding.tagButton.setOnClickListener(v -> openEquipTagDialog());

        return root;
    }

    private void openAddItemDialog() {
        // Create and show the dialog.
        AddInventoryFragment newFragment = new AddInventoryFragment();
        newFragment.show(getParentFragmentManager(), "add_item");
    }

    private void openEquipTagDialog() {
        // Create and show the dialog.
        EquipTagsFragment newFragment = new EquipTagsFragment();
        newFragment.show(getParentFragmentManager(), "equip_tags");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
