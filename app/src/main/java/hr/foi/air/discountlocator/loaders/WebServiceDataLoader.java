package hr.foi.air.discountlocator.loaders;

import android.app.Activity;
import android.widget.Toast;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.core.DataLoader;
import hr.foi.air.discountlocator.core.JsonAdapter;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;
import hr.foi.air.discountlocator.ws.WebServiceAsyncTask;
import hr.foi.air.discountlocator.ws.WebServiceParams;
import hr.foi.air.discountlocator.ws.WebServiceResultHandler;

public class WebServiceDataLoader extends DataLoader {
    Activity activity = null;
    private boolean storesLoaded = false;
    private boolean discountsLoaded = false;

    public void LoadData(Activity activity){
        this.activity = activity;

        WebServiceAsyncTask at = new WebServiceAsyncTask();
        WebServiceParams params = new WebServiceParams();
        params.jsonParams = "";
        params.methodName = "getAll";
        params.serviceName = "stores";
        params.targetAttribute = "items";
        params.resultHandler = getAllStoresHandler;
        at.execute(new WebServiceParams[]{params});

        WebServiceAsyncTask at_discounts = new WebServiceAsyncTask();
        WebServiceParams params_discounts = new WebServiceParams();
        params_discounts.jsonParams = "";
        params_discounts.methodName = "getAll";
        params_discounts.serviceName = "discounts";
        params_discounts.targetAttribute = "items";
        params_discounts.resultHandler = getAllDiscountsHandler;
        at_discounts.execute(new WebServiceParams[]{params_discounts});

        //Think of:
        //Why it is not advisable to call the web service layer twice through same async task object?
    }

    WebServiceResultHandler getAllStoresHandler = new WebServiceResultHandler() {
        @Override
        public void handleResult(String result, boolean ok, long timestamp) {
            if (ok)
            {
                try {
                    stores = JsonAdapter.getStores(result);
                    for (Store s : stores) {
                        s.save();
                    }
                    Toast.makeText(activity, R.string.data_loaded_stores, Toast.LENGTH_SHORT).show();
                    storesLoaded = true;
                    showLoadedData();
                } catch (Exception e) {
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    WebServiceResultHandler getAllDiscountsHandler = new WebServiceResultHandler() {
        @Override
        public void handleResult(String result, boolean ok, long timestamp) {
            if (ok)
            {
                try {
                    discounts = JsonAdapter.getDiscounts(result);
                    for (Discount d : discounts) {
                        d.save();
                    }
                    Toast.makeText(activity, R.string.data_loaded_discounts, Toast.LENGTH_SHORT).show();
                    discountsLoaded = true;
                    showLoadedData();
                } catch (Exception e) {
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void showLoadedData()
    {
        //synchronization
        if (storesLoaded && discountsLoaded)
        {
            //binding newly downloaded data
            bindDiscountsToStores();

            //TODO Show data on screen!

            //reset flags
            storesLoaded = false;
            discountsLoaded = false;
        }
    }

    private void bindDiscountsToStores()
    {
        for (Store s: stores)
        {
            for (Discount d: discounts)
            {
                if (d.getStoreId() == s.getRemoteId())
                {
                    d.setStore(s);
                    d.save();
                }
            }
        }
    }
}
