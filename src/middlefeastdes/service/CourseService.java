package middlefeastdes.service;

import middlefeastdes.entity.Course;
import middlefeastdes.service_interface.IService;
import middlefeastdes.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseService implements IService<Course> {

    private Connection con;
    private Statement ste;

    public CourseService() {
        con = Database.getInstance().getConnection();
    }

    @Override
    public void add(Course course) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "INSERT INTO  `formation` " +
                                "(`price`, `mode`, `date_debut`, `date_fin`, `duree`, `description`) " +
                                "VALUES (?, ?, ?, ?, ?, ?)"
                );
        pre.setInt(1, course.getPrice());
        pre.setString(2, course.getMode());
        pre.setObject(3, course.getDateDebut());
        pre.setObject(4, course.getDateFin());
        pre.setString(5, course.getDuree());
        pre.setString(6, course.getDescription());
        pre.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                "DELETE FROM `formation` WHERE id = ?"
        );
        pre.setInt(1, id);
        pre.executeUpdate();
    }

    @Override
    public void update(Course course) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                  "UPDATE `formation` SET " +
                          "price = ?, " +
                          "mode = ?, " +
                          "date_debut = ?, " +
                          "date_fin = ?, " +
                          "duree = ?, " +
                          "description = ? WHERE id = ?"
                );
        pre.setInt(1, course.getPrice());
        pre.setString(2, course.getMode());
        pre.setObject(3, course.getDateDebut());
        pre.setObject(4, course.getDateFin());
        pre.setString(5, course.getDuree());
        pre.setString(6, course.getDescription());
        pre.setInt(7, course.getId());
        pre.executeUpdate();
    }

    @Override
    public List<Course> findAll() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM formation");
        while (rs.next()){
            Course course = new Course(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    (Date) rs.getObject(4),
                    (Date) rs.getObject(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public Course findById(int id) throws SQLException {
        Course course = new Course();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM formation WHERE id = "+id);
        while (rs.next()){
            course.setId(rs.getInt(1));
            course.setPrice(rs.getInt(2));
            course.setMode(rs.getString(3));
            course.setDateDebut((Date) rs.getObject(4));
            course.setDateFin((Date) rs.getObject(5));
            course.setDuree(rs.getString(6));
            course.setDescription(rs.getString(7));
        }
        return course;
    }

    @Override
    public List<Course> searchBy(String column, String query) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("SELECT * FROM formation WHERE "+column+" LIKE '%"+query+"%'");
        while (rs.next()){
            Course course = new Course(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    (Date) rs.getObject(4),
                    (Date) rs.getObject(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public List<Course> sortBy(String column, boolean descending) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        ste = con.createStatement();

        ResultSet rs = descending ?
                ste.executeQuery("SELECT * FROM formation ORDER BY "+column+" DESC") :
                ste.executeQuery("SELECT * FROM formation ORDER BY "+column+" ASC");
        while (rs.next()){
            Course course = new Course(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    (Date) rs.getObject(4),
                    (Date) rs.getObject(5),
                    rs.getString(6),
                    rs.getString(7)
            );
            courseList.add(course);
        }
        return courseList;
    }
}
