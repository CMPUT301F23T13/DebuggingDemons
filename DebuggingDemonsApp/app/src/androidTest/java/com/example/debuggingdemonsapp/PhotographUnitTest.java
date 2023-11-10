package com.example.debuggingdemonsapp;


import android.graphics.Bitmap;
import com.example.debuggingdemonsapp.model.Photograph;
import org.junit.Test;


// As this unit test involves creating a Bitmap object it had to be placed inside the AndroidTest folder
public class PhotographUnitTest {
    Bitmap mockBitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
    private Photograph mockPhoto(){
        return new Photograph(mockBitmap);
    }

    @Test
    public void testBitmap(){
        // Method to test setting of the bitmap works correctly

        Photograph photo = mockPhoto();


        assert photo.photoBitmap() != null;
        assert photo.photoBitmap() == mockBitmap;
    }

    @Test
    public void testURI(){
        // Method to test setting of the URI works correctly

        Photograph photo = mockPhoto();

        String mockURI = "fakeURI";
        photo.setUri(mockURI);

        assert photo.storageURI() == mockURI;

    }
}
