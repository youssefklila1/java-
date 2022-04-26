/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.services;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.javafx.font.FontFactory;
import static com.sun.javafx.util.Utils.contains;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.swing.text.Document;
import middlefeast.esprit.entities.Commentaire;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.utils.MyConnection;

/**
 *
 * @author eyaba
 */
public class CommentaireService {
    Connection cnx2;

    public CommentaireService() {
        this.cnx2=MyConnection.getInstance().getCnx();
    }
    public void ajouterCommentaire2(Commentaire c) {
        
        try {
             String requete = "INSERT INTO `commentaire`(`discussion_id`, `description`, `parent`, `nblike`) VALUES (?,?,?,0)";
            //PreparedStatement pr les requetes dynamiques
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, c.getDiscussion().getId());
            pst.setString(2, badwords(c.getDescription()));            
            pst.setInt(3,c.getParent());
            pst.executeUpdate();
            System.out.println("Votre Commentaire est ajoutée avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public List<Commentaire> afficherCommentaire() {

        List<Commentaire> mylist = new ArrayList<>();
        try {

            String req3 = "Select * from Commentaire INNER JOIN discussion ON discussion.id=Commentaire.discussion_id "
                    + "INNER JOIN thematique ON discussion.thematique_id=thematique.id ORDER BY commentaire.id";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                
                Commentaire c = new Commentaire();
                c.setId(rs.getInt(1));
                c.setDescription(rs.getString("description"));
                c.setParent(rs.getInt("parent"));
                c.setNblike(rs.getInt("nblike"));
                //pour créer un commentaire il faut l'objet discussion
                //pour créer la discussion il faut un objet thematique
                Thematique t = new Thematique(rs.getInt(11),rs.getString("nom"), rs.getString("image"));
                Discussion d = new Discussion();
                d.setId(rs.getInt(7));
                d.setTitre(rs.getString("titre"));
                d.setThematique(t);
                c.setDiscussion(d);
                
                mylist.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }
    public List<Commentaire> rechercheCommentaire(String mc) {

        List<Commentaire> mylist = new ArrayList<>();
        try {

            String req3 = "Select * from Commentaire INNER JOIN discussion ON discussion.id=Commentaire.discussion_id "
                    + "INNER JOIN thematique ON discussion.thematique_id=thematique.id where commentaire.description like ?";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setString(1, "%"+mc+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                
                Commentaire c = new Commentaire();
                c.setId(rs.getInt(1));
                c.setDescription(rs.getString("description"));
                
                //pour créer un commentaire il faut l'objet discussion
                //pour créer la discussion il faut un objet thematique
                Thematique t = new Thematique(rs.getInt(11),rs.getString("nom"), rs.getString("image"));
                Discussion d = new Discussion();
                d.setId(rs.getInt(7));
                d.setTitre(rs.getString("titre"));
                d.setThematique(t);
                c.setDiscussion(d);
                
                mylist.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }
    public List<Commentaire> afficherCommentaireByDiscussion(int iddiscussion) {

        List<Commentaire> mylist = new ArrayList<>();
        try {

            String req3 = "Select * from Commentaire INNER JOIN discussion ON discussion.id=Commentaire.discussion_id "
                    + "INNER JOIN thematique ON discussion.thematique_id=thematique.id where Commentaire.discussion_id=? ORDER BY commentaire.id";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setInt(1, iddiscussion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                
                
                Commentaire c = new Commentaire();
                c.setId(rs.getInt(1));
                c.setDescription(rs.getString("description"));
                c.setParent(rs.getInt("parent"));
                c.setNblike(rs.getInt("nblike"));
                //pour créer un commentaire il faut l'objet discussion
                //pour créer la discussion il faut un objet thematique
                Thematique t = new Thematique(rs.getInt(11),rs.getString("nom"), rs.getString("image"));
                Discussion d = new Discussion();
                d.setId(rs.getInt(7));
                d.setTitre(rs.getString("titre"));
                d.setThematique(t);
                c.setDiscussion(d);
                
                mylist.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mylist;
    }
    

    public void supprimer(int id) {
        try {
            String req4 = "DELETE FROM `Commentaire` WHERE id = ?" ;
            PreparedStatement ps = cnx2.prepareStatement(req4);
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Commentaire deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(Commentaire d) {
        try {
            String req = "UPDATE `commentaire` SET `discussion_id`=?,`description`=?,`parent`=?,`nblike`=? WHERE id=?" ;
            PreparedStatement ps = cnx2.prepareStatement(req);
            ps.setInt(1, d.getDiscussion().getId());
            ps.setString(2,badwords(d.getDescription()));
            ps.setInt(3,d.getParent());
            ps.setInt(4,d.getNblike());
            ps.setInt(5,d.getId());
            ps.executeUpdate();
            ps.close();
            System.out.println("Commentaire updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void like(int id) {
        try {
            String req = "UPDATE `commentaire` SET `nblike`=(nblike+1) WHERE id=?" ;
            PreparedStatement ps = cnx2.prepareStatement(req);
            ps.setInt(1, id);
            
            ps.executeUpdate();
            ps.close();
            System.out.println("Commentaire liked !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public Commentaire detailCommentaire(int id) {
        Commentaire c = new Commentaire();
        //System.out.println("detail thematisque");
        try {

            String req3 = "Select * from Commentaire INNER JOIN discussion ON discussion.id=Commentaire.discussion_id "
                    + "INNER JOIN thematique ON discussion.thematique_id=thematique.id WHERE Commentaire.`id`=?;";
            PreparedStatement ps = cnx2.prepareStatement(req3);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setId(rs.getInt(1));
                c.setDescription(rs.getString("description"));
                c.setNblike(rs.getInt("nblike"));
                c.setParent(rs.getInt("parent"));
                Thematique t = new Thematique(rs.getInt(11),rs.getString("nom"), rs.getString("image"));
                Discussion d = new Discussion();
                d.setId(rs.getInt(7));
                d.setTitre(rs.getString("titre"));
                
                d.setThematique(t);
                c.setDiscussion(d);
            }
            
            
        } catch (SQLException ex) {
            System.out.println("Erreur detail Commentaire :"+ex.getMessage());
        }
        return c;
    }
    //Mérier répondre 
    public void repondreCommentaire(Commentaire c,int idparent) {
        try {
             String requete = "INSERT INTO `commentaire`(`discussion_id`, `description`, `parent`, `nblike`) VALUES (?,?,?,0)";
            //PreparedStatement pr les requetes dynamiques
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, c.getDiscussion().getId());
            pst.setString(2, badwords(c.getDescription()));
            pst.setInt(3, idparent);
            pst.executeUpdate();
            System.out.println("Votre Réponse Commentaire est ajoutée avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    //Métier BadWords
    public String badwords(String comment){
        
        String result = comment;
        String[] badwords = {"tuer","sang","con","merde","vomir","conne","putain"};
        
        for(String word : badwords){
            //System.out.println(result);
            if(contains(result,word)){
                result=result.replace(word,"****");
                //break;
            }
            //System.out.println(result);
        }
        return result;
    }
    
    
    

}
