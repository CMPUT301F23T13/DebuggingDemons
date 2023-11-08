package com.example.debuggingdemonsapp.model;

import android.graphics.drawable.Drawable;

public class Item {
    private String description;
    private String dateOfPurchase;
    private String make;
    private String model;
    private String serialNumber;
    private String estimatedValue;
    private String comment;
    private byte[] image1;
    private Drawable image2;
    private Drawable image3;

    // Constructors
    public Item() {}

    // Constructor with all fields for convenience
    public Item(String description, String dateOfPurchase, String make, String model, String serialNumber, String estimatedValue, String comment, byte[] image1) {
        this.description = description;
        this.dateOfPurchase = dateOfPurchase;
        this.make = make;
        this.model = model;
        this.serialNumber = serialNumber;
        this.estimatedValue = estimatedValue;
        this.comment = comment;
        this.image1 = image1;
//        this.image2 = image2;
//        this.image3 = image3;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(String estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public byte[] getImage1() {return this.image1;}

    public void setImage1(byte[] image1) {this.image1 = image1;}

    public Drawable getImage2() {return this.image2;}

    public void setImage2(Drawable image2) {this.image2 = image2;}

    public Drawable getImage3() {return this.image3;}

    public void setImage3(Drawable image3) {this.image3 = image3;}
}
