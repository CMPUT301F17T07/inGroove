package com.cmput301f17t07.ingroove;

import java.util.Date;

/**
 * Created by Ashley on 2017-10-22.
 */

public class User {

    private String name;
    private String email;
    private int userID;
    private Date joinDate;
    private int streak;

    public User(String name, String email, int userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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
}
