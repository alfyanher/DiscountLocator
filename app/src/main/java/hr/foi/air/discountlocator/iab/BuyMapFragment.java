package hr.foi.air.discountlocator.iab;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.core.NavigationItem;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public class BuyMapFragment extends Fragment implements NavigationItem{
    int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps_billing, container, false);
    }

    @Override
    public String getItemName() {
        return "Buy Map View";
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts) {
        //Nothing for this fragment.
    }
}
