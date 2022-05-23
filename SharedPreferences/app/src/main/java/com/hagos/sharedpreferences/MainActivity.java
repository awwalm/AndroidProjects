package com.hagos.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnsave = findViewById(R.id.btnsave);
        Button btnload = findViewById(R.id.btnload);

        final EditText etlastname = (EditText) findViewById(R.id.etlastname);
        final EditText etfirstname = (EditText) findViewById(R.id.etfirstname);
        final TextView tv = (TextView) findViewById(R.id.textView);

        btnsave.setOnClickListener(saveListener);
        btnload.setOnClickListener(loadListener);
    }

    private View.OnClickListener saveListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();

            final EditText etlastname = (EditText) findViewById(R.id.etlastname);
            final EditText etfirstname = (EditText) findViewById(R.id.etfirstname);
            final TextView tv = (TextView) findViewById(R.id.textView);

            String lname = etlastname.getText().toString();
            String fname = etfirstname.getText().toString();

            edit.putString("lname", lname);
            edit.putString("fname", fname);
            edit.apply();

            Toast.makeText(MainActivity.this, "Saved it", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener loadListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            final TextView tv = (TextView) findViewById(R.id.textView);

            SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
            String lname = sp.getString("lname", "na");
            String fname = sp.getString("fname", "na");
            tv.setText(String.format("%s, %s", lname, fname));
        }
    };

}
