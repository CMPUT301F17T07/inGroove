package com.cmput301f17t07.ingroove.Model;

import java.util.Date;

/**
 * Created by Ashley on 2017-10-22.
 */

public class Follow {

    private int follower;
    private int followee;
    private Boolean accepted;
    private Date requestedDate;
    private Date acceptedDate;


    public Follow(int follower, int followee) {
        this.follower = follower;
        this.followee = followee;
        this.accepted = Boolean.FALSE;
        acceptedDate = new Date();
    }

    public int getFollower() {
        return follower;
    }

    public int getFollowee() {
        return followee;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public Date getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate) {
        this.acceptedDate = acceptedDate;
    }


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
