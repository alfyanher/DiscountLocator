package hr.foi.air.discountlocator.maps;

public class Position {
    private double lat;
    private double lng;
    private String name;

    public Position(double lat, double lng, String name){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getLat() {
        return lat / 1000000;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng / 1000000;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }

}