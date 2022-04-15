package com.example.listecourse.bdd;

import android.content.Context;
import android.util.Log;

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

    public List<ListeCourseProduit> getListeP(Context context) throws SQLException {
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<ListeCourseProduit,Integer> listeCourseProduitsDAO = linker.getDao(ListeCourseProduit.class);
        List<ListeCourseProduit> listeCourseProduitsList = listeCourseProduitsDAO.queryForAll();
        List<ListeCourseProduit> listeP = new ArrayList<>();
        for (ListeCourseProduit listeCourseProduit : listeCourseProduitsList){
            if (listeCourseProduit.getIdListeCourseP().getIdListeCourse() == this.getIdListeCourse() ){
                listeP.add(listeCourseProduit);
                Log.e("test listeprod",listeCourseProduit.getIdProduitP().getLibelleProduit() );
            }
        }

        return listeP;

    }

    public void setListeP(List<ListeCourseProduit> listeP, Context context) throws SQLException {
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<ListeCourseProduit,Integer> listeCourseProduitDAO = null;
        try {
            listeCourseProduitDAO = linker.getDao(ListeCourseProduit.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (listeCourseProduitDAO !=null){
            for (ListeCourseProduit listeCourseProduit : listeP){
                if (listeCourseProduit.getIdListeCourseP().idListeCourse == this.getIdListeCourse()){
                    listeCourseProduitDAO.create(listeCourseProduit);
                }
            }
        }
    }

    public List<ListeCourseRecette> getListeR(Context context) throws SQLException {
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<ListeCourseRecette,Integer> listeCourseRecettesDAO = linker.getDao(ListeCourseRecette.class);
        List<ListeCourseRecette> listeCourseRecettesList = listeCourseRecettesDAO.queryForAll();
        List<ListeCourseRecette> listeP = new ArrayList<>();
        for (ListeCourseRecette listeCourseRecette : listeCourseRecettesList){
            if (listeCourseRecette.getIdListeCourseP().getIdListeCourse() == this.getIdListeCourse()){
                listeP.add(listeCourseRecette);
                Log.e("test listeprod",listeCourseRecette.getIdRecetteR().getLibelleRecette() );
            }
        }
        return listeP;
    }

    public void setListeR(List<ListeCourseRecette> listeR, Context context) throws SQLException {
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<ListeCourseRecette,Integer> listeCourseRecettesDAO = null;
        try {
            listeCourseRecettesDAO = linker.getDao(ListeCourseRecette.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (listeCourseRecettesDAO !=null){
            for (ListeCourseRecette listeCourseRecette : listeR){
                if (listeCourseRecette.getIdListeCourseP().getIdListeCourse() == this.getIdListeCourse()){
                    listeCourseRecettesDAO.create(listeCourseRecette);
                }
            }
        }
    }

    public double getPrixProduit() {
        return prixCourse;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixCourse = prixProduit;
    }


}

