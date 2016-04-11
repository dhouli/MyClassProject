package com.cs683.danhoui.myclassproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//This class allows the user to dynamically add the three tasks that need to be completed prior to bedtime
//Precondition: The reward has been set by the user
//PostCondition: The tasks will be set by the user, written into the DB, and read into the checklist.
public class setTasks extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tasks);

    }



    //This method will be responsible for creating and writing the tasks to the database
    public void showNext(View v){

        //create our database for the tasks
        TasksDBHelper tasksDBHelper = new TasksDBHelper(getApplicationContext());
        //Set DB repository in write mode
        SQLiteDatabase db = tasksDBHelper.getWritableDatabase();



        //capture the text that is entered for tasks 1 to 3.
        EditText task1   = (EditText)findViewById(R.id.task1);
        EditText task2   = (EditText)findViewById(R.id.task2);
        EditText task3   = (EditText)findViewById(R.id.task3);

        //Convert the tasks to a string to write to the DB
        //need to convert editText to a string
        String myTask1 = task1.getText().toString();
        String myTask2 = task2.getText().toString();
        String myTask3 = task3.getText().toString();





        //Adding the tasks to the DB to be set and read later
        ContentValues tasks = new ContentValues();
        tasks.put(TasksDBHelper.FIELD_REWARD, myTask1);
        tasks.put(TasksDBHelper.FIELD_REWARD_1, myTask2);
        tasks.put(TasksDBHelper.FIELD_REWARD_2, myTask3);
        db.insert(TasksDBHelper.TABLE_NAME, null, tasks);

        //use intent to move us to next screen
        // Take us to enter the time
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);

    }


}
