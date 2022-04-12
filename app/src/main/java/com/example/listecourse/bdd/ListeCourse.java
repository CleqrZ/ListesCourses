package com.example.listecourse.bdd;

import android.content.Context;

import com.example.listecourse.tools.DatabaseLinker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DatabaseTable(tableName="ListeCourse")
public class ListeCourse {
    @DatabaseField( columnName = "id", generatedId = true )
    private int idListeCourse;
    @DatabaseField(columnName = "libelle")
    private String libelle;
    @DatabaseField(columnName = "prixCourse")
    private double prixCourse;

    private ListeCourseProduit listeP = null;
    private ListeCourseRecette listeR = null;

    public ListeCourse(String nomListe, ListeCourseProduit listeP, ListeCourseRecette listeR, double PrixCourse) {
        this.libelle = nomListe;
        this.prixCourse =PrixCourse;
        this.listeP = listeP;
        this.listeR = listeR;
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

    public List<ListeCourseProduit> getListeP() {
        return (List<ListeCourseProduit>) listeP;
    }

    public void setListeP(List<ListeCourseProduit> listeP) {
        this.listeP = (ListeCourseProduit) listeP;
    }

    public List<ListeCourseRecette> getListeR() {
        return (List<ListeCourseRecette>) listeR;
    }

    public void setListeR(List<ListeCourseRecette> listeR) {
        this.listeR = (ListeCourseRecette) listeR;
    }

    public double getPrixProduit() {
        return prixCourse;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixCourse = prixProduit;
    }

    public List<ListeCourseProduit> getListeCourseProduit(Context context) throws SQLException {
        //A changer par une queryBuilder(requette préparer )
        /* QueryBuilder<Ville, Integer> queryBuilder = daoVille.queryBuilder();
                Log.e("ville", com.example.dossierclient.tablebdd.Ville.nameVille);
                queryBuilder.where().eq(Ville.nameVille, String.valueOf(ville.getText()));
                PreparedQuery<Ville> preparedQuery = queryBuilder.prepare();
                List<Ville> villeList = daoVille.query(preparedQuery);*/
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<ListeCourseProduit,Integer> listeCourseProduitsDao = linker.getDao(ListeCourseProduit.class);
        List<ListeCourseProduit> listeCourseProduits = listeCourseProduitsDao.queryForAll();
        List<ListeCourseProduit> listeP = new ArrayList<>();
        for (ListeCourseProduit listeCourseProduit : listeCourseProduits){
            if (listeCourseProduit.getIdListeCourseProduit() == this.getIdListeCourse()){
                listeP.add(listeCourseProduit);
            }
        }
        return listeP;
    }

    public List<ListeCourseRecette> getListeCourseRecette(Context context) throws SQLException {
        //A changer par une queryBuilder(requette préparer )
        /* QueryBuilder<Ville, Integer> queryBuilder = daoVille.queryBuilder();
                Log.e("ville", com.example.dossierclient.tablebdd.Ville.nameVille);
                queryBuilder.where().eq(Ville.nameVille, String.valueOf(ville.getText()));
                PreparedQuery<Ville> preparedQuery = queryBuilder.prepare();
                List<Ville> villeList = daoVille.query(preparedQuery);*/
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<ListeCourseRecette,Integer> listeCourseRecetteDao = linker.getDao(ListeCourseRecette.class);
        List<ListeCourseRecette> listeCourseRecettes = listeCourseRecetteDao.queryForAll();
        List<ListeCourseRecette> listeR = new ArrayList<>();
        for (ListeCourseRecette listeCourseRecette : listeCourseRecettes){
            if (listeCourseRecette.getIdListeCourseProduit() == this.getIdListeCourse()){
                listeR.add(listeCourseRecette);
            }
        }
        return listeR;
    }


}

