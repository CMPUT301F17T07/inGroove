package ingroove;

import java.util.Date;

/**
 * Created by Christopher Walter on 2017-10-17.
 */


/**
 * class to handle the follow relationships
 *
 * @author Christopher Walter
 */
public class Follow {


    private int follower; // Id of the user following
    private int followee; // Id of the user being followed
    private Boolean accepted; /* shows weather the follow request is approved
                                 -False - the follow is pending
                                 -True  - the follow is approved
                                 - to reject the request the follow is deleted from the server
                                    */
    private Date dateAccepted; /* if the follow is approved then is the date the follow was approved
                                  else its the date the request was made
                               */


    /**
     *
     * @param follower
     * @param followee
     */
    public Follow(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
        dateAccepted = new Date();
        accepted = Boolean.FALSE;
    }

    /**
     * getter for the follower attribute
     * @return the ID of the follower
     */
    public int getFollower() {
        return follower;
    }

    /**
     * sets the follower ID
     * @param follower the ID of the new follower
     */
    public void setFollower(int follower) {
        this.follower = follower;
    }

    /**
     * getter for the followee attribute
     * @return the ID of the followee
     */
    public int getFollowee() {
        return followee;
    }

    /**
     * sets the followee ID
     * @param followee the ID of the new user being followed
     */
    public void setFollowee(int followee) {
        this.followee = followee;
    }

    /**
     * getter for the followee attribute
     * @return the ID of the follower
     */
    public Boolean getAccepted() {
        return accepted;
    }

    /**
     * sets the accepted attribute
     * @param accepted
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * getter for the dateAccepted attribute
     * @return the dateAccepted
     */
    public Date getDateAccepted() {
        return dateAccepted;
    }

    /**
     * sets the date the follow was accepted
     * @param dateAccepted the date the follow was accepted
     */
    public void setDateAccepted(Date dateAccepted) {
        this.dateAccepted = dateAccepted;
    }



}
