/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author HP
 */
public class Livre {
    private int id;
    private String nom;
    private String description;
     private String auteur;
    private int nombredepage;
      private String nom_image;
       private int id_category;

    public Livre(int id, String nom, String description, String auteur, int nombredepage, String nom_image, int id_category) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.auteur = auteur;
        this.nombredepage = nombredepage;
        this.nom_image = nom_image;
        this.id_category = id_category;
    }

    public Livre() {
    }

    public Livre(String nom, String description, String auteur, int nombredepage, String nom_image, int id_category) {
        this.nom = nom;
        this.description = description;
        this.auteur = auteur;
        this.nombredepage = nombredepage;
        this.nom_image = nom_image;
        this.id_category = id_category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getNombredepage() {
        return nombredepage;
    }

    public void setNombredepage(int nombredepage) {
        this.nombredepage = nombredepage;
    }

    public String getNom_image() {
        return nom_image;
    }

    public void setNom_image(String nom_image) {
        this.nom_image = nom_image;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
   
}
