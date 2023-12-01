package com.example.debuggingdemonsapp.model;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

/**
 * Model class for an Item object. Holds all properties of a Item object along with getter and setter methods of object
 */
public class Item {
    private String description;
    private String dateOfPurchase;
    private String make;
    private String model;
    private String serialNumber;
    private String estimatedValue;
    private String comment;
    private ArrayList<String> tagNames;

    private List<String> imageUris;
    // getters and setters for imageUris
    private String image1;
    private String image2;
    private String image3;

    private String id; // Used to uniquely identify an entry in the database.

    /**
     * Constructors
      */

    public Item() {
        tagNames = new ArrayList<>();
    }

    /**
     * Constructor with all fields for convenience
     */
    public Item(String description, String dateOfPurchase, String make, String model, String serialNumber, String estimatedValue, String comment, String image1, String image2, String image3, ArrayList<String> tagNames) {
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
        this.tagNames = tagNames;
        sort(this.tagNames);
    }

    /**
     *  Getters and Setters
      */

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

    public ArrayList<String> getTagNames() {
        return tagNames;
    }

    public String compareTagNames() {
        return tagNames.toString();
    }

    public void addTag(Tag tag) {
        String name = tag.getName();
        if (!tagNames.contains(name)) {
            tagNames.add(name);
            sort(tagNames);
        }
    }

    public String getImage1() {return this.image1;}

    public void setImage1(String image1) {this.image1 = image1;}

    public String getImage2() {return this.image2;}

    public void setImage2(String image2) {this.image2 = image2;}

    public String getImage3() {return this.image3;}

    public void setImage3(String image3) {this.image3 = image3;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
