package com.hagos.async;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private  String TAG;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        tv = (TextView) findViewById(R.id.textView);
        TAG = getClass().getSimpleName();

        b.setOnClickListener(this);
        b2.setOnClickListener(buttonTwoListener);

    }


    public void onClick(View v)
    {
        Toast.makeText(MainActivity.this, "long running task started", Toast.LENGTH_SHORT).show();
        Worker worker = new Worker();
        worker.execute(tv);
        /*
        int i = 0;
        while (i < 15)
        {
            try
            {

                Thread.sleep(2000);
                tv.setText(String.format("Value of i = %d", 1));
                Log.i(TAG, String.format("value of i = %d", i++));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
         */
    }


    private View.OnClickListener buttonTwoListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Log.i(TAG, "Clicked");
            Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
        }
    };

}
