package com.cs683.danhoui.myclassproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class setTasks extends AppCompatActivity {

    //toast("Helper Created");
    //adding this method from the notes to easily use toast
    private void toast(String aToast){
        Toast.makeText(getApplicationContext(), aToast, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tasks);





    }
    public void showNext(View v){

        //create our database for the tasks
        TasksDBHelper tasksDBHelper = new TasksDBHelper(getApplicationContext());
        //Set DB repository in write mode
        SQLiteDatabase db = tasksDBHelper.getWritableDatabase();
        toast("SQLiteDatabase created tasks");


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
        toast("values created!");
        db.insert(TasksDBHelper.TABLE_NAME, null, tasks);

        //use intent to move us to next screen
        // Take us to enter the time
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);

    }


}
