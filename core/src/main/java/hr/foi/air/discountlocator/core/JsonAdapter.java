package hr.foi.air.discountlocator.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;

/**
 * Created by ivan on 27.10.2015..
 */
public class JsonAdapter {
    public static ArrayList<Store> getStores(String jsonString){
        ArrayList<Store> stores = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            stores = mapper.readValue(jsonString, new TypeReference<ArrayList<Store>>(){});
        } catch (IOException e){
            e.printStackTrace();
        }

        return stores;
    }

    public static ArrayList<Discount> getDiscounts(String jsonString){
        ArrayList<Discount> discounts = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            discounts = mapper.readValue(jsonString, new TypeReference<ArrayList<Discount>>(){});
        } catch (IOException e){
            e.printStackTrace();
        }

        return discounts;
    }
}
