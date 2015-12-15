package hr.foi.air.discountlocator.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.ads.DlAdsListener;
import hr.foi.air.discountlocator.db.Discount;

public class DiscountDetailsFragment extends Fragment {

    private InterstitialAd mInterstitial;
    private static final String AD_UNIT_ID = "ca-app-pub-8639732656343372/9330745843";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discount_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInterstitial = new InterstitialAd(this.getActivity());
        mInterstitial.setAdUnitId(AD_UNIT_ID);
        mInterstitial.setAdListener(new DlAdsListener(this.getActivity()) {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitial.show();
            }
        });

        mInterstitial.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onStart() {
        super.onStart();

        // get Bundle and extra data sent from caller activity
        Bundle data = getArguments();
        long id = data.getLong("id");

        Discount d = new Select().from(Discount.class).where("remoteId == ?", id).executeSingle();

        TextView name = ((TextView) getView().findViewById(R.id.discount_details_name));
        name.setText(d.getName() + ", " + d.getDiscountValue() + "%");

        TextView description = ((TextView) getView().findViewById(R.id.discount_details_description));
        description.setText(d.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        TextView startDate = ((TextView) getView().findViewById(R.id.discount_details_start));
        startDate.setText(sdf.format(d.getStartDate()));

        TextView endDate = ((TextView) getView().findViewById(R.id.discount_details_end));
        endDate.setText(" - " + sdf.format(d.getEndDate()));
    }


}
