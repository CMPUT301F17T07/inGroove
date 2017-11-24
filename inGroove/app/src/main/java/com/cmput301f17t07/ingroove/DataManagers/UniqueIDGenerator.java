package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.Model.Identifiable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Generates a unique ID using a guessing and check scheme for new object being inserted into a list
 * of Identifiable objects
 *
 * @see Identifiable
 * @see UUID
 *
 * Created by Fraser Bulbuc on 2017-11-10.
 */

public class UniqueIDGenerator {

    private ArrayList<? extends Identifiable> objs;
    private final int LENGTH_OF_ID = 5;

    /**
     * Constructs a new UniqueIDGenerator instance
     *
     * @param objs a list of identifiable objects for which the ID should be unique with respect to
     * @see Identifiable
     */
    public UniqueIDGenerator(ArrayList<? extends Identifiable> objs) {
        this.objs = objs;
    }

    /**
     * Generates a new string ID
     *
     * @return a string representing the unique ID
     * @see Identifiable
     */
    public String generateNewID(){
        while (true) {

            // guess an ID
            String uniqueID = UUID.randomUUID().toString();
            uniqueID = uniqueID.replace("-","").substring(0, Math.min(uniqueID.length(), LENGTH_OF_ID));

            // check if it is unique
            if (objs.size()>=0 && isUnique(uniqueID)) {
                return uniqueID;
            }
        }
    }

    /**
     * Checks if the ID guessed was unique
     *
     * @param guessedID the random ID guessed
     *
     * @return true if it is unique, false if it already exists
     * @see Identifiable
     */
    private boolean isUnique(String guessedID) {
        for ( Identifiable identifiable: objs) {
            if (guessedID.equals(identifiable.getObjectID())) {
                return false;
            }
        }
        return true;
    }


}
