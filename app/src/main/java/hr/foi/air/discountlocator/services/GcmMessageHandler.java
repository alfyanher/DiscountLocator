package hr.foi.air.discountlocator.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import hr.foi.air.discountlocator.NotificationDisplayActivity;
import hr.foi.air.discountlocator.R;

public class GcmMessageHandler extends GcmListenerService {

    public static final int MESSAGE_NOTIFICATION_ID = 1337414; // random unique number given by developer

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        System.out.println("Message: " + data.getString("message"));
        createNotification("DiscountLocator", data.getString("message"));

    }

    // Android level notification
    private void createNotification(String title, String body) {
        Context context = getBaseContext();

        Intent resultIntent = new Intent(this, NotificationDisplayActivity.class);
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("body", body);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(body);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, notificationBuilder.build());

    }
}