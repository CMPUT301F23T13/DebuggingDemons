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

/**
 * This is a class that stores and displays Tags along with checkboxes in a RecyclerView
 */
public class EquipTagsAdapter extends RecyclerView.Adapter<EquipTagsAdapter.EquipTagsViewHolder> {

    private ArrayList<Tag> tags;
    private ArrayList<Tag> selectedTags;

    /**
     * This creates a new EquipTagsAdapter with given Tags
     * @param tags
     *    List of Tags
     */
    public EquipTagsAdapter(ArrayList<Tag> tags) {
        this.tags = tags;
        selectedTags = new ArrayList<>();
    }

    /**
     * This returns a new EquipTagsViewHolder that will later display a specific Tag in Tags
     * @param parent
     *     The ViewGroup into which the new View will be added after it is bound to
     *     an adapter position
     * @param viewType
     *     The view type of the new View
     *
     * @return
     *     Return new EquipTagsViewHolder for a RecyclerView
     */
    @NonNull
    @Override
    public EquipTagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_equip_content, parent, false);
        return new EquipTagsViewHolder(tagView);
    }

    /**
     * This binds given EquipTagsViewHolder to a Tag at given position in Tags
     * @param holder
     *     The EquipTagsViewHolder which should be updated to represent the contents of the
     *     Tag at the given position in Tags
     * @param position
     *     The position of a Tag within Tags
     */
    @Override
    public void onBindViewHolder(@NonNull EquipTagsViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.tagName.setText(tag.getName());

        // keep track of currently selected Tags
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
    }

    /**
     * This returns the Tags that are currently selected
     * @return
     *    Returns currently selected Tags
     */
    public ArrayList<Tag> getSelectedTags() {
        return selectedTags;
    }

    /**
     * This is a class that stores the layout for a Tag to be displayed with in a RecyclerView
     */
    public static class EquipTagsViewHolder extends RecyclerView.ViewHolder {
        public TextView tagName;
        public CheckBox tagCheckBox;

        /**
         * This creates a new EquipTagsViewHolder using a given layout
         * @param tagView
         *     Layout for data to be displayed in
         */
        public EquipTagsViewHolder(View tagView) {
            super(tagView);
            tagName = tagView.findViewById(R.id.tag_name);
            tagCheckBox = tagView.findViewById(R.id.tag_checkbox);
        }
    }
}

