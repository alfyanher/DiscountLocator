package hr.foi.air.discountlocator.core;

import android.app.Activity;

import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

/**
 * Created by ivan on 27.10.2015..
 */
public abstract class DataLoader {
    public ArrayList<Store> stores;
    public ArrayList<Discount> discounts;

    public abstract void loadData(Activity activity);
}
