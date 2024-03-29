package hr.foi.air.discountlocator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import hr.foi.air.discountlocator.ads.DlAdsListener;
import hr.foi.air.discountlocator.core.OnDataLoadedListener;
import hr.foi.air.discountlocator.core.PreferenceManagerHelper;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;
import hr.foi.air.discountlocator.fragments.DiscountListFragment;
import hr.foi.air.discountlocator.iab.BuyMapFragment;
import hr.foi.air.discountlocator.maps.MapsFragment;
import hr.foi.air.discountlocator.services.GcmRegistrationIntentService;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, OnDataLoadedListener{
    private DrawerLayout mDrawer;
    private Toolbar mToolbar; // from android.support.v7.widget.Toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFm;
    NavigationManager nm;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main);

        // register with GCM
        registerWithGcm();

        //initializing ads
        initializeAds();

        // replace the ActionBar with the Toolbar
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // find the drawer layout
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(mDrawerToggle);

        mFm = getFragmentManager();
        mFm.addOnBackStackChangedListener(this);

        mToolbar.setNavigationOnClickListener(navigationClick);

        nm = NavigationManager.getInstance();
        nm.setDependencies(this, mDrawer, (NavigationView) findViewById(R.id.nv_drawer));

        if(savedInstanceState == null){  // running this for the first time
            mFm = getFragmentManager();
            mFm.addOnBackStackChangedListener(this);
            mToolbar.setNavigationOnClickListener(navigationClick);

            // add the modules, only once, only here
            nm.clearItems();
            nm.addItem(new DiscountListFragment());
            if(!PreferenceManagerHelper.getMapBought(this)) {
                nm.addItem(new BuyMapFragment());
            }
            else {
                nm.addItem(new MapsFragment());
            }
            nm.loadDefaultFragment();

        } else {  // running to reuse existing fragments
            nm.reloadItems(); // do not add modules again, reuse existing ones
        }
    }

    // ActionBarDrawerToggle from support v.7
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    /**
     * Overides extended method and inflates menu. After adding this method, user will be able to show menu.
     * @param menu The reference to the object that should be inflated according to menu definition in resources.
     * @return True if everything is OK.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles menu item clicks by overriding extended method.
     * @param item Contains reference to the item that user clicked on.
     * @return True if event is handled correctly.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Switch on item id.
        //Currently there are no actions to perform.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_search:
                SearchDialog sd = new SearchDialog(this);
                sd.show();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this, AppPreferenceActivity.class);
                startActivity(intent);
                break;
        }

        //Temporary shows message that click is handled.
        Toast.makeText(this, "Menu item " + item.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0){
            // there is something on the stack, I'm in the fragment
            if(mDrawer.isDrawerOpen(GravityCompat.START)){
                mDrawer.closeDrawer(GravityCompat.START);
            }
            else{
                getFragmentManager().popBackStack();
            }
        } else {
            // I'm on the landing page, close the drawer or exit
            if(mDrawer.isDrawerOpen(GravityCompat.START)){
                mDrawer.closeDrawer(GravityCompat.START);
            }
            else{
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackStackChanged() {
        mDrawerToggle.setDrawerIndicatorEnabled(mFm.getBackStackEntryCount() == 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(mFm.getBackStackEntryCount() > 0);
        mDrawerToggle.syncState();
    }

    @Override
    public void onDataLoaded(ArrayList<Store> stores, ArrayList<Discount> discounts) {
        nm.makeDataChange(stores, discounts);

    }

    private void registerWithGcm() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gcmToken = preferences.getString(GcmRegistrationIntentService.GCM_TOKEN, "");
        if(gcmToken.isEmpty()) {
            Intent i = new Intent(this, GcmRegistrationIntentService.class);
            startService(i);
        } else {
            System.out.println("Already registered with: " + gcmToken);
        }
    }

    private void initializeAds(){
        adView = (AdView) findViewById(R.id.adView);
        adView.setAdListener(new DlAdsListener(this));
        AdRequest.Builder adBuilder = new AdRequest.Builder();
        //adBuilder.addTestDevice("Device ID"); //<--- Device ID, sometimes necessary for testing
        adView.loadAd(adBuilder.build());
    }

    @Override
    protected void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adView.resume();
    }

    @Override
    protected void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }

    /*
    EVENT HANDLERS
    */

    View.OnClickListener navigationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getFragmentManager().getBackStackEntryCount() == 0) {
                mDrawer.openDrawer(GravityCompat.START);
            }
            else{
                onBackPressed();
            }
        }
    };
}
