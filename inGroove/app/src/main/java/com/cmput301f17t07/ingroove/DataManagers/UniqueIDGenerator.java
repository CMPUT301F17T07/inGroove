package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.Model.Identifiable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Fraser Bulbuc on 2017-11-10.
 */

public class UniqueIDGenerator {

    private ArrayList<? extends Identifiable> objs;

    public UniqueIDGenerator(ArrayList<? extends Identifiable> objs) {
        this.objs = objs;
    }

    public String generateNewID(){
        while (true) {
            String uniqueID = UUID.randomUUID().toString();
            uniqueID = uniqueID.replace("-","");

            if (objs.size()>=0 && isUnique(uniqueID)) {
                return uniqueID;
            }
        }
    }

    private boolean isUnique(String guessedID) {
        for ( Identifiable identifiable: objs) {
            if (guessedID.equals(identifiable.getID())) {
                return false;
            }
        }
        return true;
    }


}
