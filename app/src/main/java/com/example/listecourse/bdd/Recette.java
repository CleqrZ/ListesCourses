package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName="Recette")
public class Recette implements Serializable {
    @DatabaseField( columnName = "id", generatedId = true )
    private int idRecette;
    @DatabaseField (columnName = "libelleRecette")
    private String libelleRecette;
    @DatabaseField(columnName = "listeProduit")
    private String listeProduit;
    @DatabaseField(columnName = "prixListeProduit")
    private double prixListeProduit;

    public Recette() {
    }
    public Recette(String libelle,double Prix) {
        this.libelleRecette = libelle;
        this.prixListeProduit =Prix;
    }

    public Recette(String libelle,String liste,double Prix) {
        this.libelleRecette = libelle;
        this.listeProduit = liste;
        this.prixListeProduit =Prix;
    }


    public int getIdRecette() {

        return idRecette;
    }

    public String getListeProduit() {

        return listeProduit;
    }

    public void setListeProduit(String listeProduit) {

        listeProduit = listeProduit;
    }

    public String getLibelleRecette() {

        return libelleRecette;
    }

    public void setLibelleRecette(String libelleRecette) {

        this.libelleRecette = libelleRecette;
    }

    public double getPrixListeProduit() {

        return prixListeProduit;
    }

    public void setPrixListeProduit(double prixListeProduit) {
        this.prixListeProduit = prixListeProduit;
    }
}
