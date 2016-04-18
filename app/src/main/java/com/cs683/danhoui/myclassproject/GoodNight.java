package com.cs683.danhoui.myclassproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



//The purpose of this class is to allow the user to select the completed activity for the night
// Write the star to another array to hold the value
// either 1 or 0 or .5 will be written
// 1 will be written if all 3 activities are complete
// .5 will be written if 2 of the 3 activities are complete
// 0 will be written if 1 or 0 of the activities are completed
// Once the total equals 30 the goal is achieved

public class GoodNight extends AppCompatActivity {

    //toast("Helper Created");
    //adding this method from the notes to easily use toast
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }


    //Adding Log tagging
    private static final String TAG = "stateChange";

    // If all three tasks are selected then the child receives a star
    // create an array to get the selections
    ArrayList<String> completedTask = new ArrayList<String>();
    //initialize the star
    public double star;

    TextView final_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_night);






        //setting the variable to display the result of stars
        final_text = (TextView)findViewById(R.id.final_result);
        //setting it to false to appear after the amount has been created
        final_text.setEnabled(false);

        //adding log tagging
        Log.i(TAG, "onCreate");


        //create our database for the tasks
        TasksDBHelper tasksDBHelper = new TasksDBHelper(getApplicationContext());


        //Reading from the DB to get tge activities, then going to write it to the fields
        //TasksDBHelper tasksDBHelper = new TasksDBHelper(getApplicationContext());
        // dBase is access for reading
        SQLiteDatabase dBase = tasksDBHelper.getReadableDatabase();
        toast("SQLiteDatabase accessed");

        //use array projection to grab the column in the DB
        String[] projection = {"task","task1","task2"};

        // Query c performed with projection
        Cursor c = dBase.query(
                TasksDBHelper.TABLE_NAME,     // table to query
                projection,                         // columns to return
                null,                               // columns for WHERE clause
                null,                               // values for WHERE clause
                null,                               // don't group rows
                null,                               // don't filter by row groups
                null                                // sort order
        );

        c.moveToFirst();

        //Using this information to display in the textView to present to the user
        String myReward = c.getString(c.getColumnIndexOrThrow(TasksDBHelper.FIELD_REWARD));
        String myReward1 = c.getString(c.getColumnIndexOrThrow(TasksDBHelper.FIELD_REWARD_1));
        String myReward2 = c.getString(c.getColumnIndexOrThrow(TasksDBHelper.FIELD_REWARD_2));
        toast(myReward + myReward1 + myReward2);

        //write to the field
        //Displaying the reward using textView
        TextView myview;
        myview=(TextView)findViewById(R.id.field1);
        myview.setText(myReward);

        myview=(TextView)findViewById(R.id.field2);
        myview.setText(myReward1);

        myview=(TextView)findViewById(R.id.field3);
        myview.setText(myReward2);


    }




    //The three check boxes need to either be selected or not then submitted
    //Need to create two methods, 1. Keep track of checkbox 2. keep track of submission
    //Need to declare the selected methods
    public void selectItem(View v){
        //Boolean to confirm it is checked
        boolean selected =((CheckBox) v).isChecked();





        //Need to find which checkbox is selected
        //use switch to find this
        //reference https://www.youtube.com/watch?v=NGRV2qY9ZiU for hot to use the case statements with checkboxes
        switch(v.getId()){
            //locating the id for brush_teeth
            case R.id.brush_teeth:
                if(selected){
                    completedTask.add("teeth_brushed");
                }
                else{
                    completedTask.remove("teeth_brushed");
                }
                break;
            case R.id.pajamas:
                if(selected){
                    completedTask.add("pajamas_on");
                }
                else{
                    completedTask.remove("pajamas_on");
                }
                break;
            case R.id.story_time:
                if(selected){
                    completedTask.add("story_time");
                }
                else{
                    completedTask.remove("story_time");
                }
                break;

        }


    }
    //precondition is that the check boxes must already be selected using the SelectItem method
    // This finalSelection method totals the amount and displays it
    //post condition: the information will be stored to possibly a database
    public void finalSelection(View v){
        //create our database
        GoodNightDBHelper goodNightDBHelper = new GoodNightDBHelper(getApplicationContext());

        //Set DB repository in write mode
        SQLiteDatabase db = goodNightDBHelper.getWritableDatabase();
        toast("SQLiteDatabase created");


        String goodNight = "";
        for(String Selections : completedTask){
            //goodNight = goodNight + Selections + "\n";
            if (completedTask.size() == 3){
                star = 1;

                goodNight = "You gained " + star + " star";

                // Map of values created, where column names are the keys
                //write to an array holding the amount

                ContentValues values = new ContentValues();
                values.put(GoodNightDBHelper.FIELD_STARS, star);
                toast("values created!");

                // values inserted in row; insertion = primary key value of the new row
                long insertion = db.insert(GoodNightDBHelper.TABLE_NAME, null, values);
                toast("values Inserted; 'insertion=" + insertion);

                break;

            }else if(completedTask.size() == 2){
                star = 0.5;
                //write to an array holding the amount
                goodNight = "You gained " + star + " star";

                // Map of values created, where column names are the keys
                //write to an array holding the amount

                ContentValues values = new ContentValues();
                values.put(GoodNightDBHelper.FIELD_STARS, star);
                toast("values created!");

                // values inserted in row; insertion = primary key value of the new row
                long insertion = db.insert(GoodNightDBHelper.TABLE_NAME, null, values);
                toast("values Inserted; 'insertion=" + insertion);

                break;

            }

            else {
                star = 0;
                //write to an array holding the amount
                goodNight = "You gained " + star + " star";

                // Map of values created, where column names are the keys
                //write to an array holding the amount

                ContentValues values = new ContentValues();
                values.put(GoodNightDBHelper.FIELD_STARS, star);
                toast("values created!");

                // values inserted in row; insertion = primary key value of the new row
                long insertion = db.insert(GoodNightDBHelper.TABLE_NAME, null, values);
                toast("values Inserted; 'insertion=" + insertion);

                break;

            }

        }

        final_text.setText(goodNight);
        final_text.setEnabled(true);
        //Keep in mind the idea to write this to DB


        //DB read information
        // dBase is access for reading
        SQLiteDatabase dBase = goodNightDBHelper.getReadableDatabase();
        toast("SQLiteDatabase accessed");

        // projection specifies columns from the database
        String[] projection = {"stars"};

        // Query c performed with projection
        Cursor c = dBase.query(
                GoodNightDBHelper.TABLE_NAME,     // table to query
                projection,                         // columns to return
                null,                               // columns for WHERE clause
                null,                               // values for WHERE clause
                null,                               // don't group rows
                null,                               // don't filter by row groups
                null                                // sort order
        );
        //testing this================
        Cursor myCursor = dBase.rawQuery("SELECT SUM(stars) FROM GoodNight", null);
        if(myCursor.moveToFirst())
        {
             myCursor.getDouble(0);
            double answer = myCursor.getDouble(c.getColumnIndexOrThrow(GoodNightDBHelper.FIELD_STARS));
            toast("the answer: " + answer);
        }
        ///Testing===================
        toast("Query made");

        // firstGrade is grade1 in first record
        c.moveToFirst();
        //c.moveToNext();
        int firstGrade = c.getInt(c.getColumnIndexOrThrow(GoodNightDBHelper.FIELD_STARS));
        //int second = c.getInt(c.getColumnIndexOrThrow(GoodNightDBHelper.FIELD_STARS));
        toast("First star = " + firstGrade);
        //toast("Second star = " + second);

        dBase.close();

        // Take us to GoodNight class after the button is clicked
        Intent intent = new Intent(this, RewardDBReader.class);
        startActivity(intent);



    }



    //Log tagging
    //This was from Android Studio 6 Edition book
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }





}
