package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.debuggingdemonsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

public class EditItemFragment extends Fragment {
    private EditText DoP;
    private EditText Description;
    private EditText Make;
    private EditText Model;
    private EditText SerialNumber;
    private EditText EstimatedValue;
    private EditText Comment;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    public EditItemFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");

        View view = inflater.inflate(R.layout.item_edit, container, false);
        DoP = view.findViewById(R.id.dateOfPurchase);
        Description = view.findViewById(R.id.description);
        Make = view.findViewById(R.id.make);
        Model = view.findViewById(R.id.model);
        SerialNumber = view.findViewById(R.id.serial_number);
        EstimatedValue = view.findViewById(R.id.estimated_value);
        Comment = view.findViewById(R.id.comment);

        Button confirmButton = view.findViewById(R.id.confirm_button);
        Button cancelButton = view.findViewById(R.id.back_button);


        Bundle bundle = getArguments();
        if (bundle != null) {
            System.out.println(bundle.getString("doP"));
            String doP = bundle.getString("doP");
            String description = bundle.getString("description");
            String make = bundle.getString("make");
            String model = bundle.getString("model");
            String serialNumber = bundle.getString("serialNumber");
            String estimatedValue = bundle.getString("estimatedValue");
            String comment = bundle.getString("comment");

            // 初始化界面
            DoP.setText(doP);
            Description.setText(description);
            Make.setText(make);
            Model.setText(model);
            SerialNumber.setText(serialNumber);
            EstimatedValue.setText(estimatedValue);
            Comment.setText(comment);
        }


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                InventoryViewModel viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
                viewModel.fetchItems();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.navigation_inventory);
//                Navigation.findNavController(v).popBackStack();
            }
        });
        return view;
    }

    private void saveData(){
        String updatedDoP = DoP.getText().toString();
        String updatedDescription = Description.getText().toString();
        String updatedMake = Make.getText().toString();
        String updatedModel = Model.getText().toString();
        String updatedSerialNumber = SerialNumber.getText().toString();
        String updatedEstimatedValue = EstimatedValue.getText().toString();
        String updatedComment = Comment.getText().toString();
        Query query = itemsRef.whereEqualTo("description", updatedDescription);

        query.get(Source.SERVER).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        document.getReference().update("dateOfPurchase", updatedDoP,
                                        "make", updatedMake,
                                        "model", updatedModel,
                                        "serialNumber", updatedSerialNumber,
                                        "estimatedValue", updatedEstimatedValue,
                                        "comment", updatedComment)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Update successfully!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "Update failed, please try again later.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
}
