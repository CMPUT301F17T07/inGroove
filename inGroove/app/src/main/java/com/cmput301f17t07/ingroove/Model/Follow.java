package com.cmput301f17t07.ingroove.Model;

import java.util.Date;

/**
 * Represents the relationships between Users
 *
 * @see User
 */

public class Follow {

    private int follower;
    private int followee;
    private Boolean accepted;
    private Date requestedDate;
    private Date acceptedDate;


    /**
     * Default constructor
     *
     * @param follower the following User
     * @param followee the User being followed
     */
    public Follow(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
        this.accepted = Boolean.FALSE;
        acceptedDate = new Date();
    }

    /**
     * Get follower
     */
    public int getFollower() {
        return follower;
    }

    /**
     * Get followee
     * @return followee ID
     */
    public int getFollowee() {
        return followee;
    }

    /**
     * Get status
     * @return
     */
    public Boolean getAccepted() {
        return accepted;
    }

    /**
     * Set status
     *
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * Get date of request
     *
     * @return the date of request
     */
    public Date getRequestedDate() {
        return requestedDate;
    }

    /**
     * Get the date the request was accepted
     *
     * @return the date of accpetance
     */
    public Date getAcceptedDate() {
        return acceptedDate;
    }

    /**
     * Update the acceptance date
     *
     * @param acceptedDate the new date it was accepted
     */
    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }


    /**
     * For comparison of follow objects
     *
     * @param obj the object to be compared
     * @return true if they are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Follow temp = (Follow) obj;
        if (temp.getFollower() == this.getFollower() && temp.getFollowee() == this.getFollowee()) {
            return true;
        } else {
            return false;
        }
    }
}
