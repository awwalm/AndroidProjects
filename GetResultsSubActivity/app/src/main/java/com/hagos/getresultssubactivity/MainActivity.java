package com.hagos.getresultssubactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // data members
    private static final int REQUEST_CODE = 1000; // mark activity that sends back value
    Button b;
    TextView t;

    // will contain initialization code as usual
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //...
        b = findViewById(R.id.button);
        t = (TextView) findViewById(R.id.textView);
        b.setOnClickListener(this);

    } /*end onCreate*/


    // when the button is clicked, we will launch SecondActivity
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(this, SecondActivity.class); // explicit intent
        startActivityForResult(intent, REQUEST_CODE);

    } /*end onClick*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_CODE) && (resultCode == Activity.RESULT_OK))
        {
            t.setText(data.getStringExtra("secondactivity"));
        }

    } /*end onActivityResult*/

}
