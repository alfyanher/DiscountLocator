package hr.foi.air.discountlocator.db;

/**
 * Entity class representing an article (an item in the store).
 * Each article can be connected to zero, one or more discounts.
 * @see Discount
 * @see DiscountArticle
 */
public class Article {
    private long remoteId;
    private String name;
    private double price;
    private String imgUrl;

    public Article() {
    }

    public Article(long remoteId, String name, double price, String imgUrl) {
        this.remoteId = remoteId;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
