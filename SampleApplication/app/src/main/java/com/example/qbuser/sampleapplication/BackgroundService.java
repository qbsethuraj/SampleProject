package com.example.qbuser.sampleapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qbuser on 6/4/16.
 */
public class BackgroundService extends Service
{

    private String TAG="sampleapp";

    private static Timer timer = new Timer();
    public Boolean userAuth = false;
    private Context ctx;
    public String pActivity="";

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    //Override function will be called when service is started
    @Override
    public int onStartCommand(Intent intent,int flags, int startId)
    {

        super.onStartCommand(intent, flags, startId);


        Log.d(TAG, "Background Service started");

        startService();


        //Start a sticky service
        return START_STICKY;
    }

    //Override function will be called when service is stopped
    @Override
    public void onDestroy()
    {

        super.onDestroy();
    }


    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 500);

        Log.d(TAG,"call to start maintask recieved");
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            Log.d(TAG,"Task is running");
        }
    }





}
