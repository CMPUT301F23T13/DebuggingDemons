package com.example.debuggingdemonsapp.ui.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.inventory.EquipTagsAdapter;

import org.checkerframework.common.returnsreceiver.qual.This;

import java.util.ArrayList;

/**
 * This is a class that stores and displays Tags in a RecyclerView
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private ArrayList<Tag> tags;

    /**
     * This creates a new TagAdapter with given Tags
     * @param tags
     *     List of Tags
     */
    public TagAdapter(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    /**
     * This returns a new TagViewHolder that will later display a specific Tag in Tags
     * @param parent
     *     The ViewGroup into which the new View will be added after it is bound to
     *     an adapter position
     * @param viewType
     *     The view type of the new View
     *
     * @return
     *     Return new TagViewHolder for a RecyclerView
     */
    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_content, parent, false);
        return new TagViewHolder(tagView);
    }

    /**
     * This binds given TagViewHolder to a Tag at given position in Tags
     * @param holder
     *     The TagViewHolder which should be updated to represent the contents of the
     *     Tag at the given position in Tags
     * @param position
     *     The position of a Tag within Tags
     */
    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.tagName.setText(tag.getName());
    }

    /**
     * This returns the size of Tags
     * @return
     *     Returns size of Tags
     */
    @Override
    public int getItemCount() {
        return tags != null ? tags.size() : 0;
    }

    /**
     * This sets the Tags to given Tags
     * @param newTags
     *     List of Tags to overwrite current Tags
     */
    public void setTags(ArrayList<Tag> newTags) {
        this.tags = newTags;
        notifyDataSetChanged();
    }

    /**
     * This is a class that stores the layout for a Tag to be displayed with in a RecyclerView
     */
    public static class TagViewHolder extends RecyclerView.ViewHolder {
        public TextView tagName;

        /**
         * This creates a new TagViewHolder using a given layout
         * @param tagView
         *     Layout for data to be displayed in
         */
        public TagViewHolder(View tagView) {
            super(tagView);
            tagName = tagView.findViewById(R.id.tag_name);
        }
    }
}