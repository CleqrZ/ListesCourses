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
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.tools.CustomAdapter;
import com.example.listecourse.tools.DatabaseLinker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListeCourseModel {

    public static void createListeCourse(DatabaseLinker linker, TableLayout containerListecourse, Context context){
        containerListecourse.removeAllViews();
        try {
            Dao<ListeCourse, Integer> daoListeCourse = linker.getDao(ListeCourse.class);
            List<ListeCourse> listeCourses = daoListeCourse.queryForAll();
            for (ListeCourse listeCourse : listeCourses) {
                TableRow row = new TableRow(context);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setWeightSum(8);
                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        4f
                );

                TextView labelNom = new TextView(context);
                labelNom.setLayoutParams(param);
                labelNom.setText(listeCourse.getNomListe());
                row.addView(labelNom);
                TableRow.LayoutParams paramp = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0f
                );
                TextView prixListe = new TextView(context);
                prixListe.setLayoutParams(paramp);
                prixListe.setText(String.format("%d€", (int) listeCourse.getPrixProduit()));
                row.addView(prixListe);

                TableRow.LayoutParams paramButton = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                );
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
                TableRow rowS = new TableRow(context);
                rowS.setGravity(Gravity.CENTER_VERTICAL);
                rowS.setWeightSum(8);
                TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );
                String listeS = listeCourse.getListeProduit();

                Log.e("Produit Pliste course", listeS +"ok ");

                ObjectMapper mapper = new ObjectMapper();
                Spinner snpProduit;
                snpProduit = new Spinner(context);
                snpProduit.setLayoutParams(paramSpinner);
                List<Produit> participantJsonList = Arrays.asList(mapper.readValue(listeS, Produit[].class));
                CustomAdapter adapter = new CustomAdapter((Activity) context,
                        R.layout.spinner_layout_ressource,
                        R.id.textView_item_name,
                        R.id.quantiter,
                        R.id.prix,
                        participantJsonList);
                snpProduit.setAdapter(adapter);
                containerListecourse.addView(rowS);
                rowS.addView(snpProduit);
                //Affichage recette
                String listeRecetteS =listeCourse.getListeRecette();
                Log.e("Recette liste course", listeRecetteS +"ok ");
                ObjectMapper mapperR = new ObjectMapper();
                List<Recette> recetteJsonList =Arrays.asList(mapperR.readValue(listeRecetteS, Recette[].class));
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
                    //affichage produit des recette
                    Dao<Recette, Integer> daoRecette = linker.getDao(Recette.class);
                    TableRow.LayoutParams paramSpinnerSi = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            8f
                    );
                    int idrecette = recette.getIdRecette();
                    String listeSi = daoRecette.queryForId(idrecette).getListeProduit();
                    Log.e("Liste Produit", listeSi +"ok ");
                    Spinner snpProduitSi;
                    snpProduitSi = new Spinner(context);
                    snpProduitSi.setLayoutParams(paramSpinnerSi);
                    if(listeSi != null){
                        List<Produit>participantJsonListSi = Arrays.asList(mapper.readValue(listeSi, Produit[].class));
                        CustomAdapter adapterSi = new CustomAdapter((Activity) context,
                                R.layout.spinner_layout_ressource,
                                R.id.textView_item_name,
                                R.id.quantiter,
                                R.id.prix,
                                participantJsonListSi);
                        snpProduitSi.setAdapter(adapterSi);
                        rowRecette.addView(snpProduitSi);
                    }

                    Log.e("Recette non : ",recette +"ok" );
                    containerListecourse.addView(rowRecette);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}