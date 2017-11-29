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
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter for the ListView in the Follow Request Activity. Allows the user to press accept
 * and reject on the displayed follow requests.
 *
 * @see FollowRequestsActivity
 * @see DataManager
 * @see DataManagerAPI
 *
 * Created by Ashley on 2017-10-30.
 */

public class FollowRequestAdapter extends ArrayAdapter<User> implements View.OnClickListener {

    // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
    // this tutorial was used to help implement this class

    DataManagerAPI data = DataManager.getInstance();

    ArrayList<User> requestingFollowers;
    Context context;

    /**
     *  ViewHolder functions as a convenient way/cache to access and store the needed data.
     */
    private static class ViewHolder {
        ImageButton acceptButton;
        ImageButton rejectButton;
        TextView requesterInfo;
    }

    /**
     * Constructor for this adapter, creates the adapter object.
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
     * Deals with the users choice to press the accept or reject a follow request buttons.
     *
     * @param v the given view
     */
    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        User otherUser = (User) getItem(position);

        switch (v.getId()) {
            case R.id.acceptFollowRequestButton:
                // accept the follow request such that otherUser will now be following currentUser
                data.acceptRequest(otherUser);
                Log.i("Follow Request Info", "Accepting follow request from " + otherUser.getName());
                break;
            case R.id.rejectFollowRequestButton:
                // reject the follow request from otherUser
                data.rejectRequest(otherUser);
                Log.i("Follow Request Info", "Rejecting the follow request from " + otherUser.getName());
                break;

        }
        
        // now we need to remove the follow request from the list


    }

    /**
     * Gets the element to display in the list view.
     *
     * @param position position on the list view
     * @param convertView the view element
     * @param parent the elements parent
     *
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User user = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_activity_follow_requests, parent, false);
            viewHolder.acceptButton = (ImageButton) convertView.findViewById(R.id.acceptFollowRequestButton);
            viewHolder.rejectButton = (ImageButton) convertView.findViewById(R.id.rejectFollowRequestButton);
            viewHolder.requesterInfo = (TextView) convertView.findViewById(R.id.followerInfo);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.requesterInfo.setText(user.getName());
        viewHolder.acceptButton.setOnClickListener(this);
        viewHolder.acceptButton.setTag(position);
        viewHolder.rejectButton.setOnClickListener(this);
        viewHolder.rejectButton.setTag(position);

        return convertView;
    }

}
