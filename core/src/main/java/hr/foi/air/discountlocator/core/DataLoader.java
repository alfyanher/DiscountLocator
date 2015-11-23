package hr.foi.air.discountlocator.core;

import android.app.Activity;

import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public abstract class DataLoader {

    public ArrayList<Store> stores;
    public ArrayList<Discount> discounts;
    OnDataLoadedListener dataLoadedListener;

    public void LoadData(Activity activity){
        if(dataLoadedListener == null)
            dataLoadedListener = (OnDataLoadedListener) activity;
    }

    public boolean dataLoaded(){
        if(stores == null || discounts == null){
            return false;
        }
        else{
            dataLoadedListener.onDataLoaded(stores, discounts);
            return true;
        }
    }
}