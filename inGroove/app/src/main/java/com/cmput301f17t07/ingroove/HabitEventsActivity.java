package com.cmput301f17t07.ingroove;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

/**
 * This class is used to add new habit events to habits
 */
public class HabitEventsActivity extends AppCompatActivity {
    public static String habitevent_key = "habitevent_to_edit";
    public static String habit_key = "habit_to_edit";
    final int photoSize = 65536;

    // Location Variables
    private static final String TAG = HabitEventsActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    HabitEvent passed_habitEvent;
    Habit passed_habit;

    DataManagerAPI ServerCommunicator = DataManager.getInstance();
    private Location loc = null;

    //Interface variables.
    Button b_addImageButton;
    Button b_Cancel;
    Button b_Save;
    ImageView imageBlock;
    TextView commentBlock;
    TextView nameBlock;
    Switch locationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_events);

        // Location setup
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        passed_habitEvent = ServerCommunicator.getPassedHabitEvent();
        passed_habit = ServerCommunicator.getPassedHabit();

        //Initialize All the elements of this activity
        imageBlock = (ImageView) findViewById(R.id.eventImage);
        b_addImageButton = (Button) findViewById(R.id.uploadPictureButton);
        b_Cancel = (Button) findViewById(R.id.CancelButton);
        b_Save = (Button) findViewById(R.id.SaveButton);
        commentBlock = (EditText) findViewById(R.id.commentText);
        nameBlock = (EditText) findViewById(R.id.nameTextBox);
        locationSwitch = (Switch) findViewById(R.id.add_he_location_switch);

        locationSwitch.setChecked(false);

        if(passed_habitEvent != null)
        {
            nameBlock.setText(passed_habitEvent.getName());
            imageBlock.setImageBitmap(passed_habitEvent.getPhoto());
            commentBlock.setText(passed_habitEvent.getComment());
        }
        else
        {
            imageBlock.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        }
        //This creates an activity which will allow the user to pick an image.
        b_addImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 0);
            }
        });

        //Button listeners.
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

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (!checkPermissions()) {
                        requestPermissions();
                        locationSwitch.setChecked(false);
                    } else {
                        getLastLocation();
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
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
        if (mLastLocation != null && locationSwitch.isEnabled()) {
            LatLng ll = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            he.setLocation(ll);
        }
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

        return image;
    }

    // THE FOLLOWING METHODS ARE TAKEN FROM GOOGLE DOCUMENTATION AND ONLY SLIGHTLY MODIFIED
    // @TODO credit? Available under apache license I believe

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar("Unable to determine location. Please try again later");
                        }
                    }
                });
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.habitEventsActivityContainer);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(HabitEventsActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
}




