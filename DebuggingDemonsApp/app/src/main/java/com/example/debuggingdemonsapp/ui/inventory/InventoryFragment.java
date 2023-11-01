package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class InventoryFragment extends Fragment implements AddInventoryFragment.OnFragmentInteractionListener {

    private ArrayList<Item> itemList;
    private ItemAdapter itemAdapter;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(getContext(), itemList);

        RecyclerView itemsRecyclerView = view.findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsRecyclerView.setAdapter(itemAdapter);

        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            AddInventoryFragment addInventoryFragment = new AddInventoryFragment();
            addInventoryFragment.show(getChildFragmentManager(), "ADD_ITEM");
        });

        itemsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore", error.toString());
                    return;
                }
                if (querySnapshots != null) {
                    itemList.clear();
                    for (QueryDocumentSnapshot doc : querySnapshots) {
                        Item item = doc.toObject(Item.class);
                        itemList.add(item);
                    }
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    @Override
    public void onOKPressed(Item item) {
        itemList.add(item);
        itemAdapter.notifyDataSetChanged();
    }
}
