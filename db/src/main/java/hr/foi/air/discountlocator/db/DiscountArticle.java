package hr.foi.air.discountlocator.db;

/**
 * Entity class representing a discountArticle item. 
 * Each discountArticle connects one discount with one article.
 * @see Discount
 * @see Article
 */
public class DiscountArticle {
    private long articleId;
    private long discountId;

    public DiscountArticle() {
    }

    public DiscountArticle(long articleId, long discountId) {
        this.articleId = articleId;
        this.discountId = discountId;
    }

    public long getArticleId() {
        return articleId;
    }

    public long getDiscountId() {
        return discountId;
    }
}