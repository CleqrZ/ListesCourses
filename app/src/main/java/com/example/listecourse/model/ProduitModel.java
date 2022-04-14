package com.example.listecourse.model;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.listecourse.activity.MainActivity;
import com.example.listecourse.activity.View_ajout_produit;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.RecetteProduit;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.ConsoleHandler;

public class ProduitModel{
    public static void createProduit(DatabaseLinker linker, TableLayout containerProduits, Context context){
        containerProduits.removeAllViews();
        try {
            Dao<Produit, Integer> daoProduits = linker.getDao(Produit.class);
            Dao<RecetteProduit, Integer> daoRecetteProduits = linker.getDao(RecetteProduit.class);
            List<Produit> produitList = daoProduits.queryForAll();
            for (Produit produit : produitList) {
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
                labelNom.setText(produit.getLibelleProduit());
                row.addView(labelNom);

                TextView labelPrenom = new TextView(context);
                labelPrenom.setLayoutParams(param);
                labelPrenom.setText(String.valueOf(produit.getPrixProduit())+"€");
                row.addView(labelPrenom);

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
                            daoProduits.delete(produit);
                            List<RecetteProduit> recetteProduitList = daoRecetteProduits.queryForAll();
                            for(RecetteProduit recetteProd : recetteProduitList){
                                if (recetteProd.getIdProduitR() == produit){
                                    daoRecetteProduits.delete(recetteProd);
                                }
                            }
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
                        Intent monIntent = new Intent(context, View_ajout_produit.class);
                        monIntent.putExtra("idProduit", produit.getIdProduit());
                        context.startActivity(monIntent);
                    }
                });
                containerProduits.addView(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
