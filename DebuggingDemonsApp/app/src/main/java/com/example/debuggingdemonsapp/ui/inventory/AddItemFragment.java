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
import com.example.debuggingdemonsapp.databinding.FragmentAddItemBinding;
import com.example.debuggingdemonsapp.model.Item;

public class AddItemFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    private FragmentAddItemBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button doneButton = binding.buttonDone;
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item newItem = new Item();

                // Get all the inputs
                newItem.setDateOfPurchase(binding.editTextDateOfPurchase.getText().toString());
                newItem.setDescription(binding.editTextDescription.getText().toString());
                // ... retrieve other fields ...

                inventoryViewModel.addItem(newItem);

                // Navigate back to the list
                NavHostFragment.findNavController(AddItemFragment.this)
                        .navigate(R.id.action_addItemFragment_to_inventoryFragment);
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
