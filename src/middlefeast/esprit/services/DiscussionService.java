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
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.utils.MyConnection;

/**
 *
 * @author eyaba
 */
public class DiscussionService {
    Connection cnx2;
 public DiscussionService(){
    cnx2=MyConnection.getInstance().getCnx();
}
    public void ajouterDiscussion() {
        try {
            String requete = "INSERT INTO discussion(thematique,titre)" + "VALUES('CuisineFrancaise','Macaron')";
            //Statement pr les requetes statiques execute la requete et ramene la reponse
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete); //on invoque executeUpdate pr mettre a jour le contenu de la BD : insertion/modification/suppression
            System.out.println("Discussion ajoutée avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void ajouterDiscussion2(Discussion d) {
        try {
            String requete2 = "INSERT INTO `discussion`( `thematique_id`, `titre`) VALUES (?,?)";
            //PreparedStatement pr les requetes dynamiques
            PreparedStatement pst = cnx2.prepareStatement(requete2);
            pst.setInt(1, d.getThematique().getId());
            pst.setString(2, d.getTitre());
            pst.executeUpdate();
            System.out.println("Votre Discussion est ajoutée avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public List<Discussion> afficherDiscussion() {

        List<Discussion> mylist = new ArrayList<>();
        try {

            String req3 = "Select * from discussion INNER JOIN thematique ON thematique.id=discussion.thematique_id";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Discussion d = new Discussion();
                d.setId(rs.getInt(1));
                d.setTitre(rs.getString("titre"));
                //int thematique_id=rs.getInt("thematique_id");
                Thematique t = new Thematique(rs.getInt(5),rs.getString("nom"), rs.getString("image"));
                d.setThematique(t);
                mylist.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }
    /// les discussion avec plus de commentaires
    public List<Discussion> afficherDiscussionPlus() {

        List<Discussion> mylist = new ArrayList<>();
        try {

            String req3 = "Select   discussion.titre,count(*) as nb from commentaire INNER JOIN discussion ON discussion.id=commentaire.discussion_id group by discussion_id order by count(*) desc";
                    
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Discussion d = new Discussion();
                d.setNb_comment(rs.getInt("nb"));
                d.setTitre(rs.getString("titre"));
                
                mylist.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }
    public List<Discussion> rechercheDiscussion(String mc) {

        List<Discussion> mylist = new ArrayList<>();
        try {

            String req3 = "Select * from discussion INNER JOIN thematique ON thematique.id=discussion.thematique_id where discussion.titre like ?";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setString(1, "%"+mc+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Discussion d = new Discussion();
                d.setId(rs.getInt(1));
                d.setTitre(rs.getString("titre"));
                //int thematique_id=rs.getInt("thematique_id");
                Thematique t = new Thematique(rs.getInt(5),rs.getString("nom"), rs.getString("image"));
                d.setThematique(t);
                mylist.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }
    public List<Discussion> afficherDiscussionByTheme(int idthematique) {

        List<Discussion> mylist = new ArrayList<>();
        try {

            String req3 = "Select * from discussion INNER JOIN thematique ON thematique.id=discussion.thematique_id where discussion.thematique_id=?";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setInt(1,idthematique);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Discussion d = new Discussion();
                d.setId(rs.getInt(1));
                d.setTitre(rs.getString("titre"));
                //int thematique_id=rs.getInt("thematique_id");
                Thematique t = new Thematique(rs.getInt(5),rs.getString("nom"), rs.getString("image"));
                d.setThematique(t);
                mylist.add(d);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }

    public void supprimer(int id) {
        try {
            String req4 = "DELETE FROM `discussion` WHERE id = ?" ;
            PreparedStatement ps = cnx2.prepareStatement(req4);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("discussion deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Discussion d) {
        
        try {
            String req = "UPDATE `discussion` SET `thematique_id`=?,`titre`=? WHERE id=?" ;
            PreparedStatement ps = cnx2.prepareStatement(req);
            ps.setInt(1, d.getThematique().getId());
            ps.setString(2, d.getTitre());
            ps.setInt(3,d.getId());
            ps.executeUpdate();
            ps.close();
            System.out.println("discussion updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public Discussion detailDiscussion(int id) {
        Discussion d = new Discussion();
        //System.out.println("detail thematisque");
        try {

            String req3 = "SELECT * FROM `discussion` INNER JOIN thematique ON thematique.id=discussion.thematique_id WHERE discussion.`id`=?;";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                d.setId(rs.getInt("id"));
                d.setTitre(rs.getString("titre"));
                Thematique t = new Thematique(rs.getInt(5),rs.getString("nom"), rs.getString("image"));
                d.setThematique(t);
            }
            
            
        } catch (SQLException ex) {
            System.out.println("Erreur detail Discussion :"+ex.getMessage());
        }
        return d;
    }
    
    
    
    
    
    
        
    public void statistique(){
  
     try {
            
            String requete3="select count(*) , titre from 'discussion' join  thematique ON thematique.id=discussion.thematique_id group by titre";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
             ResultSet rs=st.executeQuery(requete3);
           
             while (rs.next()){
                 System.out.println("titre : " +rs.getString(2)+" thematique : "+rs.getInt(1));
            
             }
            
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
