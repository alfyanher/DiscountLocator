package hr.foi.air.discountlocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find reference to btn_details and subscribe to onClick event.
        Button btnDetails = (Button)findViewById(R.id.btn_details);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DiscountDetailsActivity.class);
                startActivity(i);
                //Note: Think why we can't use this as Context and we have to use getApplicationContext?
                //Note2: Can you explain what is parameter of setOnClickListener method?
            }
        });
    }

    /**
     * Overides extended method and inflates menu. After adding this method, user will be able to show menu.
     * @param menu The reference to the object that should be inflated according to menu definition in resources.
     * @return True if everything is OK.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles menu item clicks by overriding extended method.
     * @param item Contains reference to the item that user clicked on.
     * @return True if event is handled correctly.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Switch on item id.
        //Currently there are no actions to perform.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_search:
                break;
            case R.id.action_settings:
                break;
        }

        //Temporary shows message that click is handled.
        Toast.makeText(this, "Menu item " + item.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        return true;
    }
}
