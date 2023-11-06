package com.example.debuggingdemonsapp.ui.inventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.tag.TagAdapter;
import com.example.debuggingdemonsapp.ui.tag.TagDB;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EquipTagsFragment extends DialogFragment {
    private TagDB tagDB;
    private List<Tag> tags;
    private ListView tagList;
    private EquipTagsAdapter equipTagsAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        tagDB = new TagDB();
        tags = tagDB.getTags();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_equip_tags, null);

        tagList = view.findViewById(R.id.tag_list);
        equipTagsAdapter = new EquipTagsAdapter(view.getContext(), tags);

        builder.setView(view)
                .setTitle("Equip Tags")
                .setNegativeButton("Cancel", (dialog, id) -> {
                    NavHostFragment.findNavController(EquipTagsFragment.this)
                            .popBackStack();
                })
                .setPositiveButton("OK", (dialog, id) -> {
                    // TODO: equip selected tags here
                });

        return builder.create();
    }
}
