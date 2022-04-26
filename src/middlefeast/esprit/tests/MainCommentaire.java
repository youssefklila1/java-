/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.tests;

import middlefeast.esprit.entities.Commentaire;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.services.CommentaireService;
import middlefeast.esprit.services.DiscussionService;
import middlefeast.esprit.utils.MyConnection;

/**
 *
 * @author eyaba
 */
public class MainCommentaire {
     public static void main(String[] args) {
        MyConnection mc = MyConnection.getInstance();
        
        DiscussionService ds = new DiscussionService();
        CommentaireService cs = new CommentaireService();
        System.out.println("debut detail commentaire");
        Commentaire c = cs.detailCommentaire(85);
        System.out.println(c);
        System.out.println("fin detail commentaire");
        //Ajout****************************************************
        System.out.println("debut ajout commentaire");
        Discussion d3=ds.detailDiscussion(18);
        
        Commentaire c2 = new Commentaire(d3,"New comment");
        cs.ajouterCommentaire2(c2);
        System.out.println("fin ajout commentaire");
        System.out.println("debut ajout commentaire avec badwords ");
        Commentaire c5 = new Commentaire(d3,"tuer un agent avec sang");
        cs.ajouterCommentaire2(c5);
        System.out.println("fin ajout commentaire avec badwords");
        //***************************************************************
        System.out.println("debut delete commentaire");
        cs.supprimer(93);
        System.out.println("fin delete commentaire");
        //****************************************************************
        //modification
        System.out.println("debut modif commentaire");
        Commentaire c89=cs.detailCommentaire(89);
        System.out.println(c89);
        
        c89.setDescription("Couscous Tunisien très bon");
        int nblike = c89.getNblike();
        nblike +=1;
        c89.setNblike(nblike);
        cs.modifier(c89);
        System.out.println(cs.detailCommentaire(89));
        System.out.println("fin modif commentaire");
        
        //répondre commentaire
        System.out.println("debut répondre commentaire");
        
        Commentaire c3 = new Commentaire(d3,"Reponse au commentaire 89");
        cs.repondreCommentaire(c3, 89);
        System.out.println("fin ajout commentaire");
        
        //Affichage*****************************************
        System.out.println("Affichage liste");
        System.out.println(cs.afficherCommentaire());
        System.out.println("Affichage Commentaire par discussion 18");
        System.out.println(cs.afficherCommentaireByDiscussion(18));
        //recherche commentaire
        System.out.println("Recherche commentaire par descriptio, ");
        System.out.println(cs.rechercheCommentaire("bon"));
     }
}
