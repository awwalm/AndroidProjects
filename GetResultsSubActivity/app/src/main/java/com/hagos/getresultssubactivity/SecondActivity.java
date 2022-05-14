package com.hagos.getresultssubactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button b = findViewById(R.id.button2);
        b.setOnClickListener(secondButtonListener);

    }


    // inner class listener separately written for the button
    private View.OnClickListener secondButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent();
            EditText e = (EditText) findViewById(R.id.editText);
            String data = e.getText().toString();
            intent.putExtra("secondactivity", data);
            setResult(Activity.RESULT_OK, intent);
            finish();

        } // end inner class
    }; // end event listener

} /*end main class*/
