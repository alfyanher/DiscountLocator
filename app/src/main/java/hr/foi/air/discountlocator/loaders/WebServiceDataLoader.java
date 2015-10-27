package hr.foi.air.discountlocator.loaders;

import android.app.Activity;
import android.widget.Toast;

import hr.foi.air.discountlocator.core.DataLoader;
import hr.foi.air.discountlocator.core.JsonAdapter;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;
import hr.foi.air.discountlocator.ws.WebServiceAsyncTask;
import hr.foi.air.discountlocator.ws.WebServiceResultHandler;

/**
 * Created by ivan on 27.10.2015..
 */
public class WebServiceDataLoader extends DataLoader {
    Activity activity = null;

    @Override
    public void loadData(Activity activity) {
        this.activity = activity;

        WebServiceAsyncTask storesWs = new WebServiceAsyncTask(storesHandler);
        WebServiceAsyncTask discountsWs = new WebServiceAsyncTask(discountsHandler);

        storesWs.getAll("getAll", Store.class);
        discountsWs.getAll("getAll", Discount.class);
    }

    WebServiceResultHandler storesHandler = new WebServiceResultHandler() {
        @Override
        public void handleResult(String result, boolean ok, long timestamp) {
            if(ok){
                stores = JsonAdapter.getStores(result);
                for(Store s : stores){
                    s.save();
                    System.out.println("Store: " + s.getName());
                }
                Toast.makeText(activity, "Data loaded successfully", Toast.LENGTH_SHORT).show();
            }
        }
    };

    WebServiceResultHandler discountsHandler = new WebServiceResultHandler() {
        @Override
        public void handleResult(String result, boolean ok, long timestamp) {
            if(ok){
                discounts = JsonAdapter.getDiscounts(result);
                for(Discount d : discounts){
                    d.save();
                    System.out.println("Discount: " + d.getName());
                }
                Toast.makeText(activity, "Data loaded successfully", Toast.LENGTH_SHORT).show();
            }
        }
    };


}
