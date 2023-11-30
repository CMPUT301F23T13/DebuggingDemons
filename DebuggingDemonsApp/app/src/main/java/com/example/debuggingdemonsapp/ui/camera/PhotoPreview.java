package com.example.debuggingdemonsapp.ui.camera;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentPictureBinding;
import com.example.debuggingdemonsapp.ui.scanning.Scanner;
import com.example.debuggingdemonsapp.model.Photograph;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;

/**
 * This is a fragment which displays the photo after the user has taken it with the options to retake the image or save
 */
public class PhotoPreview extends Fragment {

    private FragmentPictureBinding binding;
    private Scanner imageScanner;
    private int rotationDegrees;
    private Photograph newPhoto;
    private FirebaseStorage storage;

    private Bitmap newImage;


    /**
     *  This method is used to create the fragment view which shows a preview of the photo taken.
     *      Also allows user to choose whether they want to retake the photo or save it.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View The view object that is used to display the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPictureBinding.inflate(inflater, container, false);
        newImage = getArguments().getParcelable("Image");
        newPhoto = new Photograph(newImage);

        binding.imageView.setImageBitmap(newImage);

        storage = FirebaseStorage.getInstance();

        imageScanner = new Scanner();
        rotationDegrees = getArguments().getInt("Rotation");



        // Calls 'enable' method from MainActivity with 'false' passed in, which makes it so that you can't press the bottom navigation icons
        ((MainActivity) getActivity()).enable(false);

        binding.retakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calls 'enable' method from MainActivity with 'true' passed in, which makes it so that you can press the bottom navigation icons
                backToCamera(container);
            }
        });

        binding.scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Scans the image for any serial numbers/barcodes
                imageScan();
            }
        });
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               savePhoto(container);
               backToCamera(container);
            }
        });


        View root = binding.getRoot();

        return root;
    }

    /**
     * This method is used to convert the list of barcode values into a long string separated by newline characters
     * @param barcodeList A list of the raw value(s) from the barcode(s) that were scanned
     * @return String  A string object which contains the list of barcodes in a format that the dialog message builder can take
     */
    private String displayBarcodes(List<String> barcodeList){
        StringBuilder barcodeMessage = new StringBuilder();
        for(String value: barcodeList){
            barcodeMessage.append("Barcode " + (barcodeList.indexOf(value)+1) +": ");
            if (barcodeList.indexOf(value) == barcodeList.size()-1){
                barcodeMessage.append(value);
            }else{
                barcodeMessage.append(value).append('\n');
            }

        }
        return barcodeMessage.toString();
    }

    private void imageScan(){
        MutableLiveData<List> barcodeData = imageScanner.scanImageBarcodes(newPhoto.photoBitmap(),rotationDegrees);

        imageScanner.scanSerialNumber(newPhoto.photoBitmap(), rotationDegrees);
        barcodeData.observe(getViewLifecycleOwner(), list -> {
            if(list.isEmpty()){
                Toast.makeText(getContext(),"No Barcode Found", Toast.LENGTH_SHORT).show();
            }else{
                // Add pop-up which displays barcode information
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Barcode(s) Found")
                        .setMessage(displayBarcodes(list))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Save photo to save information", Toast.LENGTH_SHORT).show();
                                // The barcode values of the photo is set to the list of strings received from the imageScanner barcode scanner
                                newPhoto.setBarcodeValues(barcodeData.getValue());


                            }
                        })
                        .create()
                        .show();
            }
        });
    }
    /**
     * This method is used to save the photo when the 'save button' is pressed
     * @param container This is a ViewGroup object that is used to
     */
    public void savePhoto(ViewGroup container){

        // Adding images to the Firestore Storage from https://firebase.google.com/docs/storage/web/upload-files
        // A directory is created for the current_user's photos if it doesn't already exist
        // Photos are added with the name 'image' + the current size of the items list

        // Outstanding Issue (to be fixed for project part 4): When the app closes, the appPhotos list resets but the database still has
        //    all the photos. Thus, when this line runs it overwrites old photos. Need to have appPhotos fetch data from database.
        StorageReference storageRef = storage.getReference(((MainActivity) getActivity()).current_user+"/image"+((MainActivity) getActivity()).appPhotos.getPhotos().size()+".jpg");
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

        // Needed to rotate image for it to be in the right orientation in the database
        // Need to figure out how to have image orientation based on camera/phone orientation
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap rotatednewPhoto = Bitmap.createBitmap(newPhoto.photoBitmap(),0,0, newPhoto.photoBitmap().getWidth(),newPhoto.photoBitmap().getHeight(),matrix,true);

        rotatednewPhoto.compress(Bitmap.CompressFormat.JPEG, 100, byteOutput);
        byte[] imageData = byteOutput.toByteArray();


        // Upload the image data to the firebase storage associated with the current_user
        storageRef.putBytes(imageData).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Sets the Uri in the newPhoto Photograph object to the Uri that firebase storage creates
                        // This is so that it can be used later on for display images
                        newPhoto.setUri(uri.toString());
                    }
                });

            }
        });

        //
        ((MainActivity) getActivity()).appPhotos.addPhoto(newPhoto);


        backToCamera(container);
        storageRef.putBytes(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        newPhoto.setUri(uri.toString());

                        Map<String, Object> imageInfo = new HashMap<>();
                        imageInfo.put("url", uri.toString());
                        imageInfo.put("timestamp", FieldValue.serverTimestamp());

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("images")
                                .add(imageInfo)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(getContext(), "Photo uploaded and saved.", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Error saving photo.", Toast.LENGTH_SHORT).show();
                                });
                    }
                });
            }
        });
    }
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

    /**
     * Navigates back to the Camera screen which is used to take photos
     * @param container The container object is from onCreateView and is used for navigation
     */
    public void backToCamera(ViewGroup container){
            ((MainActivity) getActivity()).enable(true);
            findNavController(container).navigate(R.id.navigation_camera);
        }

}
