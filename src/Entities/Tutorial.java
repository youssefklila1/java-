/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author MSI
 */
public class Tutorial {

    int id;
    String titre;
int prix  ; 

    public Tutorial(int id, String titre) {
        this.id = id;
        this.titre = titre;
    }

    public Tutorial(String titre, int prix) {
        this.titre = titre;
        this.prix = prix;
    }

    public Tutorial() {
    }

 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

}
