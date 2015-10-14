package hr.foi.air.discountlocator.db;

/**
 * Entity class representing a category item. 
 * Each category can be connected to zero, one or more stores.
 * @see Store
 * @see StoreCategory
 */
public class Category {
    private long remoteId;
    private String name;
    private long subcategoryOf;

    public Category() {
    }

    public Category(long remoteId, String name, long subcategoryOf) {
        this.remoteId = remoteId;
        this.name = name;
        this.subcategoryOf = subcategoryOf;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public String getName() {
        return name;
    }

    public long getSubcategoryOf() {
        return subcategoryOf;
    }
}