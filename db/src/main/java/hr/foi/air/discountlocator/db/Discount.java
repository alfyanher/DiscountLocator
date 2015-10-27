package hr.foi.air.discountlocator.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Entity class representing a discount item.
 * Each discount is connected to one and only one store
 * and can have zero, one or more discounted articles.
 * @see Store
 * @see DiscountArticle
 * @see Article
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Discounts")
public class Discount extends Model{

    @JsonProperty("id")
    @Column(name = "remoteId")
    private long remoteId;

    @JsonProperty("name")
    @Column(name = "name", index = true)
    private String name;

    @JsonProperty("description")
    @Column(name = "description", index = true)
    private String description;

    @JsonProperty("storeId")
    @Column(name = "storeId")
    private long storeId;

    @JsonProperty("startDate")
    @Column(name = "startDate")
    private Date startDate;

    @JsonProperty("endDate")
    @Column(name = "endDate")
    private Date endDate;

    @JsonProperty("discountValue")
    @Column(name = "discountValue")
    private int discountValue;

    @Column(name = "store")
    private Store store;

    public Discount() {
        super();
    }

    public Discount(long remoteId, String name, String description, long storeId,
                    Date startDate, Date endDate, int discountValue) {
        super();
        this.remoteId = remoteId;
        this.name = name;
        this.description = description;
        this.storeId = storeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountValue = discountValue;
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

    public long getStoreId() {
        return storeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public Store getStore() {
        return store;
    }

    /**
     * Method changes data in current object and updates it in database as well.
     * @param updatedDiscount An instance of object with updated data.
     */
    public void updateDiscount(Discount updatedDiscount)
    {
        this.name = updatedDiscount.getName();
        this.description = updatedDiscount.getDescription();
        this.startDate = updatedDiscount.getStartDate();
        this.endDate = updatedDiscount.getEndDate();
        this.discountValue = updatedDiscount.getDiscountValue();
        this.save();
    }
}

//Note:
// - You can add update method that will receive one or more attributes that should be changed.
// - You can add delete method, that will delete this object from database. But, when using it, be careful to remote the object from data layer and ui layer as well.
// - Try to experiment with active android and create methods you wish/need.