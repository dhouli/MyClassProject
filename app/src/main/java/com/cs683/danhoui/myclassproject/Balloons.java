package com.cs683.danhoui.myclassproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//This class will act as a splash screen to display celebration balloons that the user has reached the required 30 stars
//image used from google, orginal source myugavetschoolexperience.wordpress.com
//Precondition: The user has gained 30 stars
//Postcondition: The user will be brought to the reward class

public class Balloons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balloons);


        //set timer in milli to display the splash screen = 3 seconds
        int splashTimer = 10000;

        //create handler to start next activity
        Handler myHandler = new Handler();
        //using postDelayed to allow it to be run using the 3 seconds timer
        myHandler.postDelayed(new Runnable() {
            public void run() {
                //We run to start next activity
                //send us to the Main Activity class
                Intent myIntent = new Intent(Balloons.this, RewardWon.class);
                //move us to the MainActivity class
                Balloons.this.startActivity(myIntent);
                Balloons.this.finish();
            }
        }, splashTimer);//our timer variable is here
    }
}
