/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes.entity;

import java.util.Date;

public class Article {
    
    private int id;
    private String name;
    private String description;
    private String picture;
    private Date date;
    private String recette;
    private int vues;

    public Article() {
    }
    
    public Article(int id, String name, String description, String picture, Date date, String recette, int vues) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.date = date;
        this.recette = recette;
        this.vues = vues;
    }

    public Article(String name, String description, String picture, Date date, String recette, int vues) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.date = date;
        this.recette = recette;
        this.vues = vues;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRecette() {
        return recette;
    }

    public void setRecette(String recette) {
        this.recette = recette;
    }

    public int getVues() {
        return vues;
    }

    public void setVues(int vues) {
        this.vues = vues;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", name=" + name + ", description=" + description + ", picture=" + picture + ", date=" + date + ", recette=" + recette + ", vues=" + vues + '}';
    }
    
}
