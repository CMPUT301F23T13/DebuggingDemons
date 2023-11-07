package com.example.debuggingdemonsapp.ui.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Tag;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private ArrayList<Tag> tags;

    public TagAdapter(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_content, parent, false);
        return new TagViewHolder(tagView);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.tagName.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return tags != null ? tags.size() : 0;
    }

    public void setTags(ArrayList<Tag> newTags) {
        this.tags = newTags;
        notifyDataSetChanged();
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tagName;

        public TagViewHolder(View tagView) {
            super(tagView);
            tagName = tagView.findViewById(R.id.tag_name);
        }
    }
}