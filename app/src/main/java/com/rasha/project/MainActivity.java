package com.rasha.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    public void redirectActivity() {

        if (ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")){

            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);
        }
    }

    public void getStarted(View view){

        Switch userSwitch = (Switch) findViewById(R.id.userSwitch);


        Log.i("Switch Value", String.valueOf((userSwitch.isChecked())));

        String userType = "rider";
        if (userSwitch.isChecked()){

            userType = "driver";


        }


        ParseUser.getCurrentUser().put("riderOrDriver", userType);

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                redirectActivity();
            }
        });

        Log.i("info","Redirect as" + userType);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if(ParseUser.getCurrentUser()== null) {

            ParseAnonymousUtils.logIn((new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if(e == null){

                        Log.i("Info", "Anonymous user successfull");

                    } else {
                        Log.i("Info", "Anonymous user failed");

                    }
                }
            }));


        } else {

            if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {


                Log.i("info", "Redirecting as" + ParseUser.getCurrentUser().get("riderOrDriver"));

                redirectActivity();
            }


        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        }
    }

