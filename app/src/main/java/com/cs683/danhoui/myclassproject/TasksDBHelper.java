package com.cs683.danhoui.myclassproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danhoui on 4/10/16.
 * The class is used to create a table for dynamic storage of the tasks list
 * It is responsible for storing the tasks that the user gathers from completing the nightly activity.
 * precondition:the DB needs to not yet be created
 * postcondition: the DB will be created and the first entry will be stored.
 */
public class TasksDBHelper extends SQLiteOpenHelper{
    //define our DB
    private static final String DBNAME = "Tasks.db";
    private static final String PRIMARY_KEY_NAME = "id";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "MyTasks";
    public static final String FIELD_REWARD = "task";
    public static final String FIELD_REWARD_1 = "task1";
    public static final String FIELD_REWARD_2 = "task2";
    private static final String TABLE_SPECIFICATIONS =
            // form: "CREATE TABLE grades(id INTEGER PRIMARY KEY, grade1 INTEGER)"
            "CREATE TABLE " + TABLE_NAME + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " + FIELD_REWARD + " String, " + FIELD_REWARD_1 + " String, " + FIELD_REWARD_2 + " String)";


    public TasksDBHelper(Context context) {
        // A database exists, named DATABASE_NAME, with TABLE_SPECIFICATIONS
        super(context, DBNAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SPECIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
