package com.codinginflow.looperthreadexample;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

public class ExampleLooperThread extends Thread {

    private static final String TAG = "ExampleLooperThread";

    public Handler handler;

    public Looper looper;

    @Override
    public void run() {
        Looper.prepare(); // automatically creates a message queue

        looper = Looper.myLooper(); // returns looper of the current thread

        handler = new ExampleHandler(); /*Handler();*/

        Looper.loop(); // infinite for-loop

       /* for (int i = 0; i < 5; i++) {
            Log.d(TAG, "run: " + i);
            SystemClock.sleep(1000);
        }*/

        Log.d(TAG, "End of run()");
    }
}
