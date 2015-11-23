package hr.foi.air.discountlocator.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import hr.foi.air.discountlocator.R;

public class AppPreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.app_preferences);
    }
}