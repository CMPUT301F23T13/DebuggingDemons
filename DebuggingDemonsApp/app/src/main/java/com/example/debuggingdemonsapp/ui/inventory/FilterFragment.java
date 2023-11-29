package com.example.debuggingdemonsapp.ui.inventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.debuggingdemonsapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FilterFragment extends DialogFragment {
    public interface OnFilterSelectedListener{
        void onFilterSelected(String keyword);
    }
    private EditText keywordEditText;
    private OnFilterSelectedListener filterSelectedListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.item_filter, null);
        keywordEditText = dialogView.findViewById(R.id.description_filter);

        builder.setView(dialogView)
                .setTitle("Filter")
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String keyword = keywordEditText.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyword) && filterSelectedListener != null) {
                        filterSelectedListener.onFilterSelected(keyword);
                    } else {
                        filterSelectedListener.onFilterSelected("");
                    }
                })
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .setNeutralButton("Show all", (dialog, which) -> {
                    filterSelectedListener.onFilterSelected("");
                });
        return builder.create();
    }
    public void setFilterSelectedListener(OnFilterSelectedListener listener) {
        this.filterSelectedListener = listener;
    }
}
