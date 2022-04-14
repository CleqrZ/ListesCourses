package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//Liaison entre recette et produit avec un champs qte pour avoir une quantiter d'un meme produits dans une recettes
@DatabaseTable(tableName="ListeCourseProduit")
public class ListeCourseProduit {
    @DatabaseField( generatedId = true )
    private int idListeCourseProduit;
    @DatabaseField(foreign = true, foreignColumnName = "id", columnName ="ListeCourseProduit")
    private ListeCourse idListeCourseP;
    @DatabaseField(foreign = true, foreignColumnName = "p_id", columnName = "ProduitListe")
    private Produit idProduitP;
    @DatabaseField( columnName = "qte")
    private int qte;

    public ListeCourseProduit() {
    }
    public ListeCourseProduit(Produit idProduitR,ListeCourse idListeCourseP,int qte) {
        this.idProduitP = idProduitR;
        this.idListeCourseP = idListeCourseP;
        this.qte = qte;
    }

    public int getIdListeCourseProduit() {
        return idListeCourseProduit;
    }

    public void setIdListeCourseProduit(int idListeCourseProduit) {
        this.idListeCourseProduit = idListeCourseProduit;
    }

    public ListeCourse getIdListeCourseP() {
        return idListeCourseP;
    }

    public void setIdListeCourseP(ListeCourse idListeCourseP) {
        this.idListeCourseP = idListeCourseP;
    }

    public Produit getIdProduitP() {
        return idProduitP;
    }

    public void setIdProduitP(Produit idProduitR) {
        this.idProduitP = idProduitP;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
