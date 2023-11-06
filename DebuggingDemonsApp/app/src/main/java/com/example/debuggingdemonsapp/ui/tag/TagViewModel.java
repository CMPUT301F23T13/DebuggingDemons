package com.example.debuggingdemonsapp.ui.tag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.debuggingdemonsapp.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagViewModel extends ViewModel {
    private final MutableLiveData<List<Tag>> tags;
    private TagDB tagDB;

    public TagViewModel() {
        tagDB = new TagDB();
        tags = new MutableLiveData<>(tagDB.getTags());
    }

    public LiveData<List<Tag>> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        tagDB.addTag(tag);
    }
}