package com.prabeesh.firebasejobdispatcherdemo;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyService extends JobService
{

    BackgroundTask backgroundTask;

    @Override
    public boolean onStartJob(@NonNull final JobParameters job)
    {
        backgroundTask =
            new BackgroundTask()
            {
                @Override
                protected void onPostExecute(String s) {
                    Toast.makeText(MyService.this, "BACKGROUND TASK MSG: "+s, Toast.LENGTH_SHORT).show();
                    //super.onPostExecute(s);
                    jobFinished(job, false);
                }
            };

        backgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return true;
    }

    public  static class BackgroundTask extends AsyncTask<Void, Void, String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            return "Hello from background job";
        }
    }

}
