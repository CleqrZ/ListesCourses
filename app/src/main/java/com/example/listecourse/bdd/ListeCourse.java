package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="ListeCourse")
public class ListeCourse {
    @DatabaseField( columnName = "id", generatedId = true )
    private int idListeCourse;
    @DatabaseField
    private String Libelle;
    @DatabaseField
    private String ListeProduit;
    @DatabaseField
    private String ListeRecette;
    @DatabaseField
    private double PrixCourse;

    public ListeCourse() {
    }
    public ListeCourse(String nomListe, String listeProduit, String listeRecette, double PrixCourse) {
        this.Libelle = nomListe;
        this.ListeProduit = listeProduit;
        this.ListeRecette = listeRecette;
        this.PrixCourse =PrixCourse;

    }
    public int getIdListeCourse() {
        return idListeCourse;
    }

    public String getNomListe() {
        return Libelle;
    }

    public void setNomListe(String nomListe) {
        this.Libelle = nomListe;
    }

    public String getListeProduit() {
        return ListeProduit;
    }

    public void setListeProduit(String listeProduit) {
        ListeProduit = listeProduit;
    }

    public String getListeRecette() {
        return ListeRecette;
    }

    public void setListeRecette(String listeRecette) {
        ListeRecette = listeRecette;
    }

    public double getPrixProduit() {
        return PrixCourse;
    }

    public void setPrixProduit(double prixProduit) {
        this.PrixCourse = prixProduit;
    }
}
