package com.example.debuggingdemonsapp.ui.tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends ArrayAdapter<Tag> {
    private final Context context;
    private List<Tag> tags;

    public TagAdapter(Context context, List<Tag> tags) {
        super(context, 0, tags);
        this.tags = tags;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_tag, parent, false);
        }

        Tag tag = tags.get(position);
        TextView tagName = view.findViewById(R.id.tag_name);

        tagName.setText(tag.getName());
        return view;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
