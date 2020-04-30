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
public class category {

  
    private int id;
    private String libelle;
    private String description;
     private String nom_image;
    

    public category(int id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

    public String getNom_image() {
        return nom_image;
    }

    public void setNom_image(String nom_image) {
        this.nom_image = nom_image;
    }

    public category(int id, String libelle, String description, String nom_image) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.nom_image = nom_image;
    }

    public category() {
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public category(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }
    
}
