package hr.foi.air.discountlocator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.foi.air.discountlocator.core.DataLoader;
import hr.foi.air.discountlocator.loaders.WebServiceDataLoader;

public class DiscountListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discount_list_fragment, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataLoader dl = new WebServiceDataLoader();
        dl.LoadData(getActivity());
    }

}