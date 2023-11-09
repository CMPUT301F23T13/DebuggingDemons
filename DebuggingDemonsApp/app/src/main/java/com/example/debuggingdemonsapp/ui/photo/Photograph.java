package com.example.debuggingdemonsapp.ui.photo;

import android.graphics.Bitmap;

public class Photograph {
    private Bitmap photo;

    public Photograph(Bitmap savedPhoto){
        this.photo = savedPhoto;
    }

    public Bitmap photoBitmap(){
        return this.photo;
    }


}