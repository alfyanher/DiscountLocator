package hr.foi.air.discountlocator.db;

/**
 * Entity class representing a storeCategory item. 
 * Each storeCategory connects one store with one category.
 * @see Store
 * @see Category
 */
public class StoreCategory {
    private long storeId;
    private long categoryId;

    public StoreCategory() {
    }

    public StoreCategory(long storeId, long categoryId) {
        this.storeId = storeId;
        this.categoryId = categoryId;
    }

    public long getStoreId() {
        return storeId;
    }

    public long getCategoryId() {
        return categoryId;
    }
}