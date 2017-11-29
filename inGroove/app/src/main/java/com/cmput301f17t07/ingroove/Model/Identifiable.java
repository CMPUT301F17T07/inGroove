package com.cmput301f17t07.ingroove.Model;

/**
 * An interface for all objects that contain a string ID to identify them
 *
 * Created by Fraser Bulbuc on 2017-11-10.
 */

public interface Identifiable {

    /**
     * Provides the Unique id for this object
     * can be used locally as well as in Elastic Search
     *
     * @return the Unique String ID for that object
     */
    String getObjectID();

    /**
     * Used to set the object id
     * Must be Unique across all devices running this app
     *
     * ie set to userId + an id unique to this device
     * @param uniqueObjectId an id that uniquely identifies this object
     *
     */
    void setObjectID(String uniqueObjectId);




}
