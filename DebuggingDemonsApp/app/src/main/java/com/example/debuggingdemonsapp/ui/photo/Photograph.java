package com.example.debuggingdemonsapp.ui.photo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;


public class Photograph implements Parcelable {
    private Bitmap photo;
    private String uri = "";

    public Photograph(Bitmap savedPhoto){
        this.photo = savedPhoto;
    }

    protected Photograph(Parcel in) {
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        uri = in.readString();
    }

    public static final Creator<Photograph> CREATOR = new Creator<Photograph>() {
        @Override
        public Photograph createFromParcel(Parcel in) {
            return new Photograph(in);
        }

        @Override
        public Photograph[] newArray(int size) {
            return new Photograph[size];
        }
    };

    public Bitmap photoBitmap(){
        return this.photo;
    }

    public String storageURI(){
        return this.uri;
    }

    public void setUri(String databaseURI){
        this.uri = databaseURI;
    }

    public boolean matchURI(String otherUri){
        return this.uri == otherUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeArray(new Object[] {this.uri, this.photo});
    }
}