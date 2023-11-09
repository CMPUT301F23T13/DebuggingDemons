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
    private String image1;
    private String image2;
    private String image3;

    private boolean isSelected; // Used to track whether an entry is selected.
    private String id; // Used to uniquely identify an entry in the database.

    // Constructors
    public Item() {}

    // Constructor with all fields for convenience
    public Item(String description, String dateOfPurchase, String make, String model, String serialNumber, String estimatedValue, String comment, String image1, String image2, String image3) {
        this.description = description;
        this.dateOfPurchase = dateOfPurchase;
        this.make = make;
        this.model = model;
        this.serialNumber = serialNumber;
        this.estimatedValue = estimatedValue;
        this.comment = comment;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
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

    public String getImage1() {return this.image1;}

    public void setImage1(String image1) {this.image1 = image1;}

    public String getImage2() {return this.image2;}

    public void setImage2(String image2) {this.image2 = image2;}

    public String getImage3() {return this.image3;}

    public void setImage3(String image3) {this.image3 = image3;}


    // Getters and Setters for the new attributes

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
