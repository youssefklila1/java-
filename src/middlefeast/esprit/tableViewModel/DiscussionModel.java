/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.tableViewModel;

//import java.util.Date;

import middlefeast.esprit.entities.*;
//import java.util.Date;

/**
 *
 * @author eyaba
 */
public class DiscussionModel {
    int id; 
    String thematique;
    //Date date_creation_disc;
    String titre; 

    public DiscussionModel() {
    }

    public DiscussionModel(int id, String thematique, String titre) {
        this.id = id;
        this.thematique = thematique;
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThematique() {
        return thematique;
    }

    public void setThematique(String thematique) {
        this.thematique = thematique;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    
    
}
