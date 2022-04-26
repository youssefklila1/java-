/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.tableViewModel;

import middlefeast.esprit.entities.*;

/**
 *
 * @author eyaba
 */
public class CommentaireModel {
    int id;
    String discussion; 
    //String date_creation_com;
    String description;
    int parent; 
    int nblike; 

    public CommentaireModel() {
    }

    public CommentaireModel(int id, String discussion, String description, int parent, int nblike) {
        this.id = id;
        this.discussion = discussion;
        this.description = description;
        this.parent = parent;
        this.nblike = nblike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getNblike() {
        return nblike;
    }

    public void setNblike(int nblike) {
        this.nblike = nblike;
    }

   
    
}
