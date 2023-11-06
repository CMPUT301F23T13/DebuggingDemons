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

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    private FragmentInventoryBinding binding;
    private InventoryViewModel inventoryViewModel;
    private ItemAdapter adapter; // 为RecyclerView定义一个适配器变量

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

        inventoryViewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        });

        binding.addButton.setOnClickListener(v -> openAddItemDialog());

        return root;
    }

    private void openAddItemDialog() {
        AddInventoryFragment newFragment = new AddInventoryFragment();
        newFragment.show(getParentFragmentManager(), "add_item");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
