package hr.foi.air.discountlocator.ws;

/**
 * Created by ivan on 27.10.2015..
 */
public interface WebServiceResultHandler {
    void handleResult(String result, boolean ok, long timestamp);
}
