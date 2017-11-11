package com.cmput301f17t07.ingroove;

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

import java.io.FileNotFoundException;
import java.io.InputStream;

public class HabitEventsActivity extends AppCompatActivity {
    public static String habitevent_key = "habitevent_to_edit";
    public static String habit_key = "habit_to_edit";
    HabitEvent passed_habitEvent;
    Habit passed_habit;

    DataManagerAPI ServerCommunicator = DataManager.getInstance();

    Button b_addImageButton;
    ImageView imageBlock;
    TextView commentBlock;
    TextView nameBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_events);

        Bundle bundle = this.getIntent().getExtras();
        passed_habitEvent = null;
        if (bundle != null){
            passed_habitEvent = (HabitEvent) bundle.getSerializable(habitevent_key);
            passed_habit = (Habit) bundle.getSerializable(habit_key);
        }
        //Initialize All the elements of this activity
        imageBlock = (ImageView) findViewById(R.id.eventImage);
        b_addImageButton = (Button) findViewById(R.id.uploadPictureButton);
        commentBlock = (EditText) findViewById(R.id.commentText);
        nameBlock = (EditText) findViewById(R.id.nameTextBox);

        if(passed_habitEvent != null)
        {
            nameBlock.setText(passed_habitEvent.getName());
            imageBlock.setImageBitmap(passed_habitEvent.getPhoto());
            commentBlock.setText(passed_habitEvent.getComment());
            //todo: find out how locations in the maps are done.
        }
        //This should create an activity which will allow the user to pick an image.
        b_addImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 0);
            }
        });
    }

    //Overrides the back press so that it also saves changes made.
    @Override
    public void onBackPressed() {
        //SaveHabitEvent();
        editHabitEvent();
        finish();
        return;
    }

    private void SaveHabitEvent()
    {
        ServerCommunicator.addHabitEvent(passed_habit, GetEventInfoFromActivityElements());
    }

    private void editHabitEvent()
    {
        HabitEvent he = GetEventInfoFromActivityElements();
        he.setHabitID(passed_habitEvent.getHabitID());
        he.setEventID(passed_habitEvent.getHabitID());
        ServerCommunicator.editHabitEvent(passed_habitEvent, he);
    }

    private HabitEvent GetEventInfoFromActivityElements()
    {
        HabitEvent he = new HabitEvent();
        he.setName(nameBlock.getText().toString());
        he.setComment(commentBlock.getText().toString());
        he.setPhoto(((BitmapDrawable)imageBlock.getDrawable()).getBitmap());
        he.setHabitID(passed_habitEvent.getHabitID());
        //todo: find out how locations in the maps are done.
        //he.setLocation();
        return he;
    }

    //After an image is chosen, it is set to the imageBlock.
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageBlock.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked an image",Toast.LENGTH_LONG).show();
        }
    }

}