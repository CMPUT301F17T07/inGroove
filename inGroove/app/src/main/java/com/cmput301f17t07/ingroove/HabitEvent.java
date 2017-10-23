package com.cmput301f17t07.ingroove;

import android.graphics.Bitmap;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HabitEvent {

    private String name;
    private String comment;
    private Date day;
    private Bitmap photo;
    private int id;
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
    public int getID() { return id; }
    public void setID(int id) {this.id = id;}
    //Location getter/setter
    public String getLocation() { return location; }
    public void setLocation(String location) {this.location = location;}

    //Constructors.
    public HabitEvent(String name, String comment, Date day, Bitmap photo, int id, String location){
        this.name = name;
        this.comment = comment;
        this.day = day;
        this.photo = photo;
        this.id = id;
        this.location = location;
    }
    //These are extra constructors. Not really needed/finished but nice to have.
    public HabitEvent(String name, Date day){
        this(name, "", day, null, 0, "");
    }
    public HabitEvent(String name, Date day, String location){
        this(name, "", day, null, 0, location);
    }
    public HabitEvent(String name, String comment, Date day){
        this(name, comment, day, null, 0, "");
    }
    public HabitEvent(String name, String comment, Date day, String location){
        this(name, comment, day, null, 0, location);
    }
    public HabitEvent(String name, Date day, Bitmap photo){
        this(name, "", day, photo, 0, "");
    }
    public HabitEvent(String name, Date day, Bitmap photo, String location){
        this(name, "", day, photo, 0, location);
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
        if(temp.getName() == this.name && temp.getDay() == this.day && temp.getID() == this.id)
            return true;
        else
            return false;
    }
}