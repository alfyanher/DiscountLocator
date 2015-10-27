package hr.foi.air.discountlocator.ws;

public interface WebServiceResultHandler {
    public void handleResult(
            String result,
            boolean ok,
            long timestamp
    );
}
