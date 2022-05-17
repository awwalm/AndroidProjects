package com.codinginflow.looperthreadexample;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class ExampleHandler extends Handler {

    public static final String TAG = "ExampleHandler";
    public static final int TASK_A = 1;
    public static final int TASK_B = 2;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what)
        {
            case TASK_A:
                Log.d(TAG, "Task A executed");
                break;

            case TASK_B:
                Log.d(TAG, "Task B executed");
                break;
        }
    }
}
