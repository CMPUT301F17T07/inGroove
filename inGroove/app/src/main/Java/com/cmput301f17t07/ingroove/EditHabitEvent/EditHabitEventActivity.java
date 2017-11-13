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
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditHabitEventActivity extends AppCompatActivity {

    //Initilize variables.
    public static String habitevent_key = "habitevent_to_edit";
    public static String habit_key = "habit_to_edit";
    HabitEvent passed_habitEvent;
    Habit passed_habit;

    DataManagerAPI ServerCommunicator = DataManager.getInstance();

    Button b_addImageButton;
    Button b_Cancel;
    Button b_Save;
    Button b_Delete;
    ImageView imageBlock;
    TextView commentBlock;
    TextView nameBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit_event);

        Bundle bundle = this.getIntent().getExtras();
        passed_habitEvent = null;
        if (bundle != null) {
            passed_habitEvent = (HabitEvent) bundle.getSerializable(habitevent_key);
            passed_habit = (Habit) bundle.getSerializable(habit_key);
        }
        //Initialize All the elements of this activity
        imageBlock = (ImageView) findViewById(R.id.eventImage);
        b_addImageButton = (Button) findViewById(R.id.uploadPictureButton);
        b_Cancel = (Button) findViewById(R.id.CancelButton);
        b_Save = (Button) findViewById(R.id.SaveButton);
        b_Delete = (Button) findViewById(R.id.DeleteButton);
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

        b_Delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DeleteButtonClick();
            }
        });
    }

    /**
     * Overrides the back press so that it also saves changes made.
     */
    @Override
    public void onBackPressed() {
        //SaveHabitEvent();
        CancelButtonClick();
    }

    /**
     * This method is used to delete the habit event.  It tells the datamanager to remove the
     * passed in habitevent and then exits.
     */
    private void DeleteButtonClick(){
        ServerCommunicator.removeHabitEvent(passed_habitEvent);
        CancelButtonClick();
    }

    /**
     * This method is used when the save button is clicked.  It saves the habit event then exits.
     */
    private void SaveButtonClick() {
        SaveHabitEvent();
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
        ServerCommunicator.addHabitEvent(passed_habit, he);
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
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                if (selectedImage.getByteCount() < 65536 )
                    imageBlock.setImageBitmap(selectedImage);
                else
                    Toast.makeText(this, "Image is too large.", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked an image",Toast.LENGTH_LONG).show();
        }
    }
}
