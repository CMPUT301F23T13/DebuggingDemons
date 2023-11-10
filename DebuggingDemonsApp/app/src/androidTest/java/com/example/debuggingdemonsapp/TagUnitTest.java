package com.example.debuggingdemonsapp;

import com.example.debuggingdemonsapp.model.Tag;

import org.junit.Test;

public class TagUnitTest {
    private Tag mockTag() {
        return new Tag("test");
    }

    @Test
    public void testGetName() {
        Tag tag = mockTag();
        assert tag.getName().equals("test");
    }

    @Test
    public void testSetName() {
        Tag tag = mockTag();
        tag.setName("new name");
        assert tag.getName().equals("new name");
    }
}
