package com.hagos.gcf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class Calculate extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        int bigno, smallno = 0;
        int rem = 1;

        TextView gcftext = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("gcfdata");

        if ((bundle != null) & !bundle.isEmpty())
        {
            int first = bundle.getInt("fno", (int)Math.random()+1);
            int second = bundle.getInt("sno", (int)Math.random()+1);

            if (first > second)
            {
                bigno = first;
                smallno = second;
            }
            else
            {
                bigno = second;
                smallno = first;
            }

            while ((rem = bigno % smallno) != 0)
            {
                bigno = smallno;
                smallno = rem;
            }
            gcftext.setText(String.format("GCF = %d", smallno));
        }

    }
}
