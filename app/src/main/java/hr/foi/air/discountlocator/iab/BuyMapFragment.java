package hr.foi.air.discountlocator.iab;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.core.NavigationItem;
import hr.foi.air.discountlocator.core.PreferenceManagerHelper;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;
import hr.foi.air.discountlocator.iab_utils.IabHelper;
import hr.foi.air.discountlocator.iab_utils.IabResult;
import hr.foi.air.discountlocator.iab_utils.Inventory;
import hr.foi.air.discountlocator.iab_utils.Purchase;

public class BuyMapFragment extends Fragment implements NavigationItem{
    int position = 0;

    Activity mActivity;
    private boolean mMapBought = false;
    private static final String TAG = "DiscountLocator";
    private IabHelper mHelper;

    //private static final String SKU_MAP = "hr.foi.air.discountlocator.iab.maps";
    //Testing SKUs
    private static final String SKU_MAP = "android.test.purchased";
    //private static final String SKU_MAP = "android.test.canceled";
    //private static final String SKU_MAP = "android.test.refunded";
    //private static final String SKU_MAP = "android.test.item_unavailable";

    // (arbitrary) request code for the purchase flow
    private static final int RC_REQUEST = 10001;
    // developers' public key
    private final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8....please make sure to use your onw key...";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps_billing, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = this.getActivity();
        mMapBought = PreferenceManagerHelper.getMapBought(mActivity);

        TextView txtMapDesc = (TextView) mActivity.findViewById(R.id.txt_map_desc);
        Button btnMapBuy = (Button) mActivity.findViewById(R.id.btn_map_buy);
        ImageView imgMapDesc = (ImageView) mActivity.findViewById(R.id.img_map_desc);

        txtMapDesc.setEnabled(!mMapBought);
        btnMapBuy.setEnabled(!mMapBought);
        imgMapDesc.setEnabled(!mMapBought);

        btnMapBuy.setOnClickListener(onBuyMapButtonClicked);

        setupIabHelper();
    }

    private void setupIabHelper()
    {
        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this.getActivity(), base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    Log.d(TAG, "Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    public View.OnClickListener onBuyMapButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Launching purchase flow for map.");

            //TODO: for security, generate your payload here for verification. See the comments on
            //verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
            //an empty string, but on a production app you should carefully generate this.

            String payload = "";
            // when testing, uncomment different SKUs and check the clicking result
            mHelper.launchPurchaseFlow(mActivity, SKU_MAP, RC_REQUEST, mPurchaseFinishedListener, payload);

            /*
            //if buying subscriptions, the flow is slightly different
            if (!mHelper.subscriptionsSupported()) {
               complain("Subscriptions not supported on your device yet. Sorry!");
               return;
            }
            Log.d(TAG, "Launching purchase flow for subscription.");
            mHelper.launchPurchaseFlow(this,
               SKU_NOTIFICATIONS, IabHelper.ITEM_TYPE_SUBS,
               RC_REQUEST, mPurchaseFinishedListener, payload);
            */
        }
    };

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                Log.d(TAG, "Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                Log.d(TAG, "Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_MAP)) {
                Log.d(TAG, "Purchase is map. Saving...");
                PreferenceManagerHelper.setMapBought(true, mActivity);
            }
        }
    };

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        //String payload = p.getDeveloperPayload();

       /*
        * TODO: verify that the developer payload of the purchase is correct. It will be
        * the same one that you sent when initiating the purchase.
        *
        * WARNING: Locally generating a random string when starting a purchase and
        * verifying it here might seem like a good approach, but this will fail in the
        * case where the user purchases an item on one device and then uses your app on
        * a different device, because on the other device you will not have access to the
        * random string you originally generated.
        *
        * So a good developer payload has these characteristics:
        *
        * 1. If two different users purchase an item, the payload is different between them,
        *    so that one user's purchase can't be replayed to another user.
        *
        * 2. The payload must be such that you can verify it even when the app wasn't the
        *    one who initiated the purchase flow (so that items purchased by the user on
        *    one device work on other devices owned by the user).
        *
        * Using your own server to store and verify developer payloads across app
        * installations is recommended.
        */

        return true;
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                Log.d(TAG, "Failed to query inventory: " + result);
                return;
            }

            //to consume use
            //THIS is ONLY for TESTING purposes. PLEASE comment out this before production.
            if (inventory.hasPurchase(SKU_MAP)) {
                mHelper.consumeAsync(inventory.getPurchase(SKU_MAP), mConsumeFinishedListener); }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            Purchase mapPurchase = inventory.getPurchase(SKU_MAP);
            mMapBought = (mapPurchase != null && verifyDeveloperPayload(mapPurchase));
            Log.d(TAG, "User bought MAP: " + (mMapBought ? "YES" : "NO"));

            PreferenceManagerHelper.setMapBought(mMapBought, mActivity);

            //updateUi();
            //setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;
            //check which sku returned
            //..
            //..

            //check result
            if (result.isSuccess()) {
                // successfully consumed
                //do the logic here
            }
            else {
                Log.d(TAG, "Error while consuming: " + result);
            }
            //updateUi();
            //setWaitScreen(false);
            Log.d(TAG, "End consumption flow.");
        }
    };

    @Override
    public String getItemName() {
        return "Buy Map View";
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

    @Override
    public void loadData(ArrayList<Store> stores, ArrayList<Discount> discounts) {
        //Nothing for this fragment.
    }
}
