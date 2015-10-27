package hr.foi.air.discountlocator.ws;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by ivan on 27.10.2015..
 */
public interface ServiceCaller {
    /*
Ideally you would have a response without a header and parse directly to
array of objects you need, e.g. Call<List<Stores>>
 */
    @FormUrlEncoded
    @POST("stores.php")
    Call<ResponseBody> getStores(@Field("method") String method);

    @FormUrlEncoded
    @POST("discounts.php")
    Call<ResponseBody> getDiscounts(@Field("method") String method);
}
