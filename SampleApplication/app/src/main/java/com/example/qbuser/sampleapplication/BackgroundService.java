package com.example.qbuser.sampleapplication;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qbuser on 6/4/16.
 */
public class BackgroundService extends Service
{

    private String TAG="sampleapp";

    private static Timer timer = new Timer();
    public String pActivity="";
    public Handler mIncomingHandler;

    public boolean IsAuthenticated=false;

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

    //Function to start the updating task
    private void startService()
    {
        mIncomingHandler = new Handler(new IncomingHandlerCallback());

        //Call the updating task every 500ms
        timer.scheduleAtFixedRate(new UpdatingTask(), 0, 500);
        //Log here
        Log.d(TAG,"call to start UpdatingTask received");
    }



    //The updating task class
    private class UpdatingTask extends TimerTask
    {
        public void run()
        {
            Log.d(TAG, "Task is running");

            //Make call to message handler
            mIncomingHandler.sendEmptyMessage(0);
        }
    }

    private class IncomingHandlerCallback implements Handler.Callback
    {
        @Override
        public boolean handleMessage(Message message)
        {
            // Handle message code
            Log.d(TAG,"Message handled here");

            String activityOnTop;

            Log.d(TAG,"My function is getting called");

            //Get an instance of activity manager
            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            //Get a list of running tasks
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);

            ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
            activityOnTop=ar.topActivity.getClassName();

            Log.d(TAG,"Activity on top - "+activityOnTop );

            if(activityOnTop.equals("com.whatsapp.HomeActivity"))
            {
                pActivity = activityOnTop.toString();

                //If the user is not authenticated
                if(!IsAuthenticated)
                {
                    //Launch the pass code activity
                    Intent i = new Intent(BackgroundService.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }
//            else
//            {
//                if (activityOnTop.equals(pActivity) || activityOnTop.equals("com.whatsapp.HomeActivity"))
//                {
//
//                }
//                else
//                {
//                    Intent i = new Intent(BackgroundService.this, MainActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//                    Toast.makeText(BackgroundService.this, pActivity,Toast.LENGTH_SHORT).show();
//                    pActivity = activityOnTop.toString();
//
//                }
//            }

            return true;
        }
    }

}
