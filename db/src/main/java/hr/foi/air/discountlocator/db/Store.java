package hr.foi.air.discountlocator.db;

/**
 * Entity class representing a store item. 
 * Each store can have one or more discounts and is connected to one or more categories.
 * @see Discount
 * @see Category
 * @see StoreCategory
 */
public class Store {
    private long remoteId;
    private String name;
    private String description;
    private String imgUrl;
    private long longitude;
    private long latitude;

    public Store() {
    }

    public Store(long remoteId, String name, String description, String imgUrl,
                 long longitude, long latitude) {
        this.remoteId = remoteId;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }
}