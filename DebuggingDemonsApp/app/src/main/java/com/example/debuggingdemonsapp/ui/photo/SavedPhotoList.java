package com.example.debuggingdemonsapp.ui.photo;

import com.example.debuggingdemonsapp.model.Photograph;


import java.util.ArrayList;

public class SavedPhotoList {
   ArrayList<Photograph> photos;
   public SavedPhotoList(){
      this.photos = new ArrayList<Photograph>();
   }

   public ArrayList<Photograph> getPhotos(){
      return this.photos;
   }

   public void addPhoto(Photograph newPhoto){
      this.photos.add(0,newPhoto);
   }

   public void deletePhoto(Photograph photo){
      this.photos.remove(photo);
   }

   public boolean isEmpty(){
      return photos.isEmpty();
   }
}
