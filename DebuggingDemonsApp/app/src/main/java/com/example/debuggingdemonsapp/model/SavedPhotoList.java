package com.example.debuggingdemonsapp.model;

import com.example.debuggingdemonsapp.model.Photograph;


import java.util.ArrayList;

public class SavedPhotoList {
   ArrayList<Integer> imageViewIDs;
   ArrayList<Photograph> photos;
   public SavedPhotoList(){
      this.photos = new ArrayList<Photograph>();
      this.imageViewIDs = new ArrayList<Integer>();
   }

   public ArrayList<Photograph> getPhotos(){
      return this.photos;
   }

   public void addPhoto(Photograph newPhoto){
      this.photos.add(0,newPhoto);
   }
   public void addID(Integer imageViewID){
      this.imageViewIDs.add(0,imageViewID);
   }
   public void deletePhoto(Photograph photo){
      this.photos.remove(photo);
   }

   public boolean isEmpty(){
      return photos.isEmpty();
   }
}
