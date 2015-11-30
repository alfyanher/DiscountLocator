package hr.foi.air.discountlocator.maps;


import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import hr.foi.air.discountlocator.core.NavigationItem;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public class MapsFragment extends Fragment implements NavigationItem {

    private int position;
    private String name = "Discount Map";
    private GoogleMap mMap;
    private ArrayList<Position> storePositions;

    private ArrayList<Discount> discounts;
    private HashMap<String, ArrayList<Discount>> markers;

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
            storePositions.add(new Position(s.getLatitude(), s.getLongitude(), s.getName(), s.getRemoteId()));
        }
        this.discounts = discounts;
        // since data can arrive before onViewCreated you need to check if map != null in showStores()
        // and also call it again in onViewCreated
        showStores();
    }

    public void showStores(){
        if(mMap != null){
            markers = new HashMap<>();
            for(Position position : storePositions){

                MarkerOptions mo = new MarkerOptions();
                mo.position(new LatLng(position.getLat(), position.getLng()));
                mo.title(position.getName());

                Marker newMarker = mMap.addMarker(mo);

                ArrayList<Discount> relatedDiscounts = new ArrayList<>();
                for(Discount d : discounts){
                    if(d.getStoreId() == position.getId()){
                        relatedDiscounts.add(d);
                    }
                }
                markers.put(newMarker.getId(), relatedDiscounts);
            }

            // handle clickin on markers
            mMap.setOnMarkerClickListener(markerClickHandler);


            // setup default map position to 0th store
            LatLng zoomPosition = new LatLng(storePositions.get(0).getLat(), storePositions.get(0).getLng());
            CameraUpdate center= CameraUpdateFactory.newLatLng(zoomPosition);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

            // update map
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
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

    GoogleMap.OnMarkerClickListener markerClickHandler = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            ArrayList<Discount> discounts = markers.get(marker.getId());
            ArrayList<String> discountTitles = new ArrayList<>();
            for(Discount d : discounts){
                System.out.println(d.getName());
                discountTitles.add((d.getName()));
            }

            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog);

            ListView lv = (ListView) dialog.findViewById(R.id.lv);
            dialog.setCancelable(true);
            dialog.setTitle("Discounts");
            ArrayAdapter<Discount> adapter = new ArrayAdapter<Discount>(getActivity(), android.R.layout.simple_list_item_1, discounts);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Discount value = (Discount) parent.getItemAtPosition(position);
                    System.out.println("You selected: " + value.getName());

                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("id", value.getId());
                    startActivity(intent);
                }
            });

            dialog.show();

            return false;
        }
    };
}