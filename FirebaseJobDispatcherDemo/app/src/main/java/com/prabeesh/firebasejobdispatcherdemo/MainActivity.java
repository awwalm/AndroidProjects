package com.prabeesh.firebasejobdispatcherdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity
{

   private final String JOB_TAG = getClass().getName();
   private FirebaseJobDispatcher jobDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        System.out.println(JOB_TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

    }

    public void startJob(View view)
    {
        Job job = jobDispatcher.newJobBuilder()
                .setService(MyService.class)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTag(JOB_TAG)
                .setTrigger(Trigger.executionWindow(10, 15))
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setReplaceCurrent(false)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
        jobDispatcher.mustSchedule(job);
        Toast.makeText(this, "JOB SCHEDULED", Toast.LENGTH_SHORT).show();
    }

    public void stopJob(View view)
    {
        jobDispatcher.cancel(JOB_TAG);
        Toast.makeText(this, "JOB CANCELLED", Toast.LENGTH_SHORT).show();
    }


}
