package com.hagos.fragments2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
//import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentA f = new FragmentA();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
       // ft.add(R.id.frag_placeholder, null, "");
        ft.add(R.id.frag_placeholder, f ,"");
        ft.commit();

    }
}
