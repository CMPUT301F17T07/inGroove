package com.cmput301f17t07.ingroove.Model;

/**
 * An interface for all objects that contain a string ID to identify them
 *
 * Created by Fraser Bulbuc on 2017-11-10.
 */

public interface Identifiable {

    /**
     * Must provide access to their string ID
     *
     * @return the String ID for that object
     */
    public String getID();
}
