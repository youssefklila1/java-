/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author MSI
 */
public class Panier {
    int id ; 
int tutorial_id ; 
int user_id ; 
int qte ; 
double total ; 

    public Panier(int id, int tutorial_id, int user_id, int qte, double total) {
        this.id = id;
        this.tutorial_id = tutorial_id;
        this.user_id = user_id;
        this.qte = qte;
        this.total = total;
    }

    public Panier(int tutorial_id, int user_id, int qte, double total) {
        this.tutorial_id = tutorial_id;
        this.user_id = user_id;
        this.qte = qte;
        this.total = total;
    }

    public Panier() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTutorial_id() {
        return tutorial_id;
    }

    public void setTutorial_id(int tutorial_id) {
        this.tutorial_id = tutorial_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", tutorial_id=" + tutorial_id + ", total_panier=" + total + ", qte=" + qte + ", user_id=" + user_id + '}'+"\n";
    }


}
