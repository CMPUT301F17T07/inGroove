package com.cmput301f17t07.ingroove.EditHabitEvent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * [Boundary Class]
 * Activity to allow users to edit habit events
 *
 * @see HabitEvent
 * @see DataManagerAPI
 *
 */
public class EditHabitEventActivity extends AppCompatActivity {

    //Initilize variables.
    public static String habitevent_key = "habitevent_to_edit";
    public static String habit_key = "habit_to_edit";
    final int photoSize = 65536;
    HabitEvent passed_habitEvent;

    DataManagerAPI ServerCommunicator = DataManager.getInstance();

    Button b_addImageButton;
    Button b_Cancel;
    Button b_Save;
    ImageView imageBlock;
    TextView commentBlock;
    TextView nameBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit_event);

        passed_habitEvent = ServerCommunicator.getPassedHabitEvent();

        //Initialize All the elements of this activity
        imageBlock = (ImageView) findViewById(R.id.eventImage);
        b_addImageButton = (Button) findViewById(R.id.uploadPictureButton);
        b_Cancel = (Button) findViewById(R.id.CancelButton);
        b_Save = (Button) findViewById(R.id.SaveButton);
        commentBlock = (EditText) findViewById(R.id.commentText);
        nameBlock = (EditText) findViewById(R.id.nameTextBox);

        if (passed_habitEvent != null) {
            nameBlock.setText(passed_habitEvent.getName());
            imageBlock.setImageBitmap(passed_habitEvent.getPhoto());
            commentBlock.setText(passed_habitEvent.getComment());
            //todo: find out how locations in the maps are done.
        } else {
            Toast.makeText(this, "ERROR: No HabitEvent was passed in.",Toast.LENGTH_LONG).show();
        }
        //This creates an activity which will allow the user to pick an image.
        b_addImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 0);
            }
        });

        b_Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CancelButtonClick();
            }
        });

        b_Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveButtonClick();
            }
        });
    }


    @Override
    public void onBackPressed() {
        //SaveHabitEvent();
        CancelButtonClick();
    }
    /**
     * This method is used when the save button is clicked.  It saves the habit event then exits.
     */
    private void SaveButtonClick() {
        SaveHabitEvent();
        setResult(RESULT_OK);
        CancelButtonClick();
    }

    /**
     * This method Is used to close the activity and return to the previous activity.
     * It is directly tied to the cancel button.
     */
    private void CancelButtonClick() {
        finish();
        return;
    }

    /**
     * This method saves the habitevent with updated values from the phone screen.
     */
    private void SaveHabitEvent()
    {
        HabitEvent he = GetEventInfoFromActivityElements();
        ServerCommunicator.editHabitEvent(passed_habitEvent, he);
        ServerCommunicator.setPassedHabitEvent(he);
    }

    /**
     * Creates a habit event and populates it with data from the activity elements.
     * @return: A new habit that can be used to update/create with the datamanagers.
     */
    private HabitEvent GetEventInfoFromActivityElements()
    {
        HabitEvent he = new HabitEvent();
        he.setName(nameBlock.getText().toString());
        he.setComment(commentBlock.getText().toString());
        he.setPhoto(((BitmapDrawable)imageBlock.getDrawable()).getBitmap());
        //todo: find out how locations in the maps are done.
        //he.setLocation();
        return he;
    }

    /**
     * This method is used to load an image from the phone.  It also checks to see if the image is
     * within a certain size.
     * @param reqCode: Override variable.
     * @param resultCode: Override variable.
     * @param data: Override variable.
     */
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                if (selectedImage.getByteCount() < photoSize )
                    imageBlock.setImageBitmap(selectedImage);
                else {
                    selectedImage = ResizeBitmap(selectedImage);
                    Toast.makeText(this, "Image resized", Toast.LENGTH_LONG).show();
                    imageBlock.setImageBitmap(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked an image",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method attempts to scale an image down so it fits the memory limit.
     * @param image: The image to resize.
     * @return The resized image.
     */
    public Bitmap ResizeBitmap(Bitmap image) {
        //A 126x126 image is the closest we'll get to a 2^16 byte image.
        int maxSize = 126;
        int width = image.getWidth();
        int height = image.getHeight();

        while(image.getByteCount() >= photoSize ) {
            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            image = Bitmap.createScaledBitmap(image, width, height, true);
            maxSize -= 1;
        }

        return Bitmap.createScaledBitmap(image, 125, 125, true);
    }
}
