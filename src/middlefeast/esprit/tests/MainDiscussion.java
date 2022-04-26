/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.tests;

import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.services.DiscussionService;
import middlefeast.esprit.services.ThematiqueService;
import middlefeast.esprit.utils.MyConnection;

/**
 *
 * @author eyaba
 */
public class MainDiscussion {
     public static void main(String[] args) {
        MyConnection mc = MyConnection.getInstance();
        
        DiscussionService ds = new DiscussionService();
        System.out.println("debut detail discussion");
        Discussion d = ds.detailDiscussion(15);
        System.out.println(d);
        System.out.println("fin detail discussion");
        //Ajout****************************************************
        System.out.println("debut ajout discussion");
        
        ThematiqueService ts = new ThematiqueService();
        Thematique t = ts.detailThematique(25);
        
        Discussion d3=new Discussion(t,"DiscussionAjout");
        ds.ajouterDiscussion2(d3);
        System.out.println("fin ajout discussion");
        //***************************************************************
        System.out.println("debut delete discussion");
        ds.supprimer(17);
        System.out.println("fin delete discussion");
        //****************************************************************
        //modification
        System.out.println("debut modif discussion");
        Discussion d18=ds.detailDiscussion(18);
        System.out.println(ds.detailDiscussion(18));
        d18.setTitre("Couscous Tunisien");
        d18.setThematique(ts.detailThematique(30));
        ds.modifier(d18);
        System.out.println(ds.detailDiscussion(18));
        System.out.println("fin modif discussion");
        //Affichage*****************************************
        System.out.println(ds.afficherDiscussion());
        //Affichage par theme métier
        System.out.println("Affichage discussion par thematique 25");
        System.out.println(ds.afficherDiscussionByTheme(25));
        //recherche discusiionr
        System.out.println("Recherche discussion par titre ");
        System.out.println(ds.rechercheDiscussion("cous"));
        //stat
        ds.statistique();
     }
}
