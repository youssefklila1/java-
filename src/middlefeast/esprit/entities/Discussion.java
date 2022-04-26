/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.entities;

//import java.util.Date;

/**
 *
 * @author eyaba
 */
public class Discussion {
    int id; 
    Thematique thematique;
    //Date date_creation_disc;
    String titre; 
    int nb_comment=0;

    public int getNb_comment() {
        return nb_comment;
    }

    public void setNb_comment(int nb_comment) {
        this.nb_comment = nb_comment;
    }

    public Discussion() {
    }
    
    public Discussion(int id, Thematique thematique,  String titre) {
        this.id = id;
        this.thematique = thematique;
        //this.date_creation_disc = date_creation_disc;
        this.titre = titre;
    }

    public Discussion(Thematique thematique, String titre) {
        this.thematique = thematique;
        // this.date_creation_disc = date_creation_disc;
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thematique getThematique() {
        return thematique;
    }

    public void setThematique(Thematique thematique) {
        this.thematique = thematique;
    }
    /*
    public Date getDate_creation_disc() {
        return date_creation_disc;
    }

    public void setDate_creation_disc(Date date_creation_disc) {
        this.date_creation_disc = date_creation_disc;
    }*/

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Discussion{" + "id=" + id + ", thematique_id=" + thematique.getId() + ", thematique=" + thematique.getNom() + ", titre=" + titre + '}';
    }
    
    
}
