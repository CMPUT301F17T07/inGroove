package com.cmput301f17t07.ingroove.ViewFollowRequests;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashley on 2017-10-30.
 */

public class FollowRequestAdapter extends ArrayAdapter<User> implements View.OnClickListener {

    // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
    // was referenced while implementing this class

    ArrayList<User> requestingFollowers;
    Context context;

    // view lookup cache
    private static class ViewHolder {
        ImageButton acceptButton;
        ImageButton rejectButton;
        TextView requesterInfo;
    }

    /**
     * Constructor for this adapter, creates the object
     *
     * @param requestingFollowers list of people wanting to follow user
     * @param context the context
     */
    public FollowRequestAdapter(ArrayList<User> requestingFollowers, Context context) {
        super(context, R.layout.list_item_activity_follow_requests, requestingFollowers);
        this.requestingFollowers = requestingFollowers;
        this.context = context;
    }

    /**
     *
     * @param v the given view
     */
    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        User user = (User) getItem(position);

        switch (v.getId()) {
            case R.id.acceptFollowRequestButton:
                // do something to accept the request
                Log.w("TEST TEST TEST!!!!!!", "Accepting the follow request or something...");
                break;
            case R.id.rejectFollowRequestButton:
                // do something to reject the request
                Log.w("TEST TEST TEST!!!!!!", "Rejecting the follow request or something...");
                break;

        }

    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User user = getItem(position);
        ViewHolder viewHolder;
        View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_activity_follow_requests, parent, false);
            viewHolder.acceptButton = (ImageButton) convertView.findViewById(R.id.acceptFollowRequestButton);
            viewHolder.rejectButton = (ImageButton) convertView.findViewById(R.id.rejectFollowRequestButton);
            viewHolder.requesterInfo = (TextView) convertView.findViewById(R.id.followerInfo);
            result = convertView;
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;

        }

        viewHolder.requesterInfo.setText(user.getName());
        viewHolder.acceptButton.setOnClickListener(this);
        viewHolder.acceptButton.setTag(position);
        viewHolder.rejectButton.setOnClickListener(this);
        viewHolder.rejectButton.setTag(position);
        
        return convertView;
    }

}
