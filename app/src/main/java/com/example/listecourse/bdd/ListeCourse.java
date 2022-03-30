package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    public String getListeProduit() {
        return listeProduit;
    }

    public void setListeProduit(String listeProduit) {
        this.listeProduit = listeProduit;
    }

    public String getListeRecette() {
        return listeRecette;
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
