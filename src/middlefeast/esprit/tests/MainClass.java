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
public class MainClass {

    public static void main(String[] args) {
        MyConnection mc = MyConnection.getInstance();
        MyConnection mc2 = MyConnection.getInstance();
        System.out.println(mc.hashCode() + "-" + mc2.hashCode());

        ThematiqueService ts = new ThematiqueService();
        System.out.println("debut detail thematisque");
        Thematique t = ts.detailThematique(32);
        System.out.println(t);
        System.out.println("fin detail thematisque");
        //Ajout****************************************************
        System.out.println("debut ajout thematisque");
        Thematique t3=new Thematique("Ramadan them ","RamadanImage");
        ts.ajouterThematique2(t3);
        System.out.println("fin ajout thematisque");
        //***************************************************************
        System.out.println("debut delete thematisque");
        ts.supprimer(30);
        System.out.println("fin delete thematisque");
        //****************************************************************
        //modification
        System.out.println("debut modif thematisque");
        Thematique t30 =ts.detailThematique(26);
        System.out.println(ts.detailThematique(26));
        t30.setNom("Mieux Manger 2");
        t30.setImage("MieuxManger2");
        ts.modifier(t30);
        System.out.println(ts.detailThematique(26));
        System.out.println("fin modif thematisque");
        //Affichage*****************************************
        System.out.println("affichage thematisque");
        System.out.println(ts.afficherThematique());
        // recherche thematique
        System.out.println("recherche thematisque");
        System.out.println(ts.rechercheThematique("Ramadan"));
        /*DiscussionService ds = new DiscussionService();
   Discussion d2=new Discussion("Mieux Manger","Salade César");
   ds.ajouterDiscussion2(d2);*/
    }
}
