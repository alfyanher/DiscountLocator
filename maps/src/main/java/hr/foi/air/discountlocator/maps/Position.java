package hr.foi.air.discountlocator.maps;

public class Position {
    private double lat;
    private double lng;
    private String name;

    // Store Id
    private long id;

    // getter
    public long getId() {
        return id;
    }

    // Setter
    public void setId(long id) {
        this.id = id;
    }

    public Position(double lat, double lng, String name, long id){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.id = id;
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