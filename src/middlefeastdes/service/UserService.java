/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import middlefeastdes.entity.User;
import middlefeastdes.service_interface.IService;
import middlefeastdes.utils.Database;


public class UserService implements IService<User>{
    
     private Connection con;
    private Statement ste;

    public UserService() {
            con = Database.getInstance().getConnection();

    }
    
    

    @Override
    public void add(User t) throws SQLException {
        PreparedStatement pre =
                con.prepareStatement(
                        "INSERT INTO  `User` " +
                                "(`email`, `password`,`roles`, `firstname`, `lastname`, `is_verified`) " +
                                "VALUES ( ?, ?,'{}', ?, ?, ?)"
                );
        pre.setString(1, t.getEmail());
        pre.setString(2, t.getPassword());
        pre.setString(3, t.getFirstname());
        pre.setString(4, t.getLastname());
        pre.setBoolean(5, t.getIsVerified());
        pre.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        
         String request = "DELETE FROM user where id=?";
        try {
            PreparedStatement pst = con.prepareStatement(request);
            pst.setInt(1, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    @Override
    public void update(User t) throws SQLException {
        PreparedStatement pre = con.prepareStatement("UPDATE `user` SET `email` = ?, `password` = ?, `firstname` = ?, `lastname` = ? WHERE `id` = ?");
         pre.setString(1, t.getEmail());
        pre.setString(2, t.getPassword());
        pre.setString(3, t.getFirstname());
        pre.setString(4, t.getLastname());
        pre.setInt(5, t.getId());
        pre.executeUpdate();
    }

    @Override
    public List<User> findAll() throws SQLException {
         List<User> myList = new ArrayList<>();
        String req = "SELECT * FROM user";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                User e = new User();
                e.setId(rs.getInt(1));
                e.setEmail(rs.getString(2));
                e.setPassword(rs.getString(4));
                e.setLastname(rs.getString(6));
                e.setFirstname(rs.getString(5));
                e.setIsVerified(rs.getBoolean(7));
                myList.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myList;
    }

    @Override
    public User findById(int id) throws SQLException {
          User user = new User();
        String request = "SELECT * FROM user where id="+id;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                User e = new User();
                e.setId(rs.getInt(1));
                e.setEmail(rs.getString(2));
                e.setPassword(rs.getString(4));
                e.setLastname(rs.getString(6));
                e.setFirstname(rs.getString(5));
                e.setIsVerified(rs.getBoolean(7));
                user=e;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    @Override
    public List<User> searchBy(String column, String query) throws SQLException {
        return null;
    }

    @Override
    public List<User> sortBy(String column, boolean descending) {
        return null;
    }
    
}
