/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;



import Entities.Panier;
import Tools.MyConnexion;
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

/**
 *
 * @author 21627
 */
public class PanierService {
    
    Connection cnx;

    public PanierService() {
        cnx = MyConnexion.getInstance().getCnx();
    }
    
    ArrayList<Panier> listeProduits = new ArrayList<>();
    private float totalPrice;
    public void ajouterPanier(Panier p) {
    try {
            String requete = "INSERT INTO panier (qte,total,tutorial_id, user_id) VALUES (?,?,?,?)";
            PreparedStatement pst =MyConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, p.getQte());
            pst.setDouble(2, p.getTotal());
            pst.setInt(3, p.getTutorial_id());
            pst.setInt(4,p.getUser_id());
          
            pst.executeUpdate();
            System.out.println("Votre ligne de commande est ajoutée dans le panier!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
     }
    
    public void modifierPanier(Panier p) {
        try {
            String req = "UPDATE panier SET qte=" +p.getQte()+", total="+p.getTotal()+",tutorial_id="+p.getTutorial_id()+",user_id="+p.getUser_id()+" WHERE id=" + p.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("votre panier est modifée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
     public void supprimerPanier(int  p) {
        try {
            String req = "DELETE FROM panier where id=" + p;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("un article est supprimée du panier !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
     public void supprimerPanierbyuser(int  p) {
        try {
            String req = "DELETE FROM panier where user_id=" + p;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("un article est supprimée du panier !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
     

 public List<Panier> affichagePanier() {
        List<Panier> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM panier;";
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet res = st.executeQuery(requete);

            while (res.next()) {
                Panier p = new Panier();
                p.setId(res.getInt(1));
                p.setQte(res.getInt(5));
                
                p.setTotal(res.getDouble(6));
                p.setTutorial_id(res.getInt(3));
                p.setUser_id(res.getInt(4));

                
                  myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
 }
 

  public ObservableList<Panier> getPanier(int user_id) {
        ObservableList<Panier> myList = FXCollections.observableArrayList();
        try {
            String requete = "SELECT * FROM panier where user_id="+user_id;
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet res = st.executeQuery(requete);

            while (res.next()) {
                Panier p = new Panier();
            p.setId(res.getInt(1));
                p.setQte(res.getInt(5));
                
                p.setTotal(res.getDouble(6));
                p.setTutorial_id(res.getInt(3));
                p.setUser_id(res.getInt(4));

                
                  myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
 }
  
  public double sommePanier(int user_id)
  {
      double prix_total=0;
      String req="Select sum(total) from panier where user_id="+user_id;
      
      
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
  double consulterMontantPanier() {
        double total = 0.0;
        if (listeProduits.size() > 0) {
            for (int i = 0; i < this.listeProduits.size(); i++) {
                total = total + (this.listeProduits.get(i).getTotal()*this.listeProduits.get(i).getQte());
            }
        }
        return total;
    }
      public void modifierPanierqte(int c, int id,int prix) {

        try {
            System.out.println("1");

            PreparedStatement pst;
            pst = cnx.prepareStatement("UPDATE `panier` SET `qte`=?,`total`=?  WHERE id=?");
            System.out.println("2");
            pst.setInt(1, c);
            pst.setInt(2, prix);
     
            pst.setInt(3, id);

            if (pst.executeUpdate() == 1) {
                System.out.println("panier modifier avec success");
            } else {
                System.out.println("panier n'existe pas");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
 
}
