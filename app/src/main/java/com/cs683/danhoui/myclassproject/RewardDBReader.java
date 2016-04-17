package com.cs683.danhoui.myclassproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class RewardDBReader extends AppCompatActivity {
    //toast("Helper Created");
    //adding this method from the notes to easily use toast
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }

    //the purpose of this method is to provide the user with a summary of the number of stars they have and the reward they have selected
    //this will occur each night after the user has entered the activities they have completed for the night
    //Precondition: the Reward database must contain a value which is the reward
    //Precondition: the GoodNight database must be created
    //Postcondition: The reward is read from the DB and displayed
    //Postcondition: the goodnight db is read and the stars are displayed
    //Postcondition: user is returned to the screen to enter time for tomorrows bedtime and activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_dbreader);

        //set timer in milli to display the splash screen = 3 seconds
        int splashTimer = 10000;




        //Create our database object to read stars from GoodNight
        GoodNightDBHelper goodNightDBHelper = new GoodNightDBHelper(getApplicationContext());

        // dBase is access for reading
        SQLiteDatabase dBase = goodNightDBHelper.getReadableDatabase();
        toast("SQLiteDatabase accessed");



        //Gathering the sum of stars from the DB
        Cursor myCursor = dBase.rawQuery("SELECT SUM(stars) FROM GoodNight", null);
        //sif we are at the first row then let us get the cursor and store it in answer variable
        if(myCursor.moveToFirst())
        {
            double answer = myCursor.getDouble(0);

            //Converting the double to string
            //Displaying the sum of the total stars so far by using the textview
            TextView starView;
            starView = (TextView)findViewById(R.id.viewMyStars);
            String myStarsAnswer = Double.toString(answer);
            //Setting the view
            starView.setText("You currently have " + myStarsAnswer + " stars so far. And you need 30 stars to get the reward.");


            if(answer<30){
                //create handler to start next activity
                Handler myHandler = new Handler();
                //using postDelayed to allow it to be run using the 3 seconds timer
                myHandler.postDelayed(new Runnable() {
                    public void run() {
                        //We run to start next activity
                        //send us to the Main Activity class
                        Intent myIntent = new Intent(RewardDBReader.this, RewardWon.class);
                        //move us to the MainActivity class
                        RewardDBReader.this.startActivity(myIntent);
                        RewardDBReader.this.finish();
                    }
                }, splashTimer);//our timer variable is here

            }else{
                //create handler to start next activity
                Handler myHandler = new Handler();
                //using postDelayed to allow it to be run using the 3 seconds timer
                myHandler.postDelayed(new Runnable() {
                    public void run() {
                        //We run to start next activity
                        //send us to the Main Activity class
                        Intent myIntent = new Intent(RewardDBReader.this, RewardWon.class);
                        //move us to the MainActivity class
                        RewardDBReader.this.startActivity(myIntent);
                        RewardDBReader.this.finish();
                    }
                }, splashTimer);//our timer variable is here

            }


        }



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
        c.moveToFirst();

        String myReward = c.getString(c.getColumnIndexOrThrow(RewardDBHelper.FIELD_REWARD));

        //Displaying the reward using another textView
        TextView myview;
        myview=(TextView)findViewById(R.id.viewMyReward);
        myview.setText("Your reward is a " + myReward);



    }
}
