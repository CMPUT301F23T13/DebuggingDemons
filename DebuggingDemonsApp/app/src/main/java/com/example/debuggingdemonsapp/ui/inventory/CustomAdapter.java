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

public class CustomAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> values;

    public CustomAdapter(Context context, List<String> values) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = rowView.findViewById(R.id.textView);
        CheckBox checkBox = rowView.findViewById(R.id.checkBox);

        textView.setText(values.get(position));

        // Optional: Handle the checkbox state if needed
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle checkbox state changes here
        });

        return rowView;
    }
}
