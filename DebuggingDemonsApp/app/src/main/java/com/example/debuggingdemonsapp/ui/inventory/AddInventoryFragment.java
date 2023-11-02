package com.example.debuggingdemonsapp.ui.inventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Item;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddInventoryFragment extends DialogFragment {

    private EditText editTextDateOfPurchase;
    private EditText editTextDescription;
    private EditText editTextMake;
    private EditText editTextModel;
    private EditText editTextSerialNumber;
    private EditText editTextEstimatedValue;
    private EditText editTextComment;

    private OnFragmentInteractionListener listener;

    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_item, null);

        editTextDateOfPurchase = view.findViewById(R.id.editTextDateOfPurchase);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextMake = view.findViewById(R.id.editTextMake);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextSerialNumber = view.findViewById(R.id.editTextSerialNumber);
        editTextEstimatedValue = view.findViewById(R.id.editTextEstimatedValue);
        editTextComment = view.findViewById(R.id.editTextComment);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String dateOfPurchase = editTextDateOfPurchase.getText().toString();
                    String description = editTextDescription.getText().toString();
                    String make = editTextMake.getText().toString();
                    String model = editTextModel.getText().toString();
                    String serialNumber = editTextSerialNumber.getText().toString();
                    String estimatedValue = editTextEstimatedValue.getText().toString();
                    String comment = editTextComment.getText().toString();

                    Item newItem = new Item(description, dateOfPurchase, make, model, serialNumber, estimatedValue, comment);
                    itemsRef.add(newItem);

                    if (getTargetFragment() instanceof OnFragmentInteractionListener) {
                        listener = (OnFragmentInteractionListener) getTargetFragment();
                        listener.onOKPressed(newItem);
                    }
                }).create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
    }

    public interface OnFragmentInteractionListener {
        void onOKPressed(Item item);
    }
}
