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
    private String photo;
    private String eventID;
    private String habitID;
    private String userID;
    private String location;

    /**
     * Access to the event name
     *
     * @return a String representing the name
     */
    public String getName() { return name; }

    /**
     * Update the name
     *
     * @param name a String representing the new name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Access to the comment description of an event
     *
     * @return a String representing the comment description
     */
    public String getComment() { return comment; }

    /**
     * Update the comment description
     *
     * @param comment a String representing the new comment description
     */
    public void setComment(String comment) {this.comment = comment;}

    /**
     * Access to the date of completion
     *
     * @return a Date representing the day of completion
     */
    public Date getDay() { return day; }

    /**
     * Update the date of completion
     *
     * @param day a Date representing the new completion date
     */
    public void setDay(Date day) {this.day = day;}

    /**
     * Return the photo of the event
     *
     * @return a Bitmap object of the photo
     * @see Bitmap
     */
    public Bitmap getPhoto() { return new BitMapHelper().stringToBitMap(photo); }

    /**
     * Update the photo of the event
     *
     * @param photo a Bitmap of the new photo
     * @see Bitmap
     */
    public void setPhoto(Bitmap photo) {this.photo = new BitMapHelper().bitMapToString(photo);}

    /**
     * Access to the eventID, required method to be Identifiable
     *
     * @return a String representing the eventID
     * @see Identifiable
     */
    public String getID() { return eventID; }

    /**
     * Update the event ID
     *
     * @param id a String representing the new ID
     */
    public void setEventID(String id) {this.eventID = id;}

    /**
     * Access to the location as a string
     *
     * @return a String representing the location of the event completion
     */
    public String getLocation() {
        if (location == null) {
            return "";
        } else {
            return location;
        }
    }

    /**
     * Access to the user who completed the event
     *
     * @return a String representing the user's ID
     * @see User
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Update the user who completed the event ID
     *
     * @param userID a String representing the user ID
     * @see User
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Update the location of the event
     *
     * @param location a String representing the new location
     */
    public void setLocation(String location) {  this.location = location;}

    /**
     * Access to the habit for which the even was logged
     *
     * @return a String representing the Habit ID
     * @see Habit
     */
    public String getHabitID() {
        return habitID;
    }

    /**
     * Modify the habit for which the event was logged
     *
     * @param habitID a String representing the habit ID
     * @see Habit
     */
    // TODO: THIS METHOD SHOULD NEVER BE USED, ONCE AN EVENT IS LOGGED IT SHOULD NOT BE CHANGED
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
        this.habitID = HabitID;
        this.userID = UserID;
        this.location = location;
        if (photo == null ) {
            this.photo = "";
        } else {
            this.photo = new BitMapHelper().bitMapToString(photo);
        }
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
        return temp.getName().equals(this.name) && temp.getDay().compareTo(this.day) == 0 && temp.getID().equals(this.eventID);
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
