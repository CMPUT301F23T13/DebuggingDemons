package com.example.debuggingdemonsapp.ui.tag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debuggingdemonsapp.databinding.FragmentTagBinding;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class TagFragment extends Fragment implements AddTagFragment.OnFragmentInteractionListener {
    private FragmentTagBinding binding;
    private TagViewModel tagViewModel;
    private TagAdapter tagAdapter;

    public static TagFragment newInstance() {
        return new TagFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);

        binding = FragmentTagBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tagAdapter = new TagAdapter(root.getContext(), tagViewModel.getTags().getValue());

        ListView tagListView = binding.tagList;
        tagListView.setAdapter(tagAdapter);

        tagViewModel.getTags().observe(getViewLifecycleOwner(), new Observer<List<Tag>>() {
            public void onChanged(List<Tag> tagList) {
                tagAdapter.setTags(tagList);
                tagAdapter.notifyDataSetChanged();
            }
        });

        Button addButton = binding.addTagButton;

        addButton.setOnClickListener(v -> {
            AddTagFragment addTagFragment = new AddTagFragment();
            addTagFragment.show(getChildFragmentManager(), "ADD_TAG");
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onOkPressed(Tag tag) {
        tagViewModel.addTag(tag);
    }
}