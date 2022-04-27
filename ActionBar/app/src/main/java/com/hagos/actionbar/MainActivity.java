package com.hagos.actionbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true ? super.onCreateOptionsMenu(menu) : true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        //return super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.menuFile:
                Toast.makeText(this, "File", Toast.LENGTH_LONG).show();
                break;

            case R.id.menuHelp:
                Toast.makeText(this, "Help", Toast.LENGTH_LONG).show();
                break;

            case R.id.menuEdit:
                Toast.makeText(this, "Edit", Toast.LENGTH_LONG).show();
                break;

            case R.id.menuExit:
                Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show();
                break;

            default: Toast.makeText(this, "Nothing Detected", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

}
