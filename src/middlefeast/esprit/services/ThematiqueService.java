/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.utils.MyConnection;

/**
 *
 * @author eyaba
 */
public class ThematiqueService {

    Connection cnx2;

    public ThematiqueService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public void ajouterThematique() {
        try {
            String requete = "INSERT INTO thematique(nom,image)" + "VALUES('CuisineFrancaise','imagetest')";
            //Statement pr les requetes statiques execute la requete et ramene la reponse
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete); //on invoque executeUpdate pr mettre a jour le contenu de la BD : insertion/modification/suppression
            System.out.println("Thematique ajoutée avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    

    public void ajouterThematique2(Thematique t) {
        try {
            String requete2 = "INSERT INTO thematique(nom,image)" + "VALUES(?,?)";
            //PreparedStatement pr les requetes dynamiques
            PreparedStatement pst = cnx2.prepareStatement(requete2);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getImage());
            pst.executeUpdate();
            System.out.println("Votre Thematique est ajoutée avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ObservableList<Thematique> afficherThematique() {

        ObservableList<Thematique> mylist  = FXCollections.observableArrayList();
        try {

            String req3 = "Select * from thematique";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Thematique t = new Thematique();
                t.setId(rs.getInt(1));
                t.setNom(rs.getString("nom"));
                t.setImage(rs.getString("Image"));
                
                mylist.add(t);
            }
        } catch (SQLException ex) {
            System.out.println("Affichage Thematique :");
        }
        return mylist;
    }
    public List<Thematique> rechercheThematique(String mc) {

        List<Thematique> mylist = new ArrayList<>();
        try {

            String req3 = "Select * from thematique where nom like ?";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setString(1, "%"+mc+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Thematique t = new Thematique();
                t.setId(rs.getInt(1));
                t.setNom(rs.getString("nom"));
                t.setImage(rs.getString("Image"));
                mylist.add(t);
            }
        } catch (SQLException ex) {
            System.out.println("Affichage Thematique :");
        }
        return mylist;
    }
    
  
       

           
    

    public void supprimer(int id) {
        try {
            String req4 = "DELETE FROM `thematique` WHERE id = ?" ;
            PreparedStatement ps = cnx2.prepareStatement(req4);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("thematique deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Thematique t) {
        try {
            String req = "UPDATE `thematique` SET `nom` = ?, `image` = ? WHERE `thematique`.`id` = ?" ;
            PreparedStatement ps = cnx2.prepareStatement(req);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getImage());
            ps.setInt(3,t.getId());
            ps.executeUpdate();
            ps.close();
            System.out.println("thematique updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public Thematique detailThematique(int id) {
        Thematique t = new Thematique();
        //System.out.println("detail thematisque");
        try {

            String req3 = "SELECT * FROM `thematique` WHERE `id`=?;";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t.setId(rs.getInt(1));
                t.setNom(rs.getString("nom"));
                t.setImage(rs.getString("Image"));
            }
            
            
        } catch (SQLException ex) {
            System.out.println("Erreur detail Thematique :"+ex.getMessage());
        }
        return t;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    

}
