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

    //Function to start the updating task
    private void startService()
    {
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
            Log.d(TAG,"Task is running");
            //toastHandler.sendEmptyMessage(0);
        }
    }


    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            String activityOnTop;

            Log.d(TAG,"My function is getting called");

            //Get an instance of activity manager
            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            //Get a list of running tasks
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);

            ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
            activityOnTop=ar.topActivity.getClassName();

            if(activityOnTop.equals("com.whatsapp.Conversation"))
            {
                pActivity = activityOnTop.toString();
            }
            else {
                if (activityOnTop.equals(pActivity) || activityOnTop.equals("com.whatsapp.Conversation")) {

                } else {
                    Intent i = new Intent(BackgroundService.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    Toast.makeText(BackgroundService.this, pActivity, 1).show();
                    pActivity = activityOnTop.toString();

                }
            }
        }


    };

}
