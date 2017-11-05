package com.cmput301f17t07.ingroove.FollowOtherUsers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;

import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashley on 2017-10-30.
 */

public class FollowAdapter extends ArrayAdapter<User> implements View.OnClickListener {

    private ArrayList<User> searchForUsersList;
    Context context;

    public FollowAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @Override
    public void onClick(View v) {
        // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
        // was referenced while implementing this class

        switch (v.getId()) {
            case R.id.requestToFollowUserButton:
                // do something to send the request
                break;
        }
    }





}
