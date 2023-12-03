package com.example.debuggingdemonsapp;

import android.graphics.Bitmap;
import com.example.debuggingdemonsapp.model.Photograph;
import com.example.debuggingdemonsapp.model.SavedPhotoList;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class PhotoListUnitTest {
    @Mock
    Bitmap mockBitmap;
    private SavedPhotoList mockPhotoList(){
        SavedPhotoList mockList = new SavedPhotoList();
        return mockList;
    }

    @Test
    public void getPhotosTest(){
        SavedPhotoList photoList = mockPhotoList();
        assertTrue(photoList.getPhotos().isEmpty());

        Photograph photograph = new Photograph(mockBitmap);

        photoList.addPhoto(photograph);

        assertTrue(photoList.getPhotos().contains(photograph));
    }
    @Test
    public void addPhotoTest(){
        // Method used to test adding items to the photo list
        SavedPhotoList photoList = mockPhotoList();

        Photograph photograph = new Photograph(mockBitmap);

        assert photoList.isEmpty();

        photoList.addPhoto(photograph);

        assert photoList.getPhotos().size() == 1;


        Photograph photograph2 = new Photograph(mockBitmap);

        photoList.addPhoto(photograph2);
        assert photoList.getPhotos().size() == 2;
        assert photoList.getPhotos().indexOf(photograph2) == 0;
    }

    @Test
    public void appendPhotoTest(){
        SavedPhotoList photoList = mockPhotoList();

        Photograph photograph = new Photograph(mockBitmap);

        assert photoList.isEmpty();

        photoList.appendPhoto(photograph);

        assert photoList.getPhotos().size() == 1;

        Photograph photograph2 = new Photograph(mockBitmap);

        photoList.appendPhoto(photograph2);
        assert photoList.getPhotos().size() == 2;
        assert photoList.getPhotos().indexOf(photograph2) == 1;

    }

    @Test
    public void addInPositionTest(){
        SavedPhotoList photoList = mockPhotoList();

        Photograph photograph = new Photograph(mockBitmap);

        assert photoList.isEmpty();

        photoList.appendPhoto(photograph);

        assert photoList.getPhotos().size() == 1;

        Photograph photograph2 = new Photograph(mockBitmap);

        photoList.appendPhoto(photograph2);
        assert photoList.getPhotos().size() == 2;

        Photograph photograph3 = new Photograph(mockBitmap);
        photoList.addInPosition(1, photograph3);
        assert photoList.getPhotos().size() == 3;
        assert photoList.getPhotos().indexOf(photograph3) == 1;

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

    @Test
    public void itemIndexWithBitmapTest(){
        SavedPhotoList photoList = mockPhotoList();

        Photograph photograph = new Photograph(mockBitmap);
        assert photoList.itemIndexWithBitmap(mockBitmap) == -1;

        photoList.addPhoto(photograph);

        assert photoList.itemIndexWithBitmap(mockBitmap) == photoList.getPhotos().indexOf(photograph);
    }
}

