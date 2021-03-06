package com.example.listecourse.bdd;

import android.content.Context;
import android.util.Log;
import android.widget.Spinner;

import com.example.listecourse.tools.DatabaseLinker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DatabaseTable(tableName="Recette")
public class Recette {
    @DatabaseField( columnName = "r_id", generatedId = true )
    private int idRecette;
    @DatabaseField (columnName = "libelleRecette")
    private String libelleRecette;
    @DatabaseField(columnName = "prixListeProduit")
    private double prixListeProduit;


    public Recette() {
    }
    public Recette(String libelle,double Prix) {
        this.libelleRecette = libelle;
        this.prixListeProduit =Prix;
    }

    public int getIdRecette() {

        return idRecette;
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
    public RecetteProduit getListeProduitbyid(Context context, Produit produit) throws SQLException {
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<RecetteProduit,Integer> recetteProduitDao = linker.getDao(RecetteProduit.class);
        List<RecetteProduit> recetteProduitList = recetteProduitDao.queryForAll();
        List<RecetteProduit> listeP = new ArrayList<>();
        for (RecetteProduit recetteProduit : recetteProduitList){
            if (recetteProduit.getIdProduitR() == produit){
                return recetteProduit;
            }
        }
        return null;
    }
    public List<RecetteProduit> getListeProduit(Context context) throws SQLException {
        //A changer par une queryBuilder(requette pr??parer )
        /* QueryBuilder<Ville, Integer> queryBuilder = daoVille.queryBuilder();
                Log.e("ville", com.example.dossierclient.tablebdd.Ville.nameVille);
                queryBuilder.where().eq(Ville.nameVille, String.valueOf(ville.getText()));
                PreparedQuery<Ville> preparedQuery = queryBuilder.prepare();
                List<Ville> villeList = daoVille.query(preparedQuery);
                listProduit_recette_gateau =
                    daoProduit_recette.query(
                            daoProduit_recette.queryBuilder().where()
                                    .eq("recette_id", recetteChocolat.getId())
                                    .prepare());
                                    */
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<RecetteProduit,Integer> recetteProduitDao = linker.getDao(RecetteProduit.class);
        List<RecetteProduit> recetteProduitList = recetteProduitDao.queryForAll();
        List<RecetteProduit> listeP = new ArrayList<>();
        for (RecetteProduit recetteProduit : recetteProduitList){
            if (recetteProduit.getIdRectteP().getIdRecette() == this.getIdRecette()){
                listeP.add(recetteProduit);
            }
        }
        return listeP;
    }


    public void setListe(List<RecetteProduit> liste, Context context) throws SQLException {
        DatabaseLinker linker = new DatabaseLinker(context);
        Dao<RecetteProduit,Integer> recetteProduitDao = null;
        try {
            recetteProduitDao = linker.getDao(RecetteProduit.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (recetteProduitDao !=null){
            for (RecetteProduit recetteProduit : liste){
                if (recetteProduit.getIdRectteP().getIdRecette() == this.getIdRecette()){
                    recetteProduitDao.create(recetteProduit);
                }
            }
        }

    }
}
