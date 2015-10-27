package hr.foi.air.discountlocator.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.lang.reflect.Type;

import hr.foi.air.discountlocator.db.Discount;
import hr.foi.air.discountlocator.db.Store;
import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ivan on 27.10.2015..
 */
public class WebServiceAsyncTask {
    WebServiceResultHandler listener;
    Retrofit retrofit;

    public WebServiceAsyncTask(WebServiceResultHandler listener){
        this.listener = listener;

        //To verify what's sending over the network, use Interceptors
        OkHttpClient client = new OkHttpClient();
        //client.interceptors().add(new HttpInterceptor());

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://cortex.foi.hr/mtl/courses/air/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public void getAll(String method, Type dataItems){
        ServiceCaller serviceCaller = retrofit.create(ServiceCaller.class);

        Call<ResponseBody> call = null;

        if(dataItems == Store.class)
            call = serviceCaller.getStores(method);
        if(dataItems == Discount.class)
            call = serviceCaller.getDiscounts(method);

        if(call != null) {
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                    try {
                        // extract the "items" JSON string
                        String jsonString = response.body().string().toString();
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode jsonResponse = mapper.readTree(jsonString);
                        String jsonStringItems = jsonResponse.get("items").asText();

                        // raise the event
                        if (listener != null)
                            listener.handleResult(jsonStringItems, true, jsonResponse.get("timeStamp").asLong());

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
