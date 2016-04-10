package com.cs683.danhoui.myclassproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


//The purpose of this class is to display a timer that can be set for the bedtime alarm
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //set up timer
        //find by view the time picker so we can link it to the program
        final TimePicker picker = (TimePicker) findViewById(R.id.picker);
        //make the button do something when selected
        final Button set = (Button) findViewById(R.id.set);




        //when the button is clicked this method will add the time to the calendar and use alarm manager to set the alarm
        //precondition: the time needs to be selected in the time picker
        //post condition: the alarm is set, the splash screen will be complete
        // the user will also get a notification that there is an alert.
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting alarm to run
                //Need to create a notification and attach it to the service
                Intent theAlarm = new Intent(MainActivity.this, NotifyService.class);
                AlarmManager myAlarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                //to set up the alert for everyday
                PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, theAlarm, 0);



                //setting variables for picker
                //the getCurrentHour and getCurrentMinute are obsolete with latest version
                //they do work with api 22 which we are running
                int hour = picker.getCurrentHour();
                int minute = picker.getCurrentMinute();

                //Using java to get int year, month and day and assigning them to variables
                long currTime = Calendar.getInstance().getTimeInMillis();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                //using calendar to actually store and set the alarm
                Calendar cal = Calendar.getInstance();



                //we plug the picker selection into the cal set in order to pass to the alarm manager
                // I used the current day, month and year
                //The time is from the picker
                cal.set(year,month,day,hour,minute);
                cal.set(Calendar.SECOND,0);
                long milli = cal.getTimeInMillis();
                Date timer = cal.getTime();

                //Now we pass the information to the alarm manager
                //Ideally with INTERVAL_DAY we have it running everyday
                //RTC_WAKE will wake the phone at the time of the alarm
                //and we are using the pending intent which will talk to the notify service
                myAlarm.setRepeating(AlarmManager.RTC_WAKEUP,milli,AlarmManager.INTERVAL_DAY, pendingIntent);


                //Using toast to display the alarm
                Toast.makeText(MainActivity.this,"Alarm Set" + " " + timer,Toast.LENGTH_LONG).show();


                //once timer is set will hold app in splash screen until the set time
                setContentView(R.layout.activity_time_for_bed);

                //set variable to calc the difference between now and set time in milli
                long myTimer = milli - currTime;

                //create handler to start next activity
                Handler myHandler = new Handler();
                //using postDelayed to allow it to be run using the specified time
                myHandler.postDelayed(new Runnable() {
                    public void run() {
                        //We run to start next activity
                        //send us to the Main Activity class
                        Intent myIntent = new Intent(MainActivity.this, GoodNight.class);
                        //move us to the MainActivity class
                        MainActivity.this.startActivity(myIntent);
                        MainActivity.this.finish();
                    }
                }, myTimer);//our timer variable is here





            }
        });

    }


}
