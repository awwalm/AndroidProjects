package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a button object to be programmatically referenced
        Button objButton = (Button) findViewById(R.id.button);

        // create a listener for the button object
        objButton.setOnClickListener(new View.OnClickListener()
        {
            // we override the View.OnClickListener interface onClick method
            @Override
            public void onClick(View view)
            {
                System.out.println("Hello");
            }
        });
    }

}
