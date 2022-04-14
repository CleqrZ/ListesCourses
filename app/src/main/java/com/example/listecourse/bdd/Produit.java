package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName="Produit")
public class Produit {
    @DatabaseField( columnName = "p_id", generatedId = true )
    private int idProduit;
    @DatabaseField
    private String libelleProduit;
    @DatabaseField
    private String quantiter;
    @DatabaseField
    private double prixProduit;

    public Produit() {
    }
    public Produit(String libelleProduit,String quantite, double prixProduit) {
        this.libelleProduit =libelleProduit;
        this.quantiter =quantite;
        this.prixProduit  = prixProduit;
    }

    public String getLibelleProduit() {
        return libelleProduit;
    }

    public void setLibelleProduit(String libelleProduit) {
        this.libelleProduit = libelleProduit;
    }

    public double getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public String getQuantiter() {
        return quantiter;
    }

    public void setQuantiter(String quantiter) {
        this.quantiter = quantiter;
    }
}
