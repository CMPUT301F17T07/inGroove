package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import java.util.ArrayList;

/**
 * [Model Class]
 * Interface to return data from background thread. All methods that rely on an Async task
 * need to be passed a handler that implements this interface so the return data can be
 * passed upon completion of the Async task
 *
 * Created by Fraser Bulbuc on 2017-11-26.
 */
public interface AsyncResultHandler<T> {

    void handleResult(ArrayList<T> result);

}
