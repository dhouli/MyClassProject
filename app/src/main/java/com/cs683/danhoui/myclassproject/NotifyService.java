package com.cs683.danhoui.myclassproject;



import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


/**
 * Created by danhoui on 3/24/16.
 */

//The purpose of this service is to talk to the MainActivity class and display the notification using the notificationCompatBuilder
//Referenced https://www.youtube.com/watch?v=tyVaPHv-RGo for assistance with tutorial on setting alarm manager and setting up a service.

//class is extending Service class
public class NotifyService extends Service {
    //created with the service
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


    @Override
    public void onCreate(){

        // create variable for our ringtone sounds
        //This will allow us to get the ringtone of type notification so we know what alert we are receiving
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //NotificationManager manage = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //setting intent to the Main Activity
        //using getApplication context in order to tie in to the entire application
        Intent myIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        //using pendingIntent to grant the Main Activity class the right to use the intent
        PendingIntent pendInt = PendingIntent.getActivity(this,0,myIntent,0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
        //setting all the content for the alert
        // based on android documentation setSmall, setContentTitle, setContentText min req.
        .setContentTitle("Bed Time!")
        .setContentText("Time for bed!")
        .setSmallIcon(R.mipmap.ic_launcher)//for some reason on mac you have to do it this way
        .setSound(alert)
        .setContentIntent(pendInt);
        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(0,notification);




    }


}
