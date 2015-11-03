package hr.foi.air.discountlocator.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

public class JsonAdapter {
    public static ArrayList<Store> getStores(String jsonString)
    {
        ArrayList<Store> stores = new ArrayList<Store>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int size = jsonArray.length();

            for(int i = 0; i < size; i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Store store = new Store(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("description"),
                        jsonObject.getString("imgUrl"),
                        jsonObject.getLong("longitude"),
                        jsonObject.getLong("latitude")
                );
                stores.add(store);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stores;
    }

    public static ArrayList<Discount> getDiscounts(String jsonString) {
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        try {
            JSONArray jsonArr = new JSONArray(jsonString);
            int size = jsonArr.length();

            for (int i = 0; i < size; i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Discount discount = new Discount(jsonObj.getLong("id"),
                        jsonObj.getString("name"),
                        jsonObj.getString("description"),
                        jsonObj.getLong("storeId"),
                        sdf.parse(jsonObj.getString("startDate")),
                        sdf.parse(jsonObj.getString("endDate")),
                        jsonObj.getInt("discount"));
                discounts.add(discount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return discounts;
    }
}
