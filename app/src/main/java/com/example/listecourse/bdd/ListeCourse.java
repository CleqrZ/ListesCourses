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


    public ListeCourse(String nomListe, ListeCourseProduit listeP, ListeCourseRecette listeR, double PrixCourse) {
        this.libelle = nomListe;
        this.prixCourse =PrixCourse;
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

    public List<ListeCourseProduit> getListeP(Context context) throws SQLException {
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<ListeCourseProduit,Integer> listeCourseProduitsDAO = linker.getDao(ListeCourseProduit.class);
        List<ListeCourseProduit> listeCourseProduitsList = listeCourseProduitsDAO.queryForAll();
        List<ListeCourseProduit> listeP = new ArrayList<>();
        for (ListeCourseProduit listeCourseProduit : listeCourseProduitsList){
            if (listeCourseProduit.getIdProduitP() == this.getListeCourseProduit(context)){
                listeP.add(listeCourseProduit);
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
                if (listeCourseProduit.getIdProduitP() == this.getListeCourseProduit(context)){
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
            if (listeCourseRecette.getIdRecetteR() == this.getListeCourseRecette(context)){
                listeP.add(listeCourseRecette);
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
                if (listeCourseRecette.getIdRecetteR() == this.getListeCourseRecette(context)){
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

