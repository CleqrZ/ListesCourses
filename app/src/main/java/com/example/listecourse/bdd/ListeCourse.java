package com.example.listecourse.bdd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Arrays;
import java.util.List;

@DatabaseTable(tableName="ListeCourse")
public class ListeCourse {
    @DatabaseField( columnName = "id", generatedId = true )
    private int idListeCourse;
    @DatabaseField(columnName = "libelle")
    private String libelle;
    @DatabaseField(columnName = "listeProduit")
    private String listeProduit;
    @DatabaseField(columnName = "listeRecette")
    private String listeRecette;
    @DatabaseField(columnName = "prixCourse")
    private double prixCourse;
    public ListeCourse(String nomListe, String listeProduit, String listeRecette, double PrixCourse) {
        this.libelle = nomListe;
        this.prixCourse =PrixCourse;
        this.listeProduit = listeProduit;
        this.listeRecette = listeRecette;
    }
    public ListeCourse(String nomListe, double PrixCourse) {
        this.libelle = nomListe;
        this.prixCourse =PrixCourse;

    }
    public ListeCourse() {
    }


    public int getIdListeCourse() {
        return idListeCourse;
    }

    public String getNomListe() {
        return libelle;
    }

    public void setNomListe(String nomListe) {
        this.libelle = nomListe;
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
        this.listeProduit = listeProduit;
    }

    public List<Recette> getListeRecette() {
            ObjectMapper mapper = new ObjectMapper();
            List<Recette> participantJsonList = null;
            try {
                participantJsonList = Arrays.asList(mapper.readValue(listeRecette, Recette[].class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        return participantJsonList;
    }

    public void setListeRecette(String listeRecette) {
        this.listeRecette = listeRecette;
    }

    public double getPrixProduit() {
        return prixCourse;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixCourse = prixProduit;
    }
}
