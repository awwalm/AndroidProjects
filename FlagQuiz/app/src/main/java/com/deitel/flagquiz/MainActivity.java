package com.deitel.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;


//import com.google.android.material.appbar.AppBarLayout; // imported via com.android.support module dependency

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Set;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity
{
    // keys for reading data from SharedPreferences
    public static final String CHOICES = "pref_numberOfChoices";
    public static final String REGIONS = "pref_regionsToInclude";

    private boolean phoneDevice = true; // used to force portrait mode
    private boolean preferencesChanged = true; // did preferences change?


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set default values in the app's SharedPreferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // register listener for SharedPreferences changes
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(preferencesChangeListener);

        // determine screen size
        int screenSize = getResources().getConfiguration().screenLayout
                         &
                         Configuration.SCREENLAYOUT_SIZE_MASK;

        // if device is a tablet, set phoneDevice to false\
        if ( screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE )
        {
            phoneDevice = false;
        }

        // if running on phone-sized device, allow only portrait orientation
        if (phoneDevice)
        {
            setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        }

    } /*end onCreate method*/


    // called after onCreate complete execution
    @Override
    protected void onStart()
    {
        super.onStart();

        if (preferencesChanged)
        {
            // now that the default preferences have been set,
            // initialize MainActivityFragment and start the quiz
            MainActivityFragment quizFragment = (MainActivityFragment) getSupportFragmentManager().
                                                findFragmentById( R.id.quizFragment );

            quizFragment.updateGuessRows ( PreferenceManager.getDefaultSharedPreferences(this) );
            quizFragment.updateRegions ( PreferenceManager.getDefaultSharedPreferences(this) );
            quizFragment.resetQuiz ();
            preferencesChanged = false;
        }
    } /*end onStart method*/


    // show menu if app is running on a phone or a portrait-oriented tablet
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        // get the device's current orientation
        int orientation = getResources().getConfiguration().orientation;

        // display the app's menu only in portrait orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // inflate the menu
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        else
        {
            return false;
        }

    } /*end onCreateOptionsMenu*/


    // called when a menu item is selected
    // also displays the SettingsActivity when running on a phone
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);
        return super.onOptionsItemSelected(item);

    } /*end onOptionsItemSelected*/


    // anonymous inner-class implementing OnSharedPreferencesChangeListener interface
    // also a listener for changes to the app's SharedPreferences
    private OnSharedPreferenceChangeListener preferencesChangeListener =
            new OnSharedPreferenceChangeListener()
            {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
                {
                    preferencesChanged = true; // user changed settings

                    MainActivityFragment quizFragment = (MainActivityFragment) getSupportFragmentManager().
                                                        findFragmentById (R.id.quizFragment);

                    if (key.equals(CHOICES)) // no. of choices to display changed
                    {
                        quizFragment.updateGuessRows(sharedPreferences);
                        quizFragment.resetQuiz();
                    } // level 1 if-block
                    else if (key.equals(REGIONS)) // regions to include changed
                    {
                        Set<String> regions = sharedPreferences.getStringSet(REGIONS, null);

                        if (regions != null && regions.size() > 0)
                        {
                            quizFragment.updateRegions(sharedPreferences);
                            quizFragment.resetQuiz();
                        } // level 2 if-block
                        else
                        {
                            // must select one region--set North America as default
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            regions.add( getString(R.string.default_region) );
                            editor.putStringSet(REGIONS, regions);
                            editor.apply();

                            Toast.makeText(
                             MainActivity.this, R.string.default_region_meesage,
                                    Toast.LENGTH_SHORT).show();
                        } // first level 2 else-block

                    } // first level 1 else-block

                    Toast.makeText(MainActivity.this,
                            R.string.restarting_quiz,
                            Toast.LENGTH_SHORT).show();
                }
            };


} /*end MainActivity class*/
