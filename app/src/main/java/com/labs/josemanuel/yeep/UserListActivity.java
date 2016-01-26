package com.labs.josemanuel.yeep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ListFragment;

/**
 * Created by javier on 26/01/16.
 */
public class UserListActivity extends AppCompatActivity {

    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
    }


}
