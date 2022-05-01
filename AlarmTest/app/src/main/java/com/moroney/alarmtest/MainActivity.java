package com.moroney.alarmtest;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.moroney.alarmtest.R;

import java.util.Calendar;

public class MainActivity extends Activity {
    private static final String TAG ="MainActivity";
    PendingIntent myPendingIntent;
    AlarmManager alarmManager;
    BroadcastReceiver myBroadcastReceiver;
    Calendar firingCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Register AlarmManager Broadcast receive.
        firingCal= Calendar.getInstance();
        firingCal.set(Calendar.HOUR, 8); // At the hour you want to fire the alarm
        firingCal.set(Calendar.MINUTE, 0); // alarm minute
        firingCal.set(Calendar.SECOND, 0); // and alarm second
        long intendedTime = firingCal.getTimeInMillis();

        registerMyAlarmBroadcast();
        alarmManager.set( AlarmManager.RTC_WAKEUP, intendedTime , AlarmManager.INTERVAL_DAY , myPendingIntent );
    }

    private void registerMyAlarmBroadcast()
    {
        Log.i(TAG, "Going to register Intent.RegisterAlramBroadcast");

        //This is the call back function(BroadcastReceiver) which will be call when your
        //alarm time will reached.
        myBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Log.i(TAG,"BroadcastReceiver::OnReceive()");
                Toast.makeText(context, "Your Alarm is there", Toast.LENGTH_LONG).show();
            }
        };

        registerReceiver(myBroadcastReceiver, new IntentFilter("com.alarm.example") );
        myPendingIntent = PendingIntent.getBroadcast( this, 0, new Intent("com.alarm.example"),0 );
        alarmManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    }
    private void UnregisterAlarmBroadcast()
    {
        alarmManager.cancel(myPendingIntent);
        getBaseContext().unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myBroadcastReceiver);
        super.onDestroy();
    }
}