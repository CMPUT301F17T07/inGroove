package com.cmput301f17t07.ingroove.Model;

/**
 * An interface for all objects that contain a string ID to identify them
 *
 * Created by Fraser Bulbuc on 2017-11-10.
 */

public interface Identifiable {

    /**
     * Must provide access to their local string ID that identifies that object
     *
     * @return the local String ID for that object
     * @return null means error needs a userId
     */
    String getLocalID();


    /**
     * Provides access to the id used for elastic search
     * the id is a combination of the user id and and its local id
     *
     * @return "userId" + "localId"
     */
    String getServerID();

}
