package com.cmput301f17t07.ingroove.ViewFollowRequests;

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

public class FollowRequestAdapter extends ArrayAdapter<User> implements View.OnClickListener {

    private ArrayList<User> requestingFollowers;
    Context context;

    public FollowRequestAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.acceptFollowRequestButton:
                // do something to accept the request
                break;
            case R.id.rejectFollowRequestButton:
                // do something to reject the request
                break;

        }
    }
    // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
    // was referenced while implementing this class



}
