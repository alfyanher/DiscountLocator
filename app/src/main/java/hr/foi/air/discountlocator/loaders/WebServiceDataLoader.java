package hr.foi.air.discountlocator.loaders;

import android.app.Activity;
import android.widget.Toast;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.core.DataLoader;
import hr.foi.air.discountlocator.core.JsonAdapter;
import hr.foi.air.discountlocator.db.Store;
import hr.foi.air.discountlocator.ws.WebServiceAsyncTask;
import hr.foi.air.discountlocator.ws.WebServiceParams;
import hr.foi.air.discountlocator.ws.WebServiceResultHandler;

public class WebServiceDataLoader extends DataLoader {
    Activity activity = null;

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
                    Toast.makeText(activity, R.string.data_loaded, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
