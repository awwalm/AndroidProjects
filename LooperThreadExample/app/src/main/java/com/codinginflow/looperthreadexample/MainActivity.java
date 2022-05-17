package com.codinginflow.looperthreadexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import static com.codinginflow.looperthreadexample.ExampleHandler.TASK_A;
import static com.codinginflow.looperthreadexample.ExampleHandler.TASK_B;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ExampleLooperThread looperThread = new ExampleLooperThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startThread(View view) {
        looperThread.start();
    }

    public void stopThread(View view) {
        looperThread.looper.quit(); // don't run any messages after the current one
    // looperThread.looper.quitSafely(); // don't run any messages after the dispatch barrier
    }

    public void taskA(View view) {
        Message msg = Message.obtain(); // normal message object
        msg.what = TASK_A;
        looperThread.handler.sendMessage(msg); // send a message to the message queue

    /* Handler threadHandler = new Handler(looperThread.looper);
        // send something from the ui thread to the message queue
        *//*looperThread.handler*//*threadHandler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "run: " + i);
                    SystemClock.sleep(1000);
                }
            }
        });*/
    }

    public void taskB(View view) {
        Message msg = Message.obtain(); // normal message object
        msg.what = TASK_B;
        looperThread.handler.sendMessage(msg);
    }
}
