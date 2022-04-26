/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.activation.DataSource;

/**
 *
 * @author eyaba
 */
public class MyConnection {
    
    private Connection cnx;
    private static DataSource instance;
    
    private final String USER = "root";
    private final String PWD = "";
    private final String URL = "jdbc:mysql://localhost:3006/Middlefeast3";
    public static MyConnection instance2;

   private MyConnection() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connected !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    
}
    public Connection getCnx(){
        return cnx;
    }
    
    public static MyConnection getInstance(){
        if(instance2==null){
            instance2= new MyConnection();
        }
        return instance2;
}
}
