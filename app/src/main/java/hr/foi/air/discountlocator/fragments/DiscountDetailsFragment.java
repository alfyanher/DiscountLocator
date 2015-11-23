package hr.foi.air.discountlocator.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.db.Discount;

public class DiscountDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discount_details, container, false);
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
