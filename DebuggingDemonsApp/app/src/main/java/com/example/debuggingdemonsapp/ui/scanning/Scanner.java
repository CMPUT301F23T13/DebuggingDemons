package com.example.debuggingdemonsapp.ui.scanning;

import android.graphics.Bitmap;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
     *
     * @param image           A Bitmap object which holds the bitmap of the image that is to be scanned
     * @param rotationDegrees An integer object which holds the rotation degrees of the image
     */
    public MutableLiveData<List> scanImageBarcodes(Bitmap image, int rotationDegrees){
        MutableLiveData<List> mutableLiveData = new MutableLiveData<>();
        // Code adapted from https://developers.google.com/ml-kit/vision/barcode-scanning/android
        Task<List<Barcode>> getimageBarcodes = this.imageScanner.process(InputImage.fromBitmap(image, rotationDegrees)) // The bitmap is changed to an InputImage object which the scanner can use
                .addOnFailureListener(e -> {
                    System.out.println(e);
                }).addOnCompleteListener(task -> {
                    List<String> barcodeValues = new ArrayList<>();
                    if(!task.getResult().isEmpty()) {
                        // The following code gets the raw string values for each barcode in the barcode list that the imageScanner gets
                        for (Barcode barcode : task.getResult()) {
                            String barcodeValue = getBarcodeInfo(barcode);
                            if(barcodeValue != null){
                                barcodeValues.add(barcodeValue);
                            }
                        }
                    }
                    mutableLiveData.setValue(barcodeValues);
                });

        return mutableLiveData;

    }

    public MutableLiveData<String> scanSerialNumber(Bitmap image, int rotationDegrees){
        MutableLiveData<String> serialMutableData = new MutableLiveData<>();
        // Code from https://developers.google.com/ml-kit/vision/text-recognition/v2/android for creating the text recognizer
        TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        Task<Text> getSerialNumbers = textRecognizer.process(InputImage.fromBitmap(image,rotationDegrees))
                .addOnFailureListener(e -> {
                    System.out.println(e);
                })
                .addOnCompleteListener(new OnCompleteListener<Text>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Text> task) {
                        String Text = task.getResult().getText();
                        for(Text.TextBlock block : task.getResult().getTextBlocks()){
                            for(Text.Line line: block.getLines()){
                                if(line.getText().contains("Serial") || line.getText().contains("S/N") || line.getText().contains("serial")){
                                    serialMutableData.setValue(line.getText());
                                }
                            }
                        }

//                        System.out.println(task.getResult());
                    }
                });
        return serialMutableData;
    }

    /**
     * This method is used to get the information from a barcode passed to it
     * @param barcode A Barcode object that this method gets info from
     */
    public String getBarcodeInfo(Barcode barcode){
        Rect borders = barcode.getBoundingBox();
        int barcodeType = barcode.getFormat();
        String rawValue = null;
        if (barcodeType == Barcode.FORMAT_UNKNOWN){
            System.out.println("The barcode couldn't be recognized. Please retake the photo or use a different barcode");

        }else{
            rawValue = barcode.getRawValue();

        }
        return rawValue;
    }


}
