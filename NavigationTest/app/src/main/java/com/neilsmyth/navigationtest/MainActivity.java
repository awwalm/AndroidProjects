package com.neilsmyth.navigationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.net.Uri;

import com.neilsmyth.navigationtest.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements
        SecondFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        */
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {
        //
    }
}
