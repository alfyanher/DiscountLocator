package hr.foi.air.discountlocator;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import java.util.ArrayList;

import hr.foi.air.discountlocator.core.NavigationItem;

public class NavigationManager {

    public ArrayList<NavigationItem> navigationItems;
    private static NavigationManager instance;
    private Activity mHandlerActivity;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    // private constructor
    private NavigationManager(){
        navigationItems = new ArrayList<NavigationItem>();
    }

    public static NavigationManager getInstance(){
        if(instance == null)
            instance = new NavigationManager();
        return instance;
    }

    public ArrayList<String> getNavigationItemsAsStrings(){
        ArrayList<String> navigationItemStrings = new ArrayList<String>();
        for (NavigationItem item : navigationItems) {
            navigationItemStrings.add(item.getItemName());
        }
        return navigationItemStrings;
    }

    public void setDependencies(Activity handlerActivity, DrawerLayout drawerLayout, NavigationView navigationView){
        this.mHandlerActivity = handlerActivity;
        this.mNavigationView = navigationView;
        this.mDrawerLayout = drawerLayout;

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectItem(menuItem);
                return true;
            }
        });
    }

    private void selectItem(MenuItem menuItem){

        // uses the menu item to find the NavigationItem (interface implementator)
        NavigationItem clickedItem = navigationItems.get(menuItem.getItemId());

        FragmentManager fragmentManager = mHandlerActivity.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, clickedItem.getFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("")
                .commit();

        menuItem.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void addItem(NavigationItem newItem){
        newItem.setPosition(navigationItems.size());
        navigationItems.add(newItem);
        mNavigationView.getMenu().add(0,newItem.getPosition(),0,newItem.getItemName());
    }
}
