package hr.foi.air.discountlocator.loaders;

import android.app.Activity;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.discountlocator.R;
import hr.foi.air.discountlocator.core.DataLoader;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public class DbDataLoader extends DataLoader {
    @Override
    public void LoadData(Activity activity) {
        super.LoadData(activity); // setup the event listener (MainActivity)

        List<Store> storesFromDb = null ;
        List<Discount> discountsFromDb = null;

        boolean databaseQuerySuccessful = false;

        try{
            storesFromDb = new Select().all().from(Store.class).execute();
            discountsFromDb = new Select().all().from(Discount.class).execute();

            databaseQuerySuccessful = true;
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(databaseQuerySuccessful == true && storesFromDb.size() > 0 ){
            Toast.makeText(activity, R.string.loading_from_db, Toast.LENGTH_SHORT).show();

            stores = (ArrayList<Store>) storesFromDb;
            discounts = (ArrayList<Discount>) discountsFromDb;

            dataLoaded(); // raise the event
        }
    }
}