package hr.foi.air.discountlocator.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Entity class representing a store item. 
 * Each store can have one or more discounts and is connected to one or more categories.
 * @see Discount
 * @see Category
 * @see StoreCategory
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Stores")
public class Store extends Model {

    @JsonProperty("id")
    @Column(name = "remoteId")
    private long remoteId;

    @JsonProperty("name")
    @Column(name = "name", index = true)
    private String name;

    @JsonProperty("description")
    @Column(name = "description", index = true)
    private String description;

    @JsonProperty("imgUrl")
    @Column(name = "imgUrl")
    private String imgUrl;

    @JsonProperty("longitude")
    @Column(name = "longitude")
    private long longitude;

    @JsonProperty("latitude")
    @Column(name = "latitude")
    private long latitude;

    public Store() {
        super();
    }

    public Store(long remoteId, String name, String description, String imgUrl,
                 long longitude, long latitude) {
        super();
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

    /**
     * Using an existing relationship, we can easily get list of instances in related Model class.
     * @return List of discounts for this Store.
     */
    public List<Discount> discounts(){
        return getMany(Discount.class, "Store");
    }

    /**
     * Method changes data in current object and updates it in database as well.
     * @param updatedStore An instance of object with updated data.
     */
    public void updateStore(Store updatedStore)
    {
        this.name = updatedStore.getName();
        this.description = updatedStore.getDescription();
        this.imgUrl = updatedStore.getImgUrl();
        this.longitude = updatedStore.getLongitude();
        this.latitude = updatedStore.getLatitude();
        this.save();
    }
}

//Note:
// - You can add update method that will receive one or more attributes that should be changed.
// - You can add delete method, that will delete this object from database. But, when using it, be careful to remote the object from data layer and ui layer as well.
// - Try to experiment with active android and create methods you wish/need.