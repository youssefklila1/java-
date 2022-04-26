/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Commande;
import Tools.MyConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author 21627
 */
public class CommandeService {

    Connection cnx;

    public CommandeService() {
        cnx = MyConnexion.getInstance().getCnx();
    }

    public void ajouterCommande(Commande c) {
        try {
            String requete = "INSERT INTO commande   VALUES (?,?,?,?,?)";
            PreparedStatement pst = MyConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.setInt(2, c.getUser_id());
            pst.setDouble(3, c.getTotal());
            pst.setDate(4, c.getDate());
            pst.setString(5, c.getEtat());

            pst.executeUpdate();
            System.out.println("Votre commande est ajoutée!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Commande> affichageCommandes() {
        List<Commande> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM commande";
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet res = st.executeQuery(requete);

            while (res.next()) {
                Commande c = new Commande();
                c.setId(res.getInt(1));
                c.setUser_id(res.getInt(2));
                c.setTotal(res.getDouble(3));
                c.setDate(res.getDate(4));
                c.setEtat(res.getString(5));

                myList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public void supprimerCommande(int c) {
        try {
            String req = "DELETE FROM Commande where id=" + c;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commande supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierCommande(Commande c) {

        try {
            System.out.println("1");

            PreparedStatement pst;
            pst = cnx.prepareStatement("UPDATE `commande` SET `user_id`=?,`total`=? ,`date`=?,`etat`=? WHERE id=?");
            System.out.println("2");
            pst.setInt(1, c.getUser_id());
            pst.setDouble(2, c.getTotal());
            pst.setDate(3, c.getDate());
            pst.setString(4, c.getEtat());
            pst.setInt(5, c.getId());

            if (pst.executeUpdate() == 1) {
                System.out.println("commande modifier avec success");
            } else {
                System.out.println("commande n'existe pas");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void modifierCommandetotal(double c, int id) {

        try {
            System.out.println("1");

            PreparedStatement pst;
            pst = cnx.prepareStatement("UPDATE `commande` SET `total`=? WHERE id=?");
            System.out.println("2");
            pst.setDouble(1, c);
     
            pst.setInt(2, id);

            if (pst.executeUpdate() == 1) {
                System.out.println("commande modifier avec success");
            } else {
                System.out.println("commande n'existe pas");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void modifierCommandeetat(String c, int id) {

        try {
            System.out.println("1");

            PreparedStatement pst;
            pst = cnx.prepareStatement("UPDATE `commande` SET `etat`=? WHERE id=?");
            System.out.println("2");
            pst.setString(1, c);
     
            pst.setInt(2, id);

            if (pst.executeUpdate() == 1) {
                System.out.println("commande modifier avec success");
            } else {
                System.out.println("commande n'existe pas");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   public List<Commande> sortByDate() {
        List<Commande> Commandes = affichageCommandes();
        List<Commande> resultat = Commandes.stream().sorted(Comparator.comparing(Commande::getDate)).collect(Collectors.toList());
        return resultat;

    }

    public List<Commande> sortByUser() {
        List<Commande> Commandes = affichageCommandes();
        List<Commande> resultat = Commandes.stream().sorted(Comparator.comparing(Commande::getUser_id)).collect(Collectors.toList());
        return resultat;
    }

    public List<Commande> sortByTotal() {
        List<Commande> Commandes = affichageCommandes();
        List<Commande> resultat = Commandes.stream().sorted(Comparator.comparing(Commande::getTotal)).collect(Collectors.toList());
        return resultat;
    }
      public double countCommande()
  {
      double prix_total=0;
      String req="Select count(*) from commande";
      
      
      try {
      Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                    prix_total=res.getDouble(1);
            }
            }
         catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

  return prix_total;
  }
}
