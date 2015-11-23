package hr.foi.air.discountlocator.loaders;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.discountlocator.core.DataLoader;
import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public class SearchDataLoader extends DataLoader {

    public void searchData(String searchTerm) {
        // add % for LIKE clause
        searchTerm = "%" +searchTerm + "%";
        List<Discount> discountsFromDb = null;

        // check database
        boolean databaseQuerySuccessfull = false;
        try{
            discountsFromDb = new Select().all().from(Discount.class).where("name LIKE ?", searchTerm).or("description LIKE ?", searchTerm).execute();
            databaseQuerySuccessfull = true;
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(databaseQuerySuccessfull == true && discountsFromDb.size() > 0 ){

            discounts = (ArrayList<Discount>) discountsFromDb;
            stores = new ArrayList<Store>();

            // get stores
            for (Discount discount : discountsFromDb) {
                Store s = new Select().all().from(Store.class).where("remoteId == ? ", discount.getStoreId()).executeSingle();
                if(!stores.contains(s)) stores.add(s);
            }

            dataLoaded();
        }
    }
}