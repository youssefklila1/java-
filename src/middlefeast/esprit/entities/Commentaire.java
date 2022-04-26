/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.entities;

/**
 *
 * @author eyaba
 */
public class Commentaire {
    int id;
    Discussion discussion; 
    //String date_creation_com;
    String description;
    int parent; 
    int nblike; 

    public Commentaire() {
    }

    public Commentaire(int id, Discussion discussion, String description, int parent, int nblike) {
        this.id = id;
        this.discussion = discussion;
        //this.date_creation_com = date_creation_com;
        this.description = description;
        this.parent = parent;
        this.nblike = nblike;
    }
    public Commentaire(Discussion discussion, String description) {
        this.discussion = discussion;
        //this.date_creation_com = date_creation_com;
        this.description = description;
        this.parent = 0;
        this.nblike = 0;
    }
    public Commentaire(Discussion discussion, String description, int parent) {
        this.discussion = discussion;
        //this.date_creation_com = date_creation_com;
        this.description = description;
        this.parent = parent;
        this.nblike = 0;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }
    /*
    public String getDate_creation_com() {
        return date_creation_com;
    }

    public void setDate_creation_com(String date_creation_com) {
        this.date_creation_com = date_creation_com;
    }*/

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

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id+ ", discussion_id=" + discussion.getId() + ", discussion=" + discussion.getTitre() +  ", description=" + description + ", parent=" + parent + ", nblike=" + nblike + '}';
    }
    
    
    
    
}
