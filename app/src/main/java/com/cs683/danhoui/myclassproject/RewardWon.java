package com.cs683.danhoui.myclassproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

//The purpose of this class will be to display to the user a summary of the reward they have selected
//and to congratulate them on gaining the 30 stars
//In addition it tests network connectivity which will hopefully be used in a future implementation to store the information to the cloud
// Pre-condition: The user must have achieved the 30 stars
//Post Condition: The stars will be reset(by deleting the DB) and the user will be returned to the reward screen
public class RewardWon extends AppCompatActivity {


    //Set our variables for connectivityManager and Networkinfo which will be used further in the program to test our network connectivity status
    ConnectivityManager ConnectManager;
    NetworkInfo myInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_won);

        //set timer in milli to display the splash screen = 3 seconds
        int splashTimer = 10000;

        //create handler to start next activity
        Handler myHandler = new Handler();
        //using postDelayed to allow it to be run using the 3 seconds timer
        myHandler.postDelayed(new Runnable() {
            public void run() {
                //We run to start next activity
                //send us to the Main Activity class
                Intent myIntent = new Intent(RewardWon.this, Reward.class);
                //move us to the MainActivity class
                RewardWon.this.startActivity(myIntent);
                RewardWon.this.finish();
            }
        }, splashTimer);//our timer variable is here



        //create our database object to read from for reward
        RewardDBHelper rewardDBHelper = new RewardDBHelper(getApplicationContext());
        // dBase is access for reading
        SQLiteDatabase myDB = rewardDBHelper.getReadableDatabase();

        //use array projection to grab the column in the DB
        String[] projection = {"reward"};

        // Query c performed with projection
        Cursor c = myDB.query(
                RewardDBHelper.TABLE_NAME,     // table to query
                projection,                         // columns to return
                null,                               // columns for WHERE clause
                null,                               // values for WHERE clause
                null,                               // don't group rows
                null,                               // don't filter by row groups
                null                                // sort order
        );
        // firstGrade is grade1 in first record
        c.moveToLast();

        String myReward = c.getString(c.getColumnIndexOrThrow(RewardDBHelper.FIELD_REWARD));

        //Displaying the reward using another textView
        TextView myview;
        myview=(TextView)findViewById(R.id.myReward);
        myview.setText("Your reward is a " + myReward);


        //confirm the user has network access to prepare for future release
        ConnectManager =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        myInfo = ConnectManager.getActiveNetworkInfo();
        boolean isConnected = myInfo != null && myInfo.isConnectedOrConnecting();
        //use text view to display the connectivity information to the user
        TextView connectView;
        connectView=(TextView)findViewById(R.id.connectivity);
        connectView.setText("Are we connected to network: " + isConnected);

        //create our database
        GoodNightDBHelper goodNightDBHelper = new GoodNightDBHelper(getApplicationContext());

        //Set DB repository in write mode
        //Source http://stackoverflow.com/questions/26764887/how-to-drop-android-sqlite-database-programmatically
        SQLiteDatabase db = goodNightDBHelper.getWritableDatabase();
        db.delete("GoodNight",null,null);


    }

}
