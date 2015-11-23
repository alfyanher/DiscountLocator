package hr.foi.air.discountlocator.core;


import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public interface OnDataLoadedListener {
    public void onDataLoaded(ArrayList<Store> stores, ArrayList<Discount> discounts);
}
