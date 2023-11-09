package com.example.debuggingdemonsapp;

import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.tag.TagViewModel;

import org.junit.Test;

import java.util.ArrayList;

public class TagViewModelUnitTest {

    private ArrayList<Tag> mockTagList() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("1"));
        tags.add(new Tag("2"));
        tags.add(new Tag("3"));
        return tags;
    }

    @Test
    public void testGetTags() {
        // TODO
    }

    @Test
    public void testSetTags() {
        // TODO
    }

    @Test
    public void testAddTag() {
        // TODO
    }

    @Test
    public void testFetchItems() {
        // TODO
    }
}
