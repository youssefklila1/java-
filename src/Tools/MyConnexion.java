/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class MyConnexion {
    
    private final String URL="jdbc:mysql://127.0.0.1:3306/middlefeast4" ;
    private final String USER="root";
    private final String PWD="";
    
    private static Connection cnx;
    private static MyConnexion instance;

    private MyConnexion() {
        
        try {
            cnx=DriverManager.getConnection(URL,USER,PWD);
            System.out.println("connexion etablie avec succes");
        } catch (SQLException ex) {
            Logger.getLogger(MyConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static MyConnexion getInstance(){
        if (instance==null){
            instance=new MyConnexion();
        }else{
          //  System.out.println("deja connecte");
        }
        return instance;
    }
    public Connection getCnx() {
        return cnx;
    }

    
    
    
}
