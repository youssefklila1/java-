package middlefeastdes.service;

import middlefeastdes.entity.Article;
import middlefeastdes.entity.User;
import middlefeastdes.entity.WishlistArticle;
import middlefeastdes.service_interface.IService;
import middlefeastdes.utils.Database;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class WishlistArticleService implements IService<WishlistArticle> {

    private Connection con;
    private Statement ste;

    public WishlistArticleService(){
        con = Database.getInstance().getConnection();
    }

    @Override
    public void add(WishlistArticle wishlistArticle) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "INSERT INTO  `userfavoris` " +
                                "(`article_id`, `user_id`) " +
                                "VALUES (?, ?)"
                );
        pre.setInt(1, wishlistArticle.getArticle_id());
        pre.setInt(2, wishlistArticle.getUser_id());
        pre.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "DELETE FROM `userfavoris` WHERE id = ?"
                );
        pre.setInt(1, id);
        pre.executeUpdate();
    }

    @Override
    public void update(WishlistArticle wishlistArticle) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "UPDATE `userfavoris` SET " +
                                "article_id = ?, " +
                                "user_id = ?, " +
                                "picture = ?, " +
                                "date = ?, " +
                                "recette = ?, " +
                                "vues = ? WHERE id = ?"
                );
        pre.setInt(1, wishlistArticle.getArticle_id());
        pre.setInt(2, wishlistArticle.getUser_id());
        pre.setInt(3, wishlistArticle.getId());
        pre.executeUpdate();
    }

    @Override
    public List<WishlistArticle> findAll() throws SQLException {
        List<WishlistArticle> wishlistArticleList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM userfavoris");
        while (rs.next()){
            WishlistArticle wishlistArticle = new WishlistArticle(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3)
            );
            wishlistArticleList.add(wishlistArticle);
        }
        return wishlistArticleList;
    }

    public List<Article> findByUser(User user) throws SQLException {
        List<Article> articleList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM userfavoris INNER JOIN article ON userfavoris.article_id = article.id WHERE user_id = "+user.getId());
        while (rs.next()){
            Article article = new Article(
                    rs.getInt(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    (Date) rs.getObject(8),
                    rs.getString(9),
                    rs.getInt(10),
                    rs.getInt(1)
                    );
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public WishlistArticle findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<WishlistArticle> searchBy(String column, String query) throws SQLException {
        return null;
    }

    @Override
    public List<WishlistArticle> sortBy(String column, boolean descending) throws SQLException {
        return null;
    }
}
