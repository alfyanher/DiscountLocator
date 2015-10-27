package hr.foi.air.discountlocator.core;

import android.app.Activity;

import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public abstract class DataLoader {

    public ArrayList<Store> stores;
    public ArrayList<Discount> discounts;
    public abstract void LoadData(Activity activity);
}