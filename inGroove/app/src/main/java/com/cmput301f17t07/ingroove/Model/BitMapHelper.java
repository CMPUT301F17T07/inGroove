package com.cmput301f17t07.ingroove.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Converts a bitmap to a string and back for storage in JSON
 *
 * taken from https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
 */

public class BitMapHelper implements Serializable {

    public BitMapHelper() { }

    /**
     * Convert to bitmap
     *
     * @param encodedString string representing the bitmap image
     * @return an image
     */
    public Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    /**
     * Convert a bitmap to an encoded string
     *
     * @param bitmap the bitmap to convert
     * @return a string representing the bitmap
     */
    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}
