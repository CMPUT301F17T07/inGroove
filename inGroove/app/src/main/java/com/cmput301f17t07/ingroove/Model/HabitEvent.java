package com.cmput301f17t07.ingroove.Model;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstraction of an occurrence of completing a habit
 * All habit events have an ID and implement the Identifiable interface so that the ID can be accessed
 *
 * Each habit event may contain a name, comment, day, photo (optional), and location (optional)
 *
 * Created by Adam Otto on ####-##-##.
 */

public class HabitEvent implements Serializable, Identifiable{

    // Event attributes
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
    public String getID() { return eventID; }
    public void setEventID(String id) {this.eventID = id;}
    //Location getter/setter
    public String getLocation() {
        if (location == null) {
            return "";
        } else {
            return location;
        }
    }
    public void setLocation(String location) {  this.location = location;}

    public String getHabitID() {
        return habitID;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    /**
     * Default constructor
     *
     * @param name a String representing the name
     * @param comment a String representing the comment description
     * @param day a Date representing the time of completion
     * @param photo an optional photo of the event
     * @param HabitID the habit for which the event was completed
     * @param UserID the user who completed the habit
     * @param location an optional location of completion
     */
    public HabitEvent(String name, String comment, Date day, Bitmap photo, String HabitID, String UserID, String location){
        this.name = name;
        this.comment = comment;
        this.day = day;
        this.photo = photo;
        this.habitID = HabitID;
        this.userID = UserID;
        this.location = location;
    }

    /**
     * Alternate constructor, set the attributes to default values
     */
    // TODO: WHY DO WE HAVE THIS CONSTRUCTOR? THIS SHOULD NEVER BE USED
    public HabitEvent() {
        this("","", new Date(), null, "","","");
    }

    /**
     * Alternate constructor with no location
     *
     * @param name a String representing the name
     * @param comment a String representing the comment description
     * @param day a Date representing the day
     * @param photo a Bitmap photo of the event
     * @param HabitID the habit for which the event was completed
     * @param UserID the user who completed the event
     *
     */
    public HabitEvent(String name, String comment, Date day, Bitmap photo, String HabitID, String UserID){
        this(name, comment, day, photo, HabitID, UserID,"");
    }

    /**
     * Alternate constructor with no photo
     *
     * @param name a String representing the name
     * @param comment a String representing the comment description
     * @param day a Date representing the time of completion
     * @param HabitID the habit for which the event was completed
     * @param UserID the user who completed the habit
     * @param location an optional location of completion
     */
    public HabitEvent(String name, String comment, Date day, String HabitID, String UserID, String location){
        this(name, comment, day, null, HabitID, UserID, location);
    }

    /**
     * Alternate constructor with no photo or location
     *
     * @param name a String representing the name
     * @param comment a String representing the comment description
     * @param day a Date representing the time of completion
     * @param habitID the habit for which the event was completed
     * @param userID the user who completed the habit
     */
    public HabitEvent(String name, String comment, Date day, String habitID, String userID) {
        this(name, comment, day, null, habitID, userID, "");
    }

    /**
     * Alternate constructor
     *
     * @param name a String representing the name
     * @param day a Date representing the time of completion
     * @param location an optional location of completion
     */
    // TODO: WHY DO WE HAVE THIS CONSTRUCTOR? THIS SHOUDL NEVER BE USED
    public HabitEvent(String name, Date day, String location) {
        this(name, "", day, null,"","",location);
    }

    /**
     * Alternate constructor
     *
     * @param name a String representing the name
     * @param day a Date representing the day of completion
     */
    public HabitEvent(String name, Date day) {
        this(name,"",day,null,"","","");
    }

    /**
     * Return the day of completion as a string
     *
     * @return a String representing the event completion date
     * @see SimpleDateFormat
     * @see Date
     */
    public String toString() {
        // TODO: Should localize this date format string so it can be used easily in other places
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(this.day);
        return 	this.name + ": " + dateStr;
    }

    /**
     * Overridden equals method for comparison of HabitEvents that may have been serialized--compares
     * bases on state of objects
     *
     * @param obj the object to compare
     * @return true if the events are the same, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        HabitEvent temp = (HabitEvent) obj;
        return temp.getName().equals(this.name) && temp.getDay() == this.day && temp.getID().equals(this.eventID);
    }

    /**
     * Convert the location to a LatLng representation for GoogleMaps
     * The default location is set to the University of Alberta for now, but will be updated in
     * the part 5 release to a more suitable default
     *
     * @return a LatLng representation of the HabitEvent location
     * @see LatLng
     */
    public LatLng locationToLatLng() {

        if (!location.equals("") || location != null) {
            String[] latlng = location.split(",");
            double lat = Double.parseDouble(latlng[0]);
            double lng = Double.parseDouble(latlng[1]);
            return new LatLng(lat, lng);
        } else {
            // Default location is UofA for now
            // coordinates: 53.5232,-113.5263
            return new LatLng(53.5232, -113.5263);
        }
    }
}
