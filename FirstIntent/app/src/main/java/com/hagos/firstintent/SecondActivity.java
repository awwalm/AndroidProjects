package com.hagos.firstintent;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import com.google.android.material.appbar.AppBarLayout;
//import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button secondButton = (Button) findViewById(R.id.button2);
        assert secondButton != null;
        secondButton.setOnClickListener(secondButtonListener);

        /* // ALTERNATIVE SYNTAX TO THE ABOVE
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        */
    } /*end onCreate*/


    // inner class event listener separately written for the button
    private View.OnClickListener secondButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // effectively destroys an activity hence removing it from memory
            finish();
        }
    };
}
