package com.example.debuggingdemonsapp.ui.inventory;


import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

/**
 * A dialog fragment for filtering inventory items by description and date range.
 */
public class FilterFragment extends DialogFragment {

    /**
     * Interface definition for filtering items based on a description keyword.
     */
    public interface OnFilterByDescriptionListener {
        /**
         * Callback method invoked when filtering items by a description keyword.
         *
         * @param keyword The keyword used for filtering items by description.
         */
        void filterByDescription(String keyword);
    }

    /**
     * Interface definition for filtering items based on a date range.
     */
    public interface OnFilterByDateListener {
        /**
         * Callback method invoked when filtering items by a date range.
         *
         * @param startDate The start date for the filter range.
         * @param endDate   The end date for the filter range.
         */
        void filterByDate(Date startDate, Date endDate);
    }

    /**
     * Interface definition for filtering items based on a date range.
     */
    public interface OnFilterByTagListener {

        /**
         * Callback method invoked when filtering items by a description keyword.
         *
         * @param tag The keyword used for filtering items by description.
         */
        void filterByTag(String tag);
    }

    public interface OnFilterByMakeListener {
        /**
         * Callback method invoked when filtering items by their make.
         *
         * @param make The make used for filtering items.
         */
        void filterByMake(String make);
    }


    // UI elements and variables for date selection
    private EditText keywordEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private EditText tagFilterEditText;
    private EditText makeFilterEditText;

    private OnFilterByDescriptionListener filterByDescriptionListener;
    private OnFilterByDateListener filterByDateListener;
    private OnFilterByTagListener filterByTagListener;
    private OnFilterByMakeListener filterByMakeListener;



    /**
     * Create the dialog for filtering inventory items.
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Created dialog instance.
     */
    @SuppressLint("CutPasteId")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.item_filter, null);
        keywordEditText = dialogView.findViewById(R.id.description_filter);
        startDateEditText = dialogView.findViewById(R.id.start_date_filter);
        endDateEditText = dialogView.findViewById(R.id.end_date_filter);
        tagFilterEditText = dialogView.findViewById(R.id.tag_filter);
        makeFilterEditText = dialogView.findViewById(R.id.make_filter);

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        // Initialize the make filter EditText and handle its filter action
        Button makeFilterButton = dialogView.findViewById(R.id.filter_by_make_button);
        makeFilterButton.setOnClickListener(v -> {
            String make = makeFilterEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(make)) {
                filterByMakeListener.filterByMake(make);
                dismiss();
            } else {
                Toast.makeText(getContext(), "Please enter a make", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize the tag filter EditText and handle its filter action
        Button tagFilterButton = dialogView.findViewById(R.id.filter_by_tag_button);
        tagFilterButton.setOnClickListener(v -> {
            String tag = tagFilterEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(tag)) {
                filterByTagListener.filterByTag(tag);
                dismiss();
            } else {
                Toast.makeText(getContext(), "Please enter a make", Toast.LENGTH_SHORT).show();
            }
        });


        // Set click listeners for date pickers and buttons
        startDateEditText.setOnClickListener(v -> showDatePickerDialog(true));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(false));

        Button descriptionFilterButton = dialogView.findViewById(R.id.description_filter_button);
        Button dateFilterButton = dialogView.findViewById(R.id.filter_by_date_button);


        // Handle description filter button click
        descriptionFilterButton.setOnClickListener(v -> {
            String keyword = keywordEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(keyword)) {
                filterByDescriptionListener.filterByDescription(keyword);
                dismiss(); // Close the dialog after filtering by description
            } else {
                Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
            }
        });


        // Handle date filter button click
        dateFilterButton.setOnClickListener(v -> {
            Date startDate = startCalendar.getTime();
            Date endDate = endCalendar.getTime();
            // Check if end date is not older than start date
            if (endDate.before(startDate)) {
                Toast.makeText(getContext(), "End date cannot be older than start date", Toast.LENGTH_SHORT).show();
                return; // Return without filtering if the date range is invalid
            }
            filterByDateListener.filterByDate(startDate, endDate);
            dismiss(); // Close the dialog after filtering by date range
        });

        // Set up dialog view and buttons
        builder.setView(dialogView)
                .setTitle("Filter")
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .setNeutralButton("Show all", (dialog, which) -> {
                    filterByDescriptionListener.filterByDescription("");
                });
        return builder.create();
    }

    /**
     * Sets the listener for filtering items by description.
     *
     * @param listener The listener implementing the {@link OnFilterByDescriptionListener} interface.
     *                 This listener is used for filtering items based on a description keyword.
     */
    public void setFilterByDescriptionListener(OnFilterByDescriptionListener listener) {
        this.filterByDescriptionListener = listener;
    }

    /**
     * Sets the listener for filtering items by date range.
     *
     * @param listener The listener implementing the {@link OnFilterByDateListener} interface.
     *                 This listener is used for filtering items based on a date range.
     */
    public void setFilterByDateListener(OnFilterByDateListener listener) {
        this.filterByDateListener = listener;
    }

    /**
     * Sets the listener for filtering items by tag.
     *
     * @param listener The listener implementing the {@link OnFilterByTagListener} interface.
     *                 This listener is used for filtering items based on a tag string.
     */
    public void setFilterByTagListener(OnFilterByTagListener listener) {
        this.filterByTagListener = listener;
    }

    /**
     * Sets the listener for filtering items by make.
     *
     * @param listener The listener implementing the {@link OnFilterByMakeListener} interface.
     *                 This listener is used for filtering items based on a make keyword.
     */
    public void setFilterByMakeListener(OnFilterByMakeListener listener) {
        this.filterByMakeListener = listener;
    }

    // Method to show date picker dialog based on start or end date selection
    private void showDatePickerDialog(final boolean isStartDate) {
        final Calendar calendar;
        if (isStartDate) {
            calendar = startCalendar;
        } else {
            calendar = endCalendar;
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, month1, dayOfMonth1) -> {
                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, month1);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth1);
                    updateDateEditText(isStartDate);
                }, year, month, dayOfMonth);
        // Restrict date range to past dates
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    // Update the date EditText fields with selected date
    private void updateDateEditText(boolean isStartDate) {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());

        if (isStartDate) {
            startDateEditText.setText(simpleDateFormat.format(startCalendar.getTime()));
            Log.d("DatePicker", "Start date updated: " + startDateEditText.getText().toString());
        } else {
            endDateEditText.setText(simpleDateFormat.format(endCalendar.getTime()));
            Log.d("DatePicker", "End date updated: " + endDateEditText.getText().toString());
        }
    }
}

