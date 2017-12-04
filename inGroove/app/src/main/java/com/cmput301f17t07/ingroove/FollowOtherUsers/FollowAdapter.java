package com.cmput301f17t07.ingroove.FollowOtherUsers;

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
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

import java.util.ArrayList;

/**
 * [Adapter Class]
 *
 * The adapter for the ListView in the Follow Activity. Allows the user to press the send
 * follow request button for given users.
 *
 * @see FollowActivity
 * @see DataManager
 * @see DataManagerAPI
 */

public class FollowAdapter extends ArrayAdapter<User> implements View.OnClickListener {

    DataManagerAPI data = DataManager.getInstance();

    ArrayList<User> searchResults;
    Context context;

    Boolean successfullySent;

    /**
     *  ViewHolder functions as a convenient way/cache to access and store the needed data.
     */
    private static class ViewHolder {
        TextView userInfo;
        TextView userStreak;
        ImageButton sendRequestButton;
    }

    /**
     * Constructor for the adapter, creates the adapter object.
     *
     * @param searchResults list of people who fit the search criteria
     * @param context the context
     */
    public FollowAdapter(ArrayList<User> searchResults, Context context) {
        super(context, R.layout.list_item_follow_activity, searchResults);
        this.searchResults = searchResults;
        this.context = context;
    }

    /**
     * Deals with the user pressing the button to send the follow request.
     *
     * @param v the given view
     */
    @Override
    public void onClick(View v) {
        // https://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
        // was referenced while implementing this class

        int position = (Integer) v.getTag();
        User otherUser = (User) getItem(position);

        if (v.getId()== R.id.sendFollowRequestButton) {
            successfullySent = data.sendFollowRequest(otherUser);

            // check to see if the request was successfully sent and update list view to remove them
            if (successfullySent) {
                searchResults.remove(otherUser);
                notifyDataSetChanged();
                Log.i("Follow Request Info", "Requesting to follow" + otherUser.getName());
            } else {
                Log.i("Follow Request Info", "Unable to request to follow" + otherUser.getName());
            }
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
            convertView = inflater.inflate(R.layout.list_item_follow_activity, parent, false);

            viewHolder.sendRequestButton = (ImageButton) convertView.findViewById(R.id.sendFollowRequestButton);
            viewHolder.userInfo = (TextView) convertView.findViewById(R.id.otherUsersInfo);
            viewHolder.userStreak = (TextView) convertView.findViewById(R.id.otherUserStreak);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.userInfo.setText(user.getName());
        viewHolder.userStreak.setText("Current Streak: " + user.getStreak() + "    Max Streak: " + user.getMax_streak());
        viewHolder.sendRequestButton.setOnClickListener(this);
        viewHolder.sendRequestButton.setTag(position);

        return convertView;
    }

}
