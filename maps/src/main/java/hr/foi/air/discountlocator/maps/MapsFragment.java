package hr.foi.air.discountlocator.maps;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import hr.foi.air.discountlocator.core.NavigationItem;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public class MapsFragment extends Fragment implements NavigationItem {

    private int position;
    private String name = "Discount Map";
    private GoogleMap mMap;
    private ArrayList<Position> storePositions;

    public MapsFragment(){
        storePositions = new ArrayList<Position>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.maps_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment mMapFragment = (com.google.android.gms.maps.MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mMap = mMapFragment.getMap();

        showStores();
    }

    @Override
    public String getItemName() {
        return name;
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
        storePositions.clear();
        for(Store s : stores){
            storePositions.add(new Position(s.getLatitude(), s.getLongitude(), s.getName()));
        }
        // since data can arrive before onViewCreated you need to check if map != null in showStores()
        // and also call it again in onViewCreated
        showStores();
    }

    public void showStores(){
        if(mMap != null){
            for (Position current : storePositions) {
                // add marker position and title
                double lat = current.getLat();
                double lng = current.getLng();
                mMap.addMarker(new MarkerOptions().position(new LatLng(current.getLat(), current.getLng())).title(current.getName()));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        final FragmentManager fragmentManager = getFragmentManager();
        final Fragment fragment = getFragmentManager().findFragmentById(R.id.map);
        if(fragment != null){
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }
}