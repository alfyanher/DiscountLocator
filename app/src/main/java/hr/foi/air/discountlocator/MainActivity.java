package hr.foi.air.discountlocator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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

import java.util.ArrayList;

import hr.foi.air.discountlocator.core.OnDataLoadedListener;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;
import hr.foi.air.discountlocator.fragments.DiscountListFragment;
import hr.foi.air.discountlocator.maps.MapsFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, OnDataLoadedListener{
    private DrawerLayout mDrawer;
    private Toolbar mToolbar; // from android.support.v7.widget.Toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFm;
    private DiscountListFragment dlf;
    private MapsFragment mf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main);

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

        NavigationManager nm = NavigationManager.getInstance();
        nm.setDependencies(this, mDrawer, (NavigationView) findViewById(R.id.nv_drawer));

        if(savedInstanceState == null){  // running this for the first time
            dlf = new DiscountListFragment();
            mf = new MapsFragment();

            FragmentTransaction fm = getFragmentManager().beginTransaction();
            fm.replace(R.id.fragment_container, dlf);
            fm.commit();

        } else {  // running to reuse existing fragments
            dlf = (DiscountListFragment) mFm.findFragmentById(R.id.discount_fragment); // add the name in the layout file
            mf = (MapsFragment) mFm.findFragmentById(R.id.frame);

            if(dlf == null){
                dlf = new DiscountListFragment();
            }
            if(mf == null) {
                mf = new MapsFragment();
            }
        }

        nm.addItem(dlf);
        nm.addItem(mf);
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
        if(dlf != null)
            dlf.loadData(stores, discounts);
        if(mf != null)
            mf.loadData(stores, discounts);
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
