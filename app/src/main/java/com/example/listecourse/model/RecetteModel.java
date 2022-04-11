package com.example.listecourse.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.listecourse.R;
import com.example.listecourse.activity.MainActivity;
import com.example.listecourse.activity.View_ajout_recette;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.bdd.RecetteProduit;
import com.example.listecourse.tools.CustomAdapter;
import com.example.listecourse.tools.CustomAdapterQte;
import com.example.listecourse.tools.DatabaseLinker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecetteModel {

    public static void createRecette(DatabaseLinker linker, TableLayout containerRecette, Context context){
        containerRecette.removeAllViews();
        try {
            Dao<Recette, Integer> daoRecette = linker.getDao(Recette.class);
            List<Recette> recetteList = daoRecette.queryForAll();
            Dao<RecetteProduit, Integer> daoRecetteProduit = linker.getDao(RecetteProduit.class);


            for (Recette recette : recetteList) {
                TableRow row = new TableRow(context);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setWeightSum(8);
                TableRow.LayoutParams paramp = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );
                //Spinner
                TableRow rowS = new TableRow(context);
                rowS.setGravity(Gravity.CENTER_VERTICAL);
                rowS.setWeightSum(8);
                TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );

                Spinner snpProduit;
                snpProduit = new Spinner(context);
                snpProduit.setLayoutParams(paramSpinner);
                if (recette.getListeProduit(context) != null){
                    List<RecetteProduit>recetteProduitList = recette.getListeProduit(context);
                    CustomAdapterQte adapter = new CustomAdapterQte((Activity) context,
                            R.layout.spinner_layout_ressource_qte,
                            R.id.uniter,
                            R.id.textView_item_name,
                            R.id.quantiter,
                            R.id.prix,
                            recetteProduitList);
                    snpProduit.setAdapter(adapter);
                    rowS.addView(snpProduit);
                    if(rowS.getParent() != null) {
                        Log.e("", "tesssssssss");

                    }

                }

                //lable recette
                TextView labelNom = new TextView(context);
                labelNom.setLayoutParams(paramp);
                labelNom.setText(recette.getLibelleRecette());
                row.addView(labelNom);

                //prix recette
                TextView labelPrenom = new TextView(context);
                labelPrenom.setLayoutParams(paramp);
                labelPrenom.setText(String.valueOf(recette.getPrixListeProduit())+"€");
                row.addView(labelPrenom);
                TableRow.LayoutParams paramButton = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0f
                );
                //button srpprimmer
                ImageButton deleteClient = new ImageButton(context);
                deleteClient.setLayoutParams(paramButton);
                deleteClient.setImageResource(R.drawable.delete);
                deleteClient.setBackground(null);
                row.addView(deleteClient);
                deleteClient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //suppresion des produits de la recette
                            List<RecetteProduit> recetteProduitList = recette.getListeProduit(context);
                            for (RecetteProduit prodRecette : recetteProduitList){
                                daoRecetteProduit.delete(prodRecette);
                            }
                            daoRecette.delete(recette);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        ((ViewGroup) row.getParent()).removeView(row);
                        //suppression spinner
                        ((ViewGroup)rowS.getParent()).removeView(rowS); // <- fix
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
                        Intent monIntent = new Intent(context, View_ajout_recette.class);
                        monIntent.putExtra("idRecette", recette.getIdRecette());
                        context.startActivity(monIntent);
                    }
                });


                


                containerRecette.addView(row);
                containerRecette.addView(rowS);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
