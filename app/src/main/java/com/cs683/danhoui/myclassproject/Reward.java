package com.cs683.danhoui.myclassproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


//The purpose of this class is to add a reward for the child to receive after they get the 30 stars
//****This class is still needs work


public class Reward extends AppCompatActivity {

    //toast("Helper Created");
    //adding this method from the notes to easily use toast
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);



    }
    //Precondition: the Reward database must contain a value which is the reward
    //Postcondition: The reward is read from the DB and displayed
    public void showNext(View v){

        //create our database
        RewardDBHelper rewardDBHelper = new RewardDBHelper(getApplicationContext());
        //Set DB repository in write mode
        SQLiteDatabase db = rewardDBHelper.getWritableDatabase();
        toast("SQLiteDatabase created");



        //capture the next that is entered
        EditText theReward   = (EditText)findViewById(R.id.reward);

        //need to convert editText to a string
        String reward = theReward.getText().toString();

        //adding to db
        ContentValues values = new ContentValues();
        values.put(RewardDBHelper.FIELD_REWARD, reward);
        toast("values created!");

        // values inserted in row; insertion = primary key value of the new row
        long insertion = db.insert(RewardDBHelper.TABLE_NAME, null, values);
        toast("values Inserted; 'insertion=" + insertion);



        // Take us to GoodNight class after the button is clicked
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);



    }

}
