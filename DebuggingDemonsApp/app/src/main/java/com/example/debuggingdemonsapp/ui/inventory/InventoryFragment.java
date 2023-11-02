package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InventoryFragment extends Fragment {

    private FragmentInventoryBinding binding;
    private DatabaseReference databaseReference;
    private InventoryViewModel inventoryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("items");

        ListView listView = binding.listView;

        // Observe changes in the item list from ViewModel
        inventoryViewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            CustomAdapter adapter = new CustomAdapter(getContext(), items);
            listView.setAdapter(adapter);

            // Update Firebase with the new list
            updateFirebase(items);
        });

        return root;
    }

    private void updateFirebase(List<String> items) {
        // Clear existing items in Firebase
        databaseReference.setValue(null);

        // Push updated items to Firebase
        for (String item : items) {
            String id = databaseReference.push().getKey();
            databaseReference.child(id).setValue(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<String> generateRandomItems(int itemCount) {
        List<String> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < itemCount; i++) {
            int randomNumber = random.nextInt(100); // Generating a random number between 0 and 99
            items.add("Item " + randomNumber);
        }

        return items;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Generate a random list of 5 items
        List<String> randomItems = generateRandomItems(5);

        // Update the ViewModel with this new list
        inventoryViewModel.setItems(randomItems);
    }
}

