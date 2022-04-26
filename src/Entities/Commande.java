/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @author MSI
 */
public class Commande {
    int id  ;
int user_id ; 
double total  ;
Date date ; 
String etat ; 
public int userproperty(){
    return user_id ; 
}
    public Commande(int id, int user_id, double total, Date date, String etat) {
        this.id = id;
        this.user_id = user_id;
        this.total = total;
        this.date = date;
        this.etat = etat;
    }

    public Commande(int user_id, double total, Date date, String etat) {
        this.user_id = user_id;
        this.total = total;
        this.date = date;
        this.etat = etat;
    }

    public Commande() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }


    @Override
    public String toString() {
        return "Commande{" + "id=" + id+ ", user_id=" + user_id + ", total=" + total + ", date=" + date + ", etat=" + etat + '}'+"\n";
    }

}
