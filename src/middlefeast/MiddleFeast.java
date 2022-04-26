/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package middlefeast;

import Entities.Commande;
import Entities.Panier;
import Entities.Tutorial;
import Entities.User;
import Services.CommandeService;
import Services.PanierService;
import Services.PdfCommande;
import Tools.Smsapi;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author MSI
 */
public class MiddleFeast {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        //*******************************  commande + panier  ********************* //
        CommandeService cs = new CommandeService();
        PanierService ps = new PanierService();
        PdfCommande pc = new PdfCommande();
        Date date = new Date(System.currentTimeMillis());
        Tutorial t = new Tutorial("hello world", 10);
       // Commande c = new Commande(5, 12, date, "not paid");
        Panier p = new Panier(1, 1, 10, 10 * t.getPrix());
        //******************************* Ajout commande ********************* //
        /*  Commande c = new Commande(1, 12, date, "not paid");
        cs.ajouterCommande(c);
        Smsapi.sendSMS("hello");*/

        //******************************* Modifier commande ********************* //
        /* Commande c = new Commande(5,1, 133, date, "Paid");
        cs.modifierCommande(c);*/
        //******************************* Afficher commande ********************* //
        /* System.out.println("************* Affichage Commande ***************");
        System.out.println(cs.affichageCommandes());*/
        //******************************* Suppression commande ********************* //
        /*System.out.println("************* Suppression Commande ***************");
        cs.supprimerCommande(c);*/
  //******************************* trier selon date commande ********************* //
       /*  System.out.println("************* tri date Commande ***************");
        System.out.println(cs.sortByDate());*/
  //******************************* trier selon etat commande ********************* //
       /*  System.out.println("************* tri user Commande ***************");
        System.out.println(cs.sortByUser());*/
  //******************************* trier selon total commande ********************* //
        /* System.out.println("************* tri total Commande ***************");
        System.out.println(cs.sortByTotal());*/
        //******************************* Panier ********************* //
        //******************************* Ajout Panier ********************* //
        // ps.ajouterPanier(p);
        //******************************* Modifier Panier ********************* //
        /*Panier pm = new Panier(5,1, 1, 122, 22);
        ps.modifierPanier(pm);*/
        //******************************* Supprimer Panier ********************* //
        /*Panier pm = new Panier(5,1, 1, 122, 22);
        ps.supprimerPanier(pm);*/
        //******************************* Afficher Panier ********************* //
        //System.out.println(ps.affichagePanier());
        //******************************* Afficher Panier ********************* //
     //   System.out.println(ps.getPanier(1));
        //******************************* Somme Panier ********************* //
        // System.out.println(ps.sommePanier(1));
        //******************************* pdf Commande********************* //
      //  pc.liste_CommandePDF();
    }

}
