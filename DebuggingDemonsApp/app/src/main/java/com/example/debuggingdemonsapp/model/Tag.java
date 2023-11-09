package com.example.debuggingdemonsapp.model;

/**
 * This is a class that keeps track of a Tag along with its name
 */
public class Tag {
    private String name;

    /**
     * This creates a new Tag with a given name
     * @param name
     *     Name of Tag
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * This creates a new Tag
     */
    public Tag() {

    }

    /**
     * This returns the Tag's name
     * @return
     *     Return the Tag's name
     */
    public String getName() {
        return name;
    }

    /**
     * This sets the Tag's name to a given name
     * @param name
     *     New name for Tag
     */
    public void setName(String name) {
        this.name = name;
    }
}
