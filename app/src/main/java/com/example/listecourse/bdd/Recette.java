package com.example.listecourse.bdd;

import android.widget.Spinner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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

    public List<Produit> getListeProduit() {
        ObjectMapper mapper = new ObjectMapper();
        List<Produit> participantJsonList = null;
        try {
            participantJsonList = Arrays.asList(mapper.readValue(listeProduit, Produit[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return participantJsonList;
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
