package com.example.listecourse.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.sql.SQLException;
import java.util.List;

public class ListeCourseModel {

    public static void createListeCourse(DatabaseLinker linker, TableLayout containerListecourse, Context context) {
        containerListecourse.removeAllViews();
        try {
            Dao<ListeCourse, Integer> daoListeCourse = linker.getDao(ListeCourse.class);
            List<ListeCourse> listeCourses = daoListeCourse.queryForAll();
            for (ListeCourse listeCourse : listeCourses) {
                Log.e("test Liste :", listeCourse.getNomListe());
                TableRow row = new TableRow(context);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setWeightSum(8);
                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        4f
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
                prixListe.setText(String.valueOf(listeCourse.getPrixProduit()) + "€");
                row.addView(prixListe);

                TableRow.LayoutParams paramButton = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                );
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
                            daoListeCourse.delete(listeCourse);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        ((ViewGroup) row.getParent()).removeView(row);
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
                        monIntent.putExtra("idRecette", listeCourse.getNomListe());
                        context.startActivity(monIntent);
                    }
                });
                containerListecourse.addView(row);
                //Spinner




                TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );

                //spinner Recette de la liste
                if (listeCourse.getListeCourseRecette(context) != null) {
                    Log.e("-------", "Je rentre dans le IF");
                    List<ListeCourseRecette>listeCourseRecettes = listeCourse.getListeCourseRecette(context);
                    Log.e("---", String.valueOf(listeCourseRecettes.get(0)));
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
                        nomRecette.setText("recette --> " + recetteR.getLibelleRecette() + " : *" + listeCourseRecette.getQte());
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
                        if(rowS.getParent() != null) {
                            Log.e("", "tesssssssss");

                        }
                        containerListecourse.addView(rowNomRecette);
                        containerListecourse.addView(rowS);
                    }




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
                Spinner snpProduit;
                snpProduit = new Spinner(context);
                snpProduit.setLayoutParams(paramSpinnerProduit);
                if (listeCourse.getListeCourseProduit(context) != null) {
                    Log.e("-------", "Je rentre dans le IF");
                    List<ListeCourseProduit>listeCourseProduits = listeCourse.getListeCourseProduit(context);
                        CustomAdapterQteListe adapter = new CustomAdapterQteListe((Activity) context,
                                R.layout.spinner_layout_ressource_qte_liste,
                                R.id.uniter,
                                R.id.textView_item_name,
                                R.id.quantiter,
                                R.id.prix,
                                listeCourseProduits);
                        snpProduit.setAdapter(adapter);
                        rowP.addView(snpProduit);
                        if(rowP.getParent() != null) {
                            Log.e("", "tesssssssss");

                    }

                    containerListecourse.addView(rowP);


                }
                /*
                Spinner snpProduit;
                snpProduit = new Spinner(context);
                snpProduit.setLayoutParams(paramSpinner);
                List<ListeCourseProduit> produitListCourse = listeCourse.getListeCourseProduit(context);
                CustomAdapter adapter = new CustomAdapter((Activity) context,
                        R.layout.spinner_layout_ressource,
                        R.id.textView_item_name,
                        R.id.quantiter,
                        R.id.prix,
                        produitListCourse
                        );
                snpProduit.setAdapter(adapter);
                containerListecourse.addView(rowS);
                rowS.addView(snpProduit);
                    //Affichage recette
                List<Recette> recetteJsonList  = listeCourse.getListeCourseRecette();
                ObjectMapper mapperR = new ObjectMapper();
                for (Recette recette : recetteJsonList) {
                    TableRow rowRecette = new TableRow(context);
                    TableRow.LayoutParams paramTexte = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            8f
                    );
                    TextView rectteNom = new TextView(context);
                    rectteNom.setLayoutParams(paramTexte);
                    rectteNom.setText(recette.getLibelleRecette());
                    rowRecette.addView(rectteNom);

                    //affichage produit des recette dans spinner
                    Dao<Recette, Integer> daoRecette = linker.getDao(Recette.class);
                    TableRow.LayoutParams paramSpinnerSi = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            8f
                    );
                    int idrecette = recette.getIdRecette();
                    List<RecetteProduit> participantJsonListSi = daoRecette.queryForId(idrecette).getListeProduit(context);
                    Spinner snpProduitSi;
                    snpProduitSi = new Spinner(context);
                    snpProduitSi.setLayoutParams(paramSpinnerSi);
                    if(participantJsonListSi != null){
                        CustomAdapterQteL adapterSi = new CustomAdapterQteL((Activity) context,
                                R.layout.spinner_layout_ressource_qte,
                                R.id.uniter,
                                R.id.textView_item_name,
                                R.id.quantiter,
                                R.id.prix,
                                participantJsonListSi);
                        snpProduitSi.setAdapter(adapterSi);
                        rowRecette.addView(snpProduitSi);
                    }
                    Log.e("Recette non : ",recette +"ok" );
                    containerListecourse.addView(rowRecette);
                }*/
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

