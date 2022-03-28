package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Recette")
public class Recette {
    @DatabaseField( columnName = "id", generatedId = true )
    private int idRecette;
    @DatabaseField
    private String LibelleRecette;
    @DatabaseField
    private String ListeProduit;
    @DatabaseField
    private double PrixListeProduit;


    public Recette(String libelle, double Prix) {
        this.LibelleRecette = libelle;
        this.PrixListeProduit=Prix;
    }

    public Recette() {
    }

    public int getIdRecette() {

        return idRecette;
    }

    public String getListeProduit() {

        return ListeProduit;
    }

    public void setListeProduit(String listeProduit) {

        ListeProduit = listeProduit;
    }

    public String getLibelleRecette() {

        return LibelleRecette;
    }

    public void setLibelleRecette(String libelleRecette) {

        LibelleRecette = libelleRecette;
    }

    public double getPrixListeProduit() {

        return PrixListeProduit;
    }

    public void setPrixListeProduit(double prixListeProduit) {
        PrixListeProduit = prixListeProduit;
    }
}
