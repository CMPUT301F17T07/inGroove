package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import java.util.ArrayList;

/**
 * Interface to return data from background thread
 *
 * Created by Fraser Bulbuc on 2017-11-26.
 */
public interface AsyncResultHandler<T> {

    void handleResult(ArrayList<T> result);

}
