package com.grocs.sensors.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.grocs.sensors.R;

public class SensorPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
