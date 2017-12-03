package com.cmput301f17t07.ingroove.ViewFollowRequests;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

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

public class FollowRequestAdapter extends ArrayAdapter<User> implements View.OnClickListener, AsyncResultHandler<User> {

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
        TextView requesterStreak;
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
        Boolean result;

        // @TODO figure out why requests are not sending

        switch (v.getId()) {

            case R.id.acceptFollowRequestButton:
                // accept the follow request such that otherUser will now be following currentUser
                result = data.acceptRequest(otherUser);
                requestingFollowers.remove(otherUser);
                notifyDataSetChanged();
                if (result == Boolean.TRUE) {
                    Log.i("Follow Request Info", "Accepting follow request from " + otherUser.getName());
                } else {
                    Log.i("Follow Request Info", "Unable to accept follow request from " + otherUser.getName());
                }
                break;
            case R.id.rejectFollowRequestButton:
                // reject the follow request from otherUser
                result = data.rejectRequest(otherUser, this);
                requestingFollowers.remove(otherUser);
                notifyDataSetChanged();
                if (result == Boolean.TRUE) {
                    Log.i("Follow Request Info", "Rejecting follow request from " + otherUser.getName());
                } else {
                    Log.i("Follow Request Info", "Unable to reject follow request from " + otherUser.getName());
                }
                break;

        }

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
            viewHolder.requesterStreak = (TextView) convertView.findViewById(R.id.followerStreak);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.requesterInfo.setText(user.getName());
        viewHolder.requesterStreak.setText("Current Streak: " + user.getStreak() + "    Max Streak: " + user.getMax_streak());
        viewHolder.acceptButton.setOnClickListener(this);
        viewHolder.acceptButton.setTag(position);
        viewHolder.rejectButton.setOnClickListener(this);
        viewHolder.rejectButton.setTag(position);

        return convertView;
    }

    /**
     * Get the new results for the adapter and notify that the data set has cahnged.
     *
     * @param result the new list of requesting followers
     */
    @Override
    public void handleResult(ArrayList<User> result) {
        requestingFollowers = result;
        notifyDataSetChanged();
    }

}
