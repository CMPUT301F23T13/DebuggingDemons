package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.example.debuggingdemonsapp.model.Item;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    private InventoryViewModel inventoryViewModel;
    private FragmentInventoryBinding binding;
    private final ArrayList<Item> itemsList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Setup RecyclerView
        RecyclerView recyclerView = binding.itemsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
        itemAdapter = new ItemAdapter(getContext(), itemsList);
        recyclerView.setAdapter(itemAdapter);

        itemsRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore", error.toString());
                return;
            }

            if (value != null) {
                itemsList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Item item = doc.toObject(Item.class);
                    itemsList.add(item);
                }
                itemAdapter.notifyDataSetChanged();
            }
        });

        binding.addButton.setOnClickListener(v ->
                NavHostFragment.findNavController(InventoryFragment.this)
                        .navigate(R.id.action_inventoryFragment_to_addItemFragment)
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
