package middlefeastdes.service;

import middlefeastdes.entity.Article;
import middlefeastdes.entity.Course;
import middlefeastdes.service_interface.IService;
import middlefeastdes.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleService implements IService<Article> {

    private Connection con;
    private Statement ste;

    public ArticleService(){
        con = Database.getInstance().getConnection();
    }

    @Override
    public void add(Article article) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "INSERT INTO  `article` " +
                                "(`name`, `description`, `picture`, `date`, `recette`, `vues`) " +
                                "VALUES (?, ?, ?, ?, ?, ?)"
                );
        pre.setString(1, article.getName());
        pre.setString(2, article.getDescription());
        pre.setString(3, article.getPicture());
        pre.setObject(4, article.getDate());
        pre.setString(5, article.getRecette());
        pre.setInt(6, article.getVues());
        pre.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "DELETE FROM `article` WHERE id = ?"
                );
        pre.setInt(1, id);
        pre.executeUpdate();
    }

    @Override
    public void update(Article article) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "UPDATE `article` SET " +
                                "name = ?, " +
                                "description = ?, " +
                                "picture = ?, " +
                                "date = ?, " +
                                "recette = ?, " +
                                "vues = ? WHERE id = ?"
                );
        pre.setString(1, article.getName());
        pre.setString(2, article.getDescription());
        pre.setString(3, article.getPicture());
        pre.setObject(4, article.getDate());
        pre.setString(5, article.getRecette());
        pre.setInt(6, article.getVues());
        pre.setInt(7, article.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Article> findAll() throws SQLException {
        List<Article> articleList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM article");
        while (rs.next()){
            Article article = new Article(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    (Date) rs.getObject(5),
                    rs.getString(6),
                    rs.getInt(7)
            );
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public Article findById(int id) throws SQLException {
        Article article = new Article();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM article WHERE id = "+id);
        while (rs.next()){
            article.setId(rs.getInt(1));
            article.setName(rs.getString(2));
            article.setDescription(rs.getString(3));
            article.setPicture(rs.getString(4));
            article.setDate((Date) rs.getObject(5));
            article.setRecette(rs.getString(6));
            article.setVues(rs.getInt(7));
        }
        // Add +1 to views
        addOneView(article);
        return article;
    }

    private void addOneView(Article article) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "UPDATE `article` SET " +
                                "vues = ? WHERE id = ?"
                );
        int views = article.getVues()+1;
        pre.setInt(1, views);
        pre.setInt(2, article.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Article> searchBy(String column, String query) throws SQLException {
        List<Article> articleList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM article WHERE "+column+" LIKE '%"+query+"%'");
        while (rs.next()){
            Article article = new Article(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    (Date) rs.getObject(5),
                    rs.getString(6),
                    rs.getInt(7)
            );
            articleList.add(article);
        }
        return articleList;

    }

    @Override
    public List<Article> sortBy(String column, boolean descending) throws SQLException {
        List<Article> articleList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = descending ?
                ste.executeQuery("SELECT * FROM article ORDER BY "+column+" DESC") :
                ste.executeQuery("SELECT * FROM article ORDER BY "+column+" ASC");
        while (rs.next()){
            Article article = new Article(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    (Date) rs.getObject(5),
                    rs.getString(6),
                    rs.getInt(7)
            );
            articleList.add(article);
        }
        return articleList;
    }
}
