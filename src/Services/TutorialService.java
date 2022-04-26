/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Panier;
import Entities.Tutorial;
import Tools.MyConnexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MSI
 */
public class TutorialService {
        Connection cnx;

    public TutorialService() {
        cnx = MyConnexion.getInstance().getCnx();
    }
  public String getTutoriel(int user_id) {
        ObservableList<Tutorial> myList = FXCollections.observableArrayList();
        String titre="" ; 
        try {
            String requete = "SELECT * FROM tutorial where id="+user_id;
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet res = st.executeQuery(requete);

            while (res.next()) {
                Tutorial p = new Tutorial();
            p.setId(res.getInt(1));
              titre=res.getString(6);
              
                 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return titre;
 }
  public int getTutorielprix(int user_id) {
        ObservableList<Tutorial> myList = FXCollections.observableArrayList();
        int prix=0; 
        try {
            String requete = "SELECT * FROM tutorial where id="+user_id;
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet res = st.executeQuery(requete);

            while (res.next()) {
                Tutorial p = new Tutorial();
            p.setId(res.getInt(1));
              prix=res.getInt(8);
              
                 
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prix;
 }
}
