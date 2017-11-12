package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

/**
 * Created by Ashley on 2017-11-12.
 */

public class AddEditUserActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    public static String user_key = "user_key";
    User passedUser = null;

    EditText userName;
    EditText userEmail;

    Button saveUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_edit);


    }

}
