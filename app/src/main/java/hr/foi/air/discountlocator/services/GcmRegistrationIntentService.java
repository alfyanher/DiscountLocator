package hr.foi.air.discountlocator.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.ws.WebServiceAsyncTask;
import hr.foi.air.discountlocator.ws.WebServiceParams;
import hr.foi.air.discountlocator.ws.WebServiceResultHandler;

public class GcmRegistrationIntentService extends IntentService {
    public static final String GCM_TOKEN = "gcmToken";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public GcmRegistrationIntentService() {
        super("GcmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        InstanceID instanceID = InstanceID.getInstance(this);
        String senderId = getResources().getString(R.string.gcm_sender_id);

        try {
            String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            System.out.println("New registration token: " + token);
            preferences.edit().putString(GCM_TOKEN, token).apply();
            sendRegistrationIdToBackend(token);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRegistrationIdToBackend(String registrationId) {
        WebServiceAsyncTask at = new WebServiceAsyncTask();
        WebServiceParams params = new WebServiceParams();

        params.jsonParams = "{\"regId\":\""+ registrationId + "\"}";
        params.methodName = "addNew";
        params.serviceName = "gcm_apps";
        params.targetAttribute = "newAppId";
        params.resultHandler = acceptStores;
        at.execute(new WebServiceParams[]{params});
    }

    WebServiceResultHandler acceptStores = new WebServiceResultHandler() {
        @Override
        public void handleResult(String result, boolean ok, long timestamp) {
            if (ok)
            {
                Toast.makeText(getApplicationContext(), "User registered!", Toast.LENGTH_LONG).show();
            }
        }
    };
}