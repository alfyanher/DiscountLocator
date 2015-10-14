package hr.foi.air.discountlocator.db;

import java.util.Date;

/**
 * Entity class representing a discount item.
 * Each discount is connected to one and only one store
 * and can have zero, one or more discounted articles.
 * @see Store
 * @see DiscountArticle
 * @see Article
 */
public class Discount {
    private long remoteId;
    private String name;
    private String description;
    private long storeId;
    private Date startDate;
    private Date endDate;
    private int discountValue;

    public Discount() {
    }

    public Discount(long remoteId, String name, String description, long storeId,
                    Date startDate, Date endDate, int discountValue) {
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

    public int getDiscount() {
        return discountValue;
    }
}