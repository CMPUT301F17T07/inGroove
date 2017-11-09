package com.cmput301f17t07.ingroove.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ashley on 2017-10-22.
 */

public class User implements Serializable{

    private String name;
    private String email;
    private String userID;
    private Date joinDate;
    private int streak;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.joinDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User temp = (User) obj;
        if (temp.getUserID() == this.userID) {
            return true;
        } else {
            return false;
        }

    }
}
