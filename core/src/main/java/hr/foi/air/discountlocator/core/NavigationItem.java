package hr.foi.air.discountlocator.core;

import android.app.Fragment;

import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public interface NavigationItem {
    public String getItemName();
    public int getPosition();
    public void setPosition(int position);
    public Fragment getFragment();
    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts);
}