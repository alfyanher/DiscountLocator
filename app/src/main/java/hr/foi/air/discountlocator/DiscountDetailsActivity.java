package hr.foi.air.discountlocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;

import hr.foi.air.discountlocator.db.Discount;

public class DiscountDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_details);

        //Showing back button in the action bar of the activity
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //Note: Think why we had to use getSupportActionBar() method instead of getActionBar()?

        //extracting the data from the bundle
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

    /**
     * Overriding extended method to handle onOptionsItemsSelected event of back button in the
     * action bar of the activity. Other options are handled by calling original (overridden) method.
     * @param item A reference to selected options item.
     * @return True if event is successfully handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // back button (or app icon) in action bar clicked; go to main activity
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Note: Find out what is Intent FLAG used for. What is this flag user for?
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
