package com.example.debuggingdemonsapp.ui.tag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.databinding.FragmentTagBinding;
import com.example.debuggingdemonsapp.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is a class that displays a list of Tags from a TagViewModel
 */
public class TagFragment extends Fragment implements AddTagFragment.OnFragmentInteractionListener {
    private FragmentTagBinding binding;
    private TagViewModel tagViewModel;
    private TagAdapter tagAdapter;

    /**
     * This creates a new Fragment to display the list of Tags
     * @param inflater
     *     The LayoutInflater object that can be used to inflate
     *     any views in the fragment
     * @param container
     *     If non-null, this is the parent view that the fragment's
     *     UI should be attached to.  The fragment should not add the view itself,
     *     but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState
     *     If non-null, this fragment is being re-constructed
     *     from a previous saved state as given here.
     *
     * @return
     *     View that created this instance of TagFragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tagViewModel = new TagViewModel(((MainActivity) requireActivity()).current_user);

        binding = FragmentTagBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView tagList = binding.tagList;
        tagList.setLayoutManager(new LinearLayoutManager(getContext()));

        tagAdapter = new TagAdapter(new ArrayList<>());
        tagList.setAdapter(tagAdapter);

        tagViewModel.getTags().observe(getViewLifecycleOwner(), newTags -> {
                tagAdapter.setTags(newTags);
                tagAdapter.notifyDataSetChanged();
        });

        Button addButton = binding.addTagButton;
        addButton.setOnClickListener(v -> openAddTagDialog());

        return root;
    }

    /**
     * This destroys the Fragment
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * This creates a new AddTagFragment and displays it
     */
    public void openAddTagDialog() {
        AddTagFragment addTagFragment = new AddTagFragment();
        addTagFragment.show(getChildFragmentManager(), "add_tag");
    }

    /**
     * This attempts to add given Tag to TagViewModel
     * @param tag
     *     Tag created in a AddTagFragment
     */
    @Override
    public void onOkPressed(Tag tag) {
        if (!tagViewModel.addTag(tag)) {
            Toast.makeText(getActivity(), "tag already defined", Toast.LENGTH_LONG).show();
        };
    }
}