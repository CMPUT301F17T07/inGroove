package com.cmput301f17t07.ingroove.Model;

import android.graphics.Bitmap;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HabitEvent {

    private String name;
    private String comment;
    private Date day;
    private Bitmap photo;
    private String eventID;
    private String habitID;
    private String userID;
    private String location;

    //Name getter/setter
    public String getName() { return name; }
    public void setName(String name) {this.name = name;}
    //Comment getter/setter
    public String getComment() { return comment; }
    public void setComment(String comment) {this.comment = comment;}
    //Day getter/setter
    public Date getDay() { return day; }
    public void setDay(Date day) {this.day = day;}
    //Photo getter/setter
    public Bitmap getPhoto() { return photo; }
    public void setPhoto(Bitmap photo) {this.photo = photo;}
    //ID getter/setter
    public String getEventID() { return eventID; }
    public void setEventID(String id) {this.eventID = id;}
    //Location getter/setter
    public String getLocation() { return location; }
    public void setLocation(String location) {this.location = location;}

    public String getHabitID() {
        return habitID;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    //Constructors.
    public HabitEvent(String name, String comment, Date day, Bitmap photo, String HabitID, String UserID, String location){
        this.name = name;
        this.comment = comment;
        this.day = day;
        this.photo = photo;
        this.habitID = HabitID;
        this.userID = UserID;
        this.location = location;
    }

    public HabitEvent(String name, String comment, Date day, String habitID, String userID) {
        this.name = name;
        this.comment = comment;
        this.day = day;
        this.habitID = habitID;
        this.userID = userID;
    }

    public HabitEvent(String name, Date day) {
        this.name = name;
        this.day = day;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(this.day);
        return 	this.name + ": " + dateStr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        HabitEvent temp = (HabitEvent) obj;
        if(temp.getName() == this.name && temp.getDay() == this.day && temp.getEventID() == this.eventID)
            return true;
        else
            return false;
    }
}