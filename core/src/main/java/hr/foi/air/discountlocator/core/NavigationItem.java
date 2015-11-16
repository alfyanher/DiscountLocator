package hr.foi.air.discountlocator.core;

import android.app.Fragment;

public interface NavigationItem {
    public String getItemName();
    public int getPosition();
    public void setPosition(int position);
    public Fragment getFragment();
}