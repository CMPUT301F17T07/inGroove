package ingroove;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Christopher Walter on 2017-10-17.
 */

public class Follow {

    private int follower;
    private int followee;
    private Boolean accepted;
    private Date dateAccepted;

    public Follow(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
        dateAccepted = new Date();
        accepted = Boolean.FALSE;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getFollowee() {
        return followee;
    }

    public void setFollowee(int followee) {
        this.followee = followee;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Date getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(Date dateAccepted) {
        this.dateAccepted = dateAccepted;
    }



    public static ArrayList<Integer> getFollowersOf(User user) {

    }

    public static ArrayList<Integer> getWhoFollows(User user) {

    }

    public static ArrayList<Integer> getRequestsToFollow(User user) {

    }

    public static ArrayList<Integer> getRequestBy(User user) {

    }



}
