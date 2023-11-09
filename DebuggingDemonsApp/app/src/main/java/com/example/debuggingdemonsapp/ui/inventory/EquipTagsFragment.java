package com.example.debuggingdemonsapp.ui.inventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.tag.TagViewModel;

import java.util.ArrayList;

/**
 * This is a class that allows the user to select Tags from a TagViewModel
 */
public class EquipTagsFragment extends DialogFragment {
    private RecyclerView tagList;
    private EquipTagsAdapter equipTagsAdapter;
    private TagViewModel tagViewModel;
    private EquipTagsFragment.OnFragmentInteractionListener listener;

    /**
     * This is an interface that ensures a listener that implements this interface
     * will execute onEquipTags() when necessary
     */
    public interface OnFragmentInteractionListener {
        void onEquipTags(ArrayList<Tag> tags);
    }

    /**
     * This attaches the EquipTagsFragment to a given Context
     * @param context
     *     Context to which the EquipTagsFragment should be attached
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener) getParentFragment();
        assert listener != null;
    }

    /**
     * This creates a Dialog that displays list of Tags from TagViewModel
     * @param savedInstanceState
     *     The last saved instance state of the Fragment, or null if
     *     this is a freshly created Fragment
     *
     * @return
     *     Returns Dialog with list of Tags
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_tag_equip, null);

        tagList = view.findViewById(R.id.tag_list);
        tagList.setLayoutManager(new LinearLayoutManager(getContext()));

        equipTagsAdapter = new EquipTagsAdapter(new ArrayList<>());
        tagList.setAdapter(equipTagsAdapter);

        // update data in equipTagsAdapter once data is retrieved from TagViewModel
        tagViewModel.getTags().observe(this, newTags -> {
            assert newTags != null;
            equipTagsAdapter.setTags(newTags);
            equipTagsAdapter.notifyDataSetChanged();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // return a Dialog
        return builder.setView(view)
                .setTitle("Equip Tags")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    // pass selected Tags to InventoryFragment when "OK" is clicked
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onEquipTags(equipTagsAdapter.getSelectedTags());
                    }
                }).create();
    }
}