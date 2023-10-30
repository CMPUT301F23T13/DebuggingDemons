package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;

public class InventoryFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    private FragmentInventoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button addButton = binding.buttonAddItem;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(InventoryFragment.this)
                        .navigate(R.id.action_inventoryFragment_to_addItemFragment);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
