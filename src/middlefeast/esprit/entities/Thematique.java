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
public class Thematique {
    private int id;
    private String nom;
    private String image;

    public Thematique() {
    }
   

    public Thematique(int id, String nom, String image) {
        this.id = id;
        this.nom = nom;
        this.image = image;
    }

    public Thematique(String nom, String image) {
        this.nom = nom;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Thematique{" + "id=" + id + ", nom=" + nom + ", image=" + image + '}';
    }
    
    
}
