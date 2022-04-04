package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="RecetteProduit")
public class RecetteProduit {
    @DatabaseField( generatedId = true )
    private int idRecetteProduit;
    @DatabaseField(foreign = true, foreignColumnName = "r_id", columnName ="Recette")
    private Recette idRectteP;
    @DatabaseField(foreign = true, foreignColumnName = "p_id", columnName = "Produit")
    private Produit idProduitR;
    @DatabaseField( columnName = "qte")
    private int qte;

    public RecetteProduit() {

    }
    public RecetteProduit(Produit idProduitR,Recette idRectteP,int qte) {
        this.idProduitR = idProduitR;
        this.idRectteP = idRectteP;
        this.qte = qte;
    }

    public Recette getIdRectteP() {
        return idRectteP;
    }

    public void setIdRectteP(Recette idRectteP) {
        this.idRectteP = idRectteP;
    }

    public Produit getIdProduitR() {
        return idProduitR;
    }

    public void setIdProduitR(Produit idProduitR) {
        this.idProduitR = idProduitR;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getIdRecetteProduit() {
        return idRecetteProduit;
    }
}
