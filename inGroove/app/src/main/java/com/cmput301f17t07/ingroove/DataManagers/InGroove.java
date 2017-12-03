package com.cmput301f17t07.ingroove.DataManagers;

import android.app.Application;

/**
 * [Utility Class]
 *
 * Used to get a Context for save/Loading from files
 *
 * referenced:
 * https://stackoverflow.com/questions/17441295/android-context-without-being-in-an-activity-and-other-activity-less-programmin
 *
 * Created by Christopher Walter on 2017-11-10.
 *
 * @see android.content.Context
 * @see DataManager
 * @see HabitManager
 * @see HabitEventManager
 *
 */
public class InGroove extends Application {

    private static InGroove instance;

    public InGroove() {
        instance = this;
    }

    public static InGroove getInstance() {
        return instance;
    }
}
