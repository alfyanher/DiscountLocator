package hr.foi.air.discountlocator.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
}
