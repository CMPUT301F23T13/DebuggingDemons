package com.example.debuggingdemonsapp;

import androidx.lifecycle.LifecycleOwner;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.tag.TagViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.A;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class TagViewModelUnitTest {


    @Test
    public void testGetTags() {
        TagViewModel tagViewModel = new TagViewModel(null);

        // get tag names from db
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference tagsRef = db.collection("tags");
        ArrayList<String> tagNames1 = new ArrayList<>();
        tagsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    tagNames1.add(document.toObject(Tag.class).getName());
                }

            }
        });

        // get tag names from view model
        ArrayList<String> tagNames2 = new ArrayList<>();
        for (Tag tag : tagViewModel.getTags().getValue()) {
            tagNames2.add(tag.getName());
        }

        // ensure consistent order
        Collections.sort(tagNames1);
        Collections.sort(tagNames2);

        // ensure lists are equivalent
        assert tagNames1.size() == tagNames2.size();
        for (int i = 0; i < tagNames1.size(); i++) {
            assert tagNames1.get(i).equals(tagNames2.get(i));
        }
    }
}
