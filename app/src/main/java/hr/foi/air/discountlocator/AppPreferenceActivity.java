package hr.foi.air.discountlocator;


import android.app.Activity;
import android.os.Bundle;

import hr.foi.air.discountlocator.fragments.AppPreferencesFragment;

public class AppPreferenceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // replace the entire content of the activity with fragments' layout
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AppPreferencesFragment())
                .commit();
        }
}
