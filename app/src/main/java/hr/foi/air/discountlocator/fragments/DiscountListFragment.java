package hr.foi.air.discountlocator.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

import hr.foi.air.discountlocator.DiscountsExpandableAdapter;
import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.core.DataLoader;
import hr.foi.air.discountlocator.core.NavigationItem;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;
import hr.foi.air.discountlocator.loaders.DbDataLoader;
import hr.foi.air.discountlocator.loaders.WebServiceDataLoader;

public class DiscountListFragment extends Fragment implements NavigationItem {

    private int position;
    private String name = "Discount List";
    private boolean mAlereadyLoaded = false;
    private ArrayList<Store> stores;
    private ArrayList<Discount> discounts;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discount_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState == null && !mAlereadyLoaded){
            mAlereadyLoaded = true;
            // load data from database
            DataLoader dl = new DbDataLoader();
            dl.LoadData(getActivity());

            //if no data loaded, call ws if allowed
            if(!dl.dataLoaded()){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if(preferences.getBoolean("pref_allow_web", true)){
                    dl = new WebServiceDataLoader();
                    dl.LoadData(getActivity());
                }
                else{
                    Toast.makeText(getActivity(), "Local database is empty. Get from web disabled.", Toast.LENGTH_LONG).show();
                }
            }

        } else {
            loadData(stores, discounts);
        }
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

    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts) {
        DiscountsExpandableAdapter adapter = new DiscountsExpandableAdapter(stores, discounts);
        adapter.setInflater( (LayoutInflater) getActivity().getSystemService(FragmentActivity.LAYOUT_INFLATER_SERVICE), getActivity());
        ExpandableListView expandableList = (ExpandableListView) getView().findViewById(R.id.elv_stores_and_discounts);

        if(expandableList != null) {
            expandableList.setAdapter(adapter);
        }
    }
}