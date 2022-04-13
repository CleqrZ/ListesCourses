package com.example.listecourse.bdd;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//Liaison entre recette et produit avec un champs qte pour avoir une quantiter d'un meme produits dans une recettes
@DatabaseTable(tableName="ListeCourseProduit")
public class ListeCourseRecette {
    @DatabaseField( generatedId = true )
    private int idListeCourseProduit;
    @DatabaseField(foreign = true, foreignColumnName = "id", columnName ="ListeCourse", columnDefinition = "INTEGER CONSTRAINT FK_NAME REFERENCES ListeCourse(id) ON DELETE CASCADE")
    private ListeCourse idListeCourseP;
    @DatabaseField(foreign = true, foreignColumnName = "r_id", columnName = "Recette", columnDefinition = "INTEGER CONSTRAINT FK_NAME REFERENCES Recette(r_id) ON DELETE CASCADE")
    private Recette idRecetteR;
    @DatabaseField( columnName = "qte")
    private int qte;
    public ListeCourseRecette() {
    }
    public ListeCourseRecette(Recette idRecetteR,ListeCourse idListeCourseP,int qte) {
        this.idRecetteR = idRecetteR;
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

    public Recette getIdRecetteR() {
        return idRecetteR;
    }

    public void setIdRecetteR(Recette idRecetteP) {
        this.idRecetteR = idRecetteP;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
