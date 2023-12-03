package com.example.debuggingdemonsapp;


import android.graphics.Bitmap;
import com.example.debuggingdemonsapp.model.Photograph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class PhotographUnitTest {
    @Mock
    Bitmap mockBitmap;
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

        assert photo.getUri() == mockURI;

    }

    @Test
    public void testBarcodeValues(){
        // Method to test if setting/getting of the barcode values works correctly
        Photograph photo = mockPhoto();

        List<String> mockBarcodes = new ArrayList<>();
        mockBarcodes.add("fakeBarcode1");
        mockBarcodes.add("fakeBarcode2");
        photo.setBarcodeValues(mockBarcodes);

        assert photo.getBarcodeValues() == mockBarcodes;
    }

    @Test
    public void testSerialNumbers(){
        // Method to test if setting/getting of the serial number values works correctly
        Photograph photo = mockPhoto();

        String mockSerial = "fakeSerial";

        photo.setSerialNumber(mockSerial);

        assert photo.getSerialNumber() == mockSerial;
    }

}
