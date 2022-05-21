package com.example.numberguess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener
{
    int numberToGuess = 0;
    EditText e;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberToGuess = initNumberToGuess();

        e = (EditText) findViewById(R.id.editText);
        t = (TextView) findViewById(R.id.textView);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        int number = Integer.parseInt(e.getText().toString());

        emptyLabel:
            if ( TextUtils.isEmpty(e.getText().toString()) )
            {
                e.setError("must not be empty");
                e.setText(0);
                break emptyLabel;
            }

        if (number == numberToGuess)
        {
            t.setText(Integer.toString(number) + " is the correct answer");
        }
        else if (number < numberToGuess)
        {
            t.setText("Guess higher");
        }
        else if (number > numberToGuess)
        {
            t.setText("Guess lower");
        }

        Log.i("Ted", numberToGuess + "");
    }

    public int initNumberToGuess()
    {
        Random r = new Random();
        numberToGuess = r.nextInt(100) + 50;
        Log.i("Ted", numberToGuess + "");
        return numberToGuess;
    }

}
