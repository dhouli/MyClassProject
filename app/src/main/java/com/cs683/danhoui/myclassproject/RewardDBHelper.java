package com.cs683.danhoui.myclassproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by danhoui on 4/2/16.
 * This will be used to store the information of the users expected reward
 * The format is similar to that of the notes
 */
public class RewardDBHelper extends SQLiteOpenHelper {

    //define our DB
    private static final String DBNAME = "Reward.db";
    private static final String PRIMARY_KEY_NAME = "id";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "MyReward";
    public static final String FIELD_REWARD = "reward";
    private static final String TABLE_SPECIFICATIONS =
            // form: "CREATE TABLE grades(id INTEGER PRIMARY KEY, grade1 INTEGER)"
            "CREATE TABLE " + TABLE_NAME + "(" +
                    PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY, " + FIELD_REWARD + " INTEGER)";


    public RewardDBHelper(Context context) {
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
