package com.example.debuggingdemonsapp.ui.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.debuggingdemonsapp.R;

import java.util.List;

/**
 * CustomAdapter is an ArrayAdapter for displaying a list of strings, each with a checkbox.
 * It is designed to be used with a ListView or similar view that requires an adapter.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> values;

    /**
     * Constructor for the CustomAdapter.
     *
     * @param context The current context. This value is used to inflate the layout file.
     * @param values  A List of String values that will be displayed in the ListView.
     */
    public CustomAdapter(Context context, List<String> values) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = values;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the list item view.
     * @param convertView The old view to reuse, if possible. If not possible, a new view is inflated.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get a layout inflater from the current context
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the layout for this list item
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        // Find the TextView and CheckBox in the inflated layout
        TextView textView = rowView.findViewById(R.id.item_name);
        CheckBox checkBox = rowView.findViewById(R.id.item_checkbox);

        // Set the text for the TextView to be the item at the current position
        textView.setText(values.get(position));

        // Optional: Handle the checkbox state if needed
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle checkbox state changes here
            // Implement the logic to handle checkbox state changes here
            // For example, updating the state of the item in the list
        });

        return rowView;
    }
}
