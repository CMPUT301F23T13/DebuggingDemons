package com.example.debuggingdemonsapp.ui.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Tag;

import java.util.ArrayList;

public class EquipTagsAdapter extends RecyclerView.Adapter<EquipTagsAdapter.EquipTagsViewHolder> {

    private ArrayList<Tag> tags;
    private ArrayList<Tag> selectedTags;

    public EquipTagsAdapter(ArrayList<Tag> tags) {
        this.tags = tags;
        selectedTags = new ArrayList<>();
    }

    @NonNull
    @Override
    public EquipTagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_equip_content, parent, false);
        return new EquipTagsViewHolder(tagView);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipTagsViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.tagName.setText(tag.getName());
        holder.tagCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !selectedTags.contains(tag)) {
                    selectedTags.add(tag);
                }
                if (!isChecked && selectedTags.contains(tag)) {
                    selectedTags.remove(tag);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags != null ? tags.size() : 0;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Tag> getSelectedTags() {
        return selectedTags;
    }

    public static class EquipTagsViewHolder extends RecyclerView.ViewHolder {
        public TextView tagName;
        public CheckBox tagCheckBox;

        public EquipTagsViewHolder(View tagView) {
            super(tagView);
            tagName = tagView.findViewById(R.id.tag_name);
            tagCheckBox = tagView.findViewById(R.id.tag_checkbox);
        }
    }
}

