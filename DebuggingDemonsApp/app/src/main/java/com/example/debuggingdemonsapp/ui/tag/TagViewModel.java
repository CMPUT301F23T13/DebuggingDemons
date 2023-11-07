package com.example.debuggingdemonsapp.ui.tag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.debuggingdemonsapp.model.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TagViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Tag>> tags;
    private FirebaseFirestore db;
    private CollectionReference tagsRef;

    public TagViewModel() {
        db = FirebaseFirestore.getInstance();
        tagsRef = db.collection("tags");
        tags = new MutableLiveData<>(new ArrayList<>());
        fetchItems();
    }

    public LiveData<ArrayList<Tag>> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> newTags) {
        tags.setValue(newTags);
    }

    public void addTag(Tag tag) {
        tagsRef.add(tag);
        ArrayList<Tag> currentTags = new ArrayList<>(tags.getValue());
        currentTags.add(tag);
        tags.setValue(currentTags);
    }

    public void fetchItems() {
        tagsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Tag> newTags = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    newTags.add(document.toObject(Tag.class));
                }
                tags.postValue(newTags);
            }
        });
    }
}