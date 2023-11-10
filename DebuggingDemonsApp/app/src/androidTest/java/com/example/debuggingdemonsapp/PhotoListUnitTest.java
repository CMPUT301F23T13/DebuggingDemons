package com.example.debuggingdemonsapp;

import android.graphics.Bitmap;
import com.example.debuggingdemonsapp.model.Photograph;
import com.example.debuggingdemonsapp.model.SavedPhotoList;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhotoListUnitTest {

    Bitmap mockBitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
    private SavedPhotoList mockPhotoList(){
        SavedPhotoList mockList = new SavedPhotoList();
        return mockList;
    }

    @Test
    public void addPhotoTest(){
        // Method used to test adding items to the photo list
        SavedPhotoList photoList = mockPhotoList();

        Photograph photograph = new Photograph(mockBitmap);

        assert photoList.isEmpty();

        photoList.addPhoto(photograph);

        assert photoList.getPhotos().size() == 1;
    }

    @Test
    public void isEmptyTest(){
        // Method used to test whether the isEmpty method gives the correct value
        SavedPhotoList photoList = mockPhotoList();

        assertEquals(photoList.getPhotos().isEmpty(), photoList.isEmpty());
    }

    @Test
    public void deletePhotoTest(){
        // Method used to test the photo deletion method of photolist

        SavedPhotoList photoList = mockPhotoList();

        Photograph photograph = new Photograph(mockBitmap);

        photoList.addPhoto(photograph);

        assertFalse(photoList.isEmpty());

        photoList.deletePhoto(photograph);

        assertTrue(photoList.isEmpty());

    }
}

