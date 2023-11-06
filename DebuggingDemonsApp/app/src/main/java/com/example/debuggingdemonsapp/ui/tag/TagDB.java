package com.example.debuggingdemonsapp.ui.tag;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.model.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TagDB {
    private FirebaseFirestore db;
    private CollectionReference tagsRef;
    private List<Tag> tags;

    public TagDB() {
        db = FirebaseFirestore.getInstance();
        tagsRef = db.collection("tags");
        tags = new ArrayList<>();

        tagsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    tags.add(document.toObject(Tag.class));
                }
            }
        });
    }

    public void addTag(Tag tag) {
        tagsRef.add(tag);
        tags.add(tag);
    }

    public List<Tag> getTags() {
        return tags;
    }
}
