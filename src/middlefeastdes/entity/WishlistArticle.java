package middlefeastdes.entity;

public class WishlistArticle {
    private int id;
    private int user_id;
    private int article_id;

    public WishlistArticle() {
    }

    public WishlistArticle(int id, int user_id, int article_id) {
        this.id = id;
        this.user_id = user_id;
        this.article_id = article_id;
    }

    public WishlistArticle(int user_id, int article_id) {
        this.user_id = user_id;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    @Override
    public String toString() {
        return "Wishlist{" + "id=" + id + ", user_id=" + user_id + ", article_id=" + article_id + '}';
    }
}
