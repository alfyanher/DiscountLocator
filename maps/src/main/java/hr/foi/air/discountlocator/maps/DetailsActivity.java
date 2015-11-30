package hr.foi.air.discountlocator.maps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;

import hr.foi.air.discountlocator.db.Discount;


public class DetailsActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_discount_details);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        // get Bundle and extra data sent from caller activity
        Bundle data = getIntent().getExtras();
        long id = data.getLong("id");

        Discount d = new Select().from(Discount.class).where("remoteId == ?", id).executeSingle();

        TextView name = ((TextView) findViewById(R.id.discount_details_name));
        name.setText(d.getName() + ", " + d.getDiscountValue() + "%");

        TextView description = ((TextView) findViewById(R.id.discount_details_description));
        description.setText(d.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        TextView startDate = ((TextView) findViewById(R.id.discount_details_start));
        startDate.setText(sdf.format(d.getStartDate()));

        TextView endDate = ((TextView) findViewById(R.id.discount_details_end));
        endDate.setText(" - " + sdf.format(d.getEndDate()));
    }

}
