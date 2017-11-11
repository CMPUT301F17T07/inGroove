package com.cmput301f17t07.ingroove.DataManagers;

import android.app.Application;

/**
 * Created by Christopher Walter on 2017-11-10.
 *
 * https://stackoverflow.com/questions/17441295/android-context-without-being-in-an-activity-and-other-activity-less-programmin
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
