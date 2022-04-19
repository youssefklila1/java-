package middlefeastdes.service;

import middlefeastdes.entity.Course;
import middlefeastdes.entity.Tutorial;
import middlefeastdes.service_interface.IService;
import middlefeastdes.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TutorialService implements IService<Tutorial> {

    private Connection con;
    private Statement ste;

    public TutorialService() {
        con = Database.getInstance().getConnection();
    }

    @Override
    public void add(Tutorial tutorial) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "INSERT INTO  `tutorial` " +
                                "(`video`, `image`, `date_tuto`, `category`, `titre`, `description`, `prix`) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
        pre.setString(1, tutorial.getVideo());
        pre.setString(2, tutorial.getImage());
        pre.setObject(3, tutorial.getDateTuto());
        pre.setString(4, tutorial.getCategory());
        pre.setString(5, tutorial.getTitre());
        pre.setString(6, tutorial.getDescription());
        pre.setDouble(7, tutorial.getPrix());
        pre.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "DELETE FROM `tutorial` WHERE id = ?"
                );
        pre.setInt(1, id);
        pre.executeUpdate();
    }

    @Override
    public void update(Tutorial tutorial) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "UPDATE `tutorial` SET " +
                                "video = ?, " +
                                "image = ?, " +
                                "date_tuto = ?, " +
                                "category = ?, " +
                                "titre = ?, " +
                                "description = ?, " +
                                "prix = ? WHERE id = ?"
                );
        pre.setString(1, tutorial.getVideo());
        pre.setString(2, tutorial.getImage());
        pre.setObject(3, tutorial.getDateTuto());
        pre.setString(4, tutorial.getCategory());
        pre.setString(5, tutorial.getTitre());
        pre.setString(6, tutorial.getDescription());
        pre.setDouble(7, tutorial.getPrix());
        pre.setInt(8, tutorial.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Tutorial> findAll() throws SQLException {
        List<Tutorial> tutorialList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM tutorial");
        while (rs.next()){
            Tutorial tutorial = new Tutorial(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    (Date) rs.getObject(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getDouble(8)
            );
            tutorialList.add(tutorial);
        }
        return tutorialList;
    }

    @Override
    public Tutorial findById(int id) throws SQLException {
        Tutorial tutorial = new Tutorial();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM tutorial WHERE id = "+id);
        while (rs.next()){
            tutorial.setId(rs.getInt(1));
            tutorial.setVideo(rs.getString(2));
            tutorial.setImage(rs.getString(3));
            tutorial.setDateTuto((Date) rs.getObject(4));
            tutorial.setCategory(rs.getString(5));
            tutorial.setTitre(rs.getString(6));
            tutorial.setDescription(rs.getString(7));
            tutorial.setPrix(8);
        }
        return tutorial;
    }

    @Override
    public List<Tutorial> searchBy(String column, String query) throws SQLException {
        List<Tutorial> tutorialList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM tutorial WHERE "+column+" LIKE '%"+query+"%'");
        while (rs.next()){
            Tutorial tutorial = new Tutorial(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    (Date) rs.getObject(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getDouble(8)
            );
            tutorialList.add(tutorial);
        }
        return tutorialList;
    }

    @Override
    public List<Tutorial> sortBy(String column, boolean descending) throws SQLException {
        List<Tutorial> tutorialList = new ArrayList<>();
        ste = con.createStatement();

        ResultSet rs = descending ?
                ste.executeQuery("SELECT * FROM tutorial ORDER BY "+column+" DESC") :
                ste.executeQuery("SELECT * FROM tutorial ORDER BY "+column+" ASC");

        while (rs.next()){
            Tutorial tutorial = new Tutorial(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    (Date) rs.getObject(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getDouble(8)
            );
            tutorialList.add(tutorial);
        }
        return tutorialList;
    }
}
