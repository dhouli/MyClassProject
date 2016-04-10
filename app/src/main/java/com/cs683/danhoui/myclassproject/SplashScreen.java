package com.cs683.danhoui.myclassproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


// The purpose of this class is to display a splash screen
//Currently there is text however the goal will be to add an image to the display
//reference http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen for assistance to create splash screen

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //our view is the activity splash screen layout
        setContentView(R.layout.activity_splash_screen);

        //set timer in milli to display the splash screen = 3 seconds
        int splashTimer = 3000;

        //create handler to start next activity
        Handler myHandler = new Handler();
        //using postDelayed to allow it to be run using the 3 seconds timer
        myHandler.postDelayed(new Runnable() {
            public void run() {
                //We run to start next activity
                //send us to the Main Activity class
                Intent myIntent = new Intent(SplashScreen.this, Reward.class);
                //move us to the MainActivity class
                SplashScreen.this.startActivity(myIntent);
                SplashScreen.this.finish();
            }
        }, splashTimer);//our timer variable is here


    }

}
