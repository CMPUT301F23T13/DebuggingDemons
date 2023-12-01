package com.example.debuggingdemonsapp.ui.inventory;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.debuggingdemonsapp.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * A DialogFragment used to display sorting options for items.
 * Users can choose to sort by different criteria such as date, description, make, or value,
 * and can select either ascending or descending order.
 */
public class SortByItemFragment extends DialogFragment {

    private SortByItemListener listener;
    private String sortBy;

    /**
     * Interface for callback to be invoked when a sort option is selected.
     */
    public interface SortByItemListener {
        void onSortSelected(String sortBy, boolean isAscending);
    }

    /**
     * Set the listener that will receive the sort option selections.
     *
     * @param listener the SortByItemListener to notify
     */
    public void setSortByItemListener(SortByItemListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // No layout inflation is needed here since it's done in onCreateDialog
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_sort_by_item, null);
        builder.setView(view);

        // Initialize view components
        RadioGroup sortOrderGroup = view.findViewById(R.id.sort_order_group);
        RadioButton sortAscending = view.findViewById(R.id.sort_ascending);

        // Set listeners for sorting criteria buttons
        view.findViewById(R.id.sort_by_date).setOnClickListener(v -> sortBy = "date");
        view.findViewById(R.id.sort_by_description).setOnClickListener(v -> sortBy = "description");
        view.findViewById(R.id.sort_by_make).setOnClickListener(v -> sortBy = "make");
        view.findViewById(R.id.sort_by_value).setOnClickListener(v -> sortBy = "value");
        view.findViewById(R.id.sort_by_tag).setOnClickListener(v -> sortBy = "tag");

        // Setup dialog buttons
        builder.setPositiveButton("Confirm", null); // Initially null for custom behavior
        builder.setNegativeButton("Cancel", (dialog, id) -> dismiss());

        AlertDialog dialog = builder.create();

        // Custom behavior for the Confirm button
        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view1 -> {
                // Check if a sorting criterion has been selected
                if (sortBy == null || sortBy.isEmpty()) {
                    Snackbar.make(view, "Please select a sorting criterion", Snackbar.LENGTH_SHORT).show();
                    return; // Exit, do not proceed further
                }
                // Check if a sorting order (ascending/descending) has been selected
                if (sortOrderGroup.getCheckedRadioButtonId() == -1) {
                    Snackbar.make(view, "Please select a sorting order", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                boolean isAscendingOrder = sortOrderGroup.getCheckedRadioButtonId() == sortAscending.getId();
                if (listener != null) {
                    listener.onSortSelected(sortBy, isAscendingOrder);
                }
                dialog.dismiss();
            });
        });

        return dialog;
    }

}
