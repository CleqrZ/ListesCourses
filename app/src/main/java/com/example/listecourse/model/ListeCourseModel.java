package com.example.listecourse.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.listecourse.R;
import com.example.listecourse.activity.View_ajout_ListeCourse;
import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.bdd.ListeCourseProduit;
import com.example.listecourse.bdd.ListeCourseRecette;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.bdd.RecetteProduit;
import com.example.listecourse.tools.CustomAdapterQte;
import com.example.listecourse.tools.CustomAdapterQteListe;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.util.List;

public class ListeCourseModel {

    public static void createListeCourse(DatabaseLinker linker, TableLayout containerListecourse, Context context) {
        containerListecourse.removeAllViews();
        try {
            Dao<ListeCourse, Integer> daoListeCourse = linker.getDao(ListeCourse.class);
            List<ListeCourse> listeCourses = daoListeCourse.queryForAll();
            Dao<RecetteProduit, Integer> daoRecetteProduit = linker.getDao(RecetteProduit.class);
            Dao<Recette, Integer> daoRecette = linker.getDao(Recette.class);
            List<Recette> recetteList = daoRecette.queryForAll();
            Dao<ListeCourseRecette, Integer> daoListeCourseRecette = linker.getDao(ListeCourseRecette.class);
            Dao<ListeCourseProduit, Integer> daoListeCourseProduit = linker.getDao(ListeCourseProduit.class);

            for (ListeCourse listeCourse : listeCourses) {
                Log.e("test Liste :", listeCourse.getNomListe());
                TableRow row = new TableRow(context);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setWeightSum(8);
                TableRow paramListe = new TableRow(context);

                TextView liste = new TextView(context);
                liste.setTypeface(null, Typeface.BOLD);
                liste.setText("Nom de la liste");
                paramListe.addView(liste);
                containerListecourse.addView(paramListe);
                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );
                //label recette
                TextView labelNom = new TextView(context);
                labelNom.setLayoutParams(param);
                labelNom.setText(listeCourse.getNomListe());
                row.addView(labelNom);
                TableRow.LayoutParams paramp = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0f
                );
                //prix recette
                TextView prixListe = new TextView(context);
                prixListe.setLayoutParams(paramp);
                prixListe.setText(String.valueOf(listeCourse.getPrixProduit()) + "???");
                row.addView(prixListe);
                TableRow.LayoutParams paramButton = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                );
                containerListecourse.addView(row);
                //Spinner
                TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );
                //spinner Recette de la liste
                TableLayout test = new TableLayout(context);
                if (listeCourse.getListeR(context) != null) {
                    Log.e("-------", "Je rentre dans le IF");
                    List<ListeCourseRecette>listeCourseRecettes = listeCourse.getListeR(context);
                    int cpt;
                    for (ListeCourseRecette listeCourseRecette : listeCourseRecettes ) {

                        Spinner snpRecette;
                        snpRecette = new Spinner(context);
                        snpRecette.setLayoutParams(paramSpinner);
                        Log.e("-------", listeCourseRecette.getIdRecetteR().getLibelleRecette());
                        TableRow rowS = new TableRow(context);
                        rowS.setGravity(Gravity.CENTER_VERTICAL);
                        rowS.setWeightSum(8);

                        TableRow.LayoutParams paramSpinnerNomRecette = new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                8f
                        );
                        TableRow rowNomRecette = new TableRow(context);
                        rowNomRecette.setGravity(Gravity.CENTER_VERTICAL);
                        rowNomRecette.setWeightSum(8);
                        Recette recetteR = listeCourseRecette.getIdRecetteR();
                        TextView nomRecette = new TextView(context);
                        nomRecette.setTypeface(null, Typeface.BOLD);
                        nomRecette.setText("Recette --> " + recetteR.getLibelleRecette() + " : *" + listeCourseRecette.getQte());
                        List<RecetteProduit> listRecetteProduit = recetteR.getListeProduit(context);
                        CustomAdapterQte adapter = new CustomAdapterQte((Activity) context,
                                R.layout.spinner_layout_ressource_qte,
                                R.id.uniter,
                                R.id.textView_item_name,
                                R.id.quantiter,
                                R.id.prix,
                                listRecetteProduit);
                        snpRecette.setAdapter(adapter);
                        rowNomRecette.addView(nomRecette);
                        rowS.addView(snpRecette);
                        test.addView(rowNomRecette);
                        test.addView(rowS);
                        if(rowS.getParent() != null) {
                            Log.e("", "tesssssssss");

                        }
                    }
                    containerListecourse.addView(test);
                }
                TableRow rowP = new TableRow(context);
                rowP.setGravity(Gravity.CENTER_VERTICAL);
                rowP.setWeightSum(8);
                TableRow.LayoutParams paramSpinnerProduit = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );

                //spinner Produit de la liste
                TableRow.LayoutParams paramSpinnerProduitListe = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );

                TableRow rowNomProduitListe = new TableRow(context);
                rowNomProduitListe.setGravity(Gravity.CENTER_VERTICAL);
                rowNomProduitListe.setWeightSum(8);
                TextView produitListe = new TextView(context);
                produitListe.setTypeface(null, Typeface.BOLD);
                produitListe.setText("Produits de la liste : " );
                rowNomProduitListe.addView(produitListe);
                containerListecourse.addView(rowNomProduitListe);




                Spinner snpProduit;
                snpProduit = new Spinner(context);
                snpProduit.setLayoutParams(paramSpinnerProduit);
                if (listeCourse.getListeP(context) != null) {
                    Log.e("-------", "Je rentre dans le IF");
                    List<ListeCourseProduit> listeCourseProduits = listeCourse.getListeP(context);
                    CustomAdapterQteListe adapter = new CustomAdapterQteListe((Activity) context,
                            R.layout.spinner_layout_ressource_qte_liste,
                            R.id.uniter,
                            R.id.textView_item_name,
                            R.id.quantiter,
                            R.id.prix,
                            listeCourseProduits);
                    snpProduit.setAdapter(adapter);
                    rowP.addView(snpProduit);
                    if (rowP.getParent() != null) {
                        Log.e("", "tesssssssss");

                    }
                }
                containerListecourse.addView(rowP);

                //button delete
                ImageButton deleteClient = new ImageButton(context);
                deleteClient.setLayoutParams(paramButton);
                deleteClient.setImageResource(com.android.car.ui.R.drawable.car_ui_icon_delete);
                deleteClient.setBackground(null);
                row.addView(deleteClient);
                deleteClient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            List<ListeCourseRecette> listeCourseRecette = listeCourse.getListeR(context);
                            for (ListeCourseRecette prodRecette1 : listeCourseRecette){
                                //suppresion des produits de la recette
                                daoListeCourseRecette.delete(prodRecette1);
                            }

                            //suppresion des produits de la liste
                            List<ListeCourseProduit> ListeCourseproduit = listeCourse.getListeP(context);
                            for (ListeCourseProduit prodList : ListeCourseproduit){
                                daoListeCourseProduit.delete(prodList);
                            }
                            daoListeCourse.delete(listeCourse);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        ((ViewGroup) row.getParent()).removeView(row);
                        ((ViewGroup) rowP.getParent()).removeView(rowP);
                        ((ViewGroup) rowNomProduitListe.getParent()).removeView(rowNomProduitListe);
                        ((ViewGroup) test.getParent()).removeView(test);
                        ((ViewGroup) paramListe.getParent()).removeView(paramListe);

                    }
                });
                //button modif
                ImageButton modifClient = new ImageButton(context);
                modifClient.setLayoutParams(paramButton);
                modifClient.setImageResource(com.android.car.ui.R.drawable.car_ui_icon_edit);
                modifClient.setBackground(null);
                row.addView(modifClient);
                modifClient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent monIntent = new Intent(context, View_ajout_ListeCourse.class);
                        monIntent.putExtra("idListeCourse", listeCourse.getIdListeCourse());
                        context.startActivity(monIntent);
                    }
                });
                TextView espace = new TextView(context);
                espace.setText("\n");
                containerListecourse.addView(espace);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}