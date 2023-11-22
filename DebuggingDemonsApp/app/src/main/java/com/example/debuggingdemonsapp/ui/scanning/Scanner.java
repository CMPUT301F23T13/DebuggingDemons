package com.example.debuggingdemonsapp.ui.scanning;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.camera.core.impl.ImageOutputConfig;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Class creates a Barcode Scanner object which is used to scan images for any barcodes
 */
public class Scanner {
    BarcodeScanner imageScanner;

    /**
     * Constructor method that is used to initialize the scanner
     */
    public Scanner() {
        this.imageScanner = initializeScanner();
    }

    /**
     * This method is used to initialize the scanner and setting its options
     * @return BarcodeScanner  A BarcodeScanner object
     */
    public BarcodeScanner initializeScanner(){
        // Code from https://developers.google.com/ml-kit/vision/barcode-scanning/android
        // Used to create a scanner that is able to read all barcode formats
        BarcodeScannerOptions scannerOptions = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .enableAllPotentialBarcodes().build();

        return BarcodeScanning.getClient(scannerOptions);
    }

    /**
     * This method is used to scan an image to get any and all barcodes that can be seen
     * @param image  A Bitmap object which holds the bitmap of the image that is to be scanned
     * @param rotationDegrees An integer object which holds the rotation degrees of the image
     */
    public void scanImage(Bitmap image, int rotationDegrees){
        // Code from https://developers.google.com/ml-kit/vision/barcode-scanning/android
        Task<List<Barcode>> getimageBarcodes = this.imageScanner.process(InputImage.fromBitmap(image, rotationDegrees)) // The bitmap is changed to an InputImage object which the scanner can use
                .addOnSuccessListener(barcodes -> {
                    System.out.println(barcodes);

                    if (!barcodes.isEmpty()) {
                        // Gets the info from all the barcodes that were captured in the image
                        for (Barcode barcode : barcodes) {
                            getBarcodeInfo(barcode);
                        }
                    }
                }).addOnFailureListener(e -> {
                    System.out.println(e);
                });
    }

    /**
     * This method is used to get the information from a barcode passed to it
     * @param barcode A Barcode object that this method gets info from
     */
    public void getBarcodeInfo(Barcode barcode){
        Rect borders = barcode.getBoundingBox();
        int barcodeType = barcode.getFormat();
        String rawValue;
        if (barcodeType == Barcode.FORMAT_UNKNOWN){
            System.out.println("The barcode couldn't be recognized. Please retake the photo or use a different barcode");

        }else{
            rawValue = barcode.getRawValue();
            System.out.println(rawValue);
        }

    }


}
