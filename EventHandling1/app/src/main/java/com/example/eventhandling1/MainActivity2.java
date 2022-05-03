package com.example.eventhandling1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.button1);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button1:
                show("Button One");
                break;
            case R.id.button2:
                show("Button Two");
                break;
            case R.id.button3:
                show("Button Three");
                break;
            default:
                show("This should not happen");
        }
    }

    public void show(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.i(getClass().getName(), message);
    }
}
