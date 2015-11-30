package hr.foi.air.discountlocator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NotificationDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_display);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle data = getIntent().getExtras();

        TextView titleText = (TextView) findViewById(R.id.notification_title);
        TextView bodyText = (TextView) findViewById(R.id.notification_body);

        titleText.setText(data.getString("title"));
        bodyText.setText(data.getString("body"));

    }
}