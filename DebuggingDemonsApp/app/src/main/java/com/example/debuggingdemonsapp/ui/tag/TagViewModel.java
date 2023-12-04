package com.example.debuggingdemonsapp.ui.tag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.debuggingdemonsapp.model.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * This is a class that retrieves and updates Tag data from a FirebaseFirestore database
 */
public class TagViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Tag>> tags;
    private FirebaseFirestore db;
    private CollectionReference tagsRef;

    /**
     * This creates a new TagViewModel, connects to database, and fetches Tags from database
     */
    public TagViewModel(String current_user) {
        db = FirebaseFirestore.getInstance();
        if (current_user == null){
           tagsRef = db.collection("tags");
        }else{
            tagsRef = db.collection("users"+"/"+current_user + "/" + "tags");
        }

        tags = new MutableLiveData<>(new ArrayList<>());
        fetchTags();
    }

    /**
     * This returns the List of Tags
     * @return
     *     Returns Tags (LiveData)
     */
    public LiveData<ArrayList<Tag>> getTags() {
        return tags;
    }

    /**
     * This sets the List of Tags to newTags
     * @param newTags
     *     List of Tags to overwrite current Tags
     */
    public void setTags(ArrayList<Tag> newTags) {
        tags.postValue(newTags);
    }

    /**
     * This adds a given Tag to Tags, only if a Tag with same name isn't already present
     * @param tag
     *     Tag to be added
     * @return
     *     Returns true if Tag is added, false otherwise
     */
    public boolean addTag(Tag tag) {
        ArrayList<Tag> currentTags = new ArrayList<>(tags.getValue());

        // return false if tag has duplicate name
        for (Tag aTag : currentTags) {

            if (aTag.getName().equals(tag.getName())) {
                return false;
            }
        }

        currentTags.add(tag);
        tags.setValue(currentTags);
        tagsRef.add(tag);
        return true;
    }

    /**
     * This retrieves List of Tags from Firebase Firestore database
     */
    public void fetchTags() {
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