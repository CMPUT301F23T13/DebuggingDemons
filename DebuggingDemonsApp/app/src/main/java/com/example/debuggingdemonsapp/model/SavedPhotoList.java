package com.example.debuggingdemonsapp.model;




import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * This class is for creating a SavedPhotoList object which is holds a list of saved photos
 */
public class SavedPhotoList {

   ArrayList<Photograph> photos;

   /**
    * Constructor object for the class
    */
   public SavedPhotoList(){
      this.photos = new ArrayList<Photograph>();

   }

   /**
    * Method which returns the saved photos list
    * @return ArrayList<Photograph> Returns an array list of saved photos
    */
   public ArrayList<Photograph> getPhotos(){
      return this.photos;
   }

   /**
    * Method is used to add a new Photograph object to the beginning of the list of photos
    * @param newPhoto Photograph object which should be added to list
    */
   public void addPhoto(Photograph newPhoto){
      this.photos.add(0,newPhoto);
   }

   /**
    * Method is used to append a new Photograph object to the end of the list of photos
    * @param newPhoto Photograph object which should be added to list
    */
   public void appendPhoto(Photograph newPhoto){this.photos.add(newPhoto);}

   /**
    * This method adds a given photo to the photos list at a specific position
    * @param position An int object representing the position that the photo should be in the list
    * @param newPhoto Photograph object representing the photo that is to be added to the list
    */
   public void addInPosition(int position,Photograph newPhoto){
      this.photos.add(position, newPhoto);
   }
   /**
    * Method is used to delete a specific Photograph object from the list of photos
    * @param photo Photograph object to delete from the list
    */
   public void deletePhoto(Photograph photo){
      this.photos.remove(photo);
   }

   /**
    * Method is used to check whether the saved photos list is empty or not
    * @return boolean corresponding to whether the list is empty or not
    */
   public boolean isEmpty(){
      return photos.isEmpty();
   }

   /**
    * This method is used to get the index of an item with a given bitmap in the list
    * @param bitmap This is a Bitmap object which represents an image's bitmap
    * @return An int object is returned which represents the index of the item
    */
   public int itemIndexWithBitmap(Bitmap bitmap){
      for (Photograph photo: this.photos){

         if(photo.photoBitmap() == bitmap){

            return this.photos.indexOf(photo);
         }
      }
      return -1;
   }
}
