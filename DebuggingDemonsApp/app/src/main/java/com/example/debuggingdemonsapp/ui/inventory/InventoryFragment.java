package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;

import java.util.Arrays;
import java.util.List;

public class InventoryFragment extends Fragment {

    private FragmentInventoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InventoryViewModel inventoryViewModel =
                new ViewModelProvider(this).get(InventoryViewModel.class);

        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = binding.listView; // Assuming 'listView' is the ID of the ListView in your XML

        // Sample list of items
        List<String> mockItems = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");

        // Directly set the mock items to the ListView using the CustomAdapter
        CustomAdapter adapter = new CustomAdapter(getContext(), mockItems);
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
