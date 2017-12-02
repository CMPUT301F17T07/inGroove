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
    private String objectID;
    private String habitID;
    private String userID;
    private String location;

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
    public HabitEvent(String name, String comment, Date day, Bitmap photo, String HabitID, String UserID, LatLng location){
        this.name = name;
        this.comment = comment;
        this.day = day;
        this.habitID = HabitID;
        this.userID = UserID;
        this.location = latLngToString(location);
        if (photo == null ) {
            this.photo = null;
        } else {
            this.photo = new BitMapHelper().bitMapToString(photo);
        }
    }

    /**
     * Alternate constructor, set the attributes to default values
     */
    // TODO: WHY DO WE HAVE THIS CONSTRUCTOR? THIS SHOULD NEVER BE USED
    public HabitEvent() {
        this("","", new Date(), null, "","", null);
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
        this(name, comment, day, photo, HabitID, UserID, null);
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
    public HabitEvent(String name, String comment, Date day, String HabitID, String UserID, LatLng location){
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
        this(name, comment, day, null, habitID, userID, null);
    }

    /**
     * Alternate constructor
     *
     * @param name a String representing the name
     * @param day a Date representing the time of completion
     * @param location an optional location of completion
     */
    // TODO: WHY DO WE HAVE THIS CONSTRUCTOR? THIS SHOUDL NEVER BE USED
    public HabitEvent(String name, Date day, LatLng location) {
        this(name, "", day, null,"","",location);
    }

    /**
     * Alternate constructor
     *
     * @param name a String representing the name
     * @param day a Date representing the day of completion
     */
    public HabitEvent(String name, Date day) {
        this(name,"",day,null,"","",null);
    }

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
    public Bitmap getPhoto() {
        if (photo != null) {
            return new BitMapHelper().stringToBitMap(photo);
        } else {
            return null;
        }
    }

    /**
     * Update the photo of the event
     *
     * @param photo a Bitmap of the new photo
     * @see Bitmap
     */
    public void setPhoto(Bitmap photo) {this.photo = new BitMapHelper().bitMapToString(photo);}

    /**
     * Access to the objectID, required method to be Identifiable
     *
     * @return a String representing the objectID
     * @see Identifiable
     */
    public String getObjectID() { return objectID; }

    /**
     * Used to set the object id
     * Must be Unique across all devices running this app
     *
     * ie set to userId + an id unique to this device
     * @param uniqueObjectId an id that uniquely identifies this object
     *
     */
    public void setObjectID(String uniqueObjectId) {
        this.objectID = uniqueObjectId;
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
     * Access to the location as a string
     *
     * @return a String representing the location of the event completion
     */
    public LatLng getLocation() {
        return stringToLatLng(location);
    }

    /**
     * Update the location of the event
     *
     * @param location a String representing the new location
     */
    public void setLocation(LatLng location) {
        this.location = latLngToString(location);
    }

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
    public void setHabitID(String habitID) {
        this.habitID = habitID;
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
        return temp.getName().equals(this.name) && temp.getDay().compareTo(this.day) == 0 && temp.getObjectID().equals(this.objectID);
    }


    /**
     * Convert a string to a LatLng
     * @param locationString the string to convert to LatLng in form "lat,lng"
     * @return the LatLng object
     * @see LatLng
     */
    private LatLng stringToLatLng(String locationString) {

        if (locationString != null && !locationString.equals("")) {
            String[] latlngString = locationString.split(",");
            double lat = Double.parseDouble(latlngString[0]);
            double lng = Double.parseDouble(latlngString[1]);
            return new LatLng(lat,lng);
        } else {
            return null;
        }

    }

    /**
     * Converts a LatLng object to a string for easier storage on elasticsearch
     * @param locationLatLng the LatLng object to be converted
     * @return the String in form "lat,lng"
     * @see LatLng
     */
    private String latLngToString(LatLng locationLatLng) {

        if (locationLatLng != null) {
            double lat = locationLatLng.latitude;
            double lng = locationLatLng.longitude;
            String locationString = String.valueOf(lat) + "," + String.valueOf(lng);
            return locationString;
        } else {
            return null;
        }
    }
}
