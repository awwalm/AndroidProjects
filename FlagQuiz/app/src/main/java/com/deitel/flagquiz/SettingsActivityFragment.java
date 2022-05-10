package com.deitel.flagquiz;

import android.preference.PreferenceFragment;
import android.os.Bundle;

public class SettingsActivityFragment extends PreferenceFragment
{
    // creates preferences GUI from preferences.xml file in res/xml
    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences); // load from XML
    }
}
