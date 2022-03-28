package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.listecourse.R;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class View_recette extends AppCompatActivity {

    private TableLayout containerProduits;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produit);
        drawerLayout = findViewById(R.id.drawerLayout);
        containerProduits = findViewById(R.id.container_produit);
        Button buttonAjoutProduits = findViewById(R.id.button_ajout_produit);
        buttonAjoutProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProduits = new Intent(View_recette.this, View_ajout_recette.class);
                startActivity(intentProduits);
            }
        });
        createProduit();
        TextView produit =findViewById(R.id.produitMenu);
        produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRecette = new Intent(View_recette.this, View_produit.class);
                startActivity(intentRecette);
            }
        });
        TextView recette = findViewById(R.id.recetteMenu);
        TextView liste = findViewById(R.id.listeMenu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem _item) {
        switch(_item.getItemId()){
            case R.id.menu:
                drawerLayout.openDrawer(Gravity.RIGHT);

                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }
    public void createProduit() {
        containerProduits.removeAllViews();
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            Dao<Recette, Integer> daoRecette = linker.getDao(Recette.class);
            List<Recette> recetteList = daoRecette.queryForAll();
            for (Recette recette : recetteList) {
                TableRow row = new TableRow(this);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setWeightSum(8);

                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        4f
                );

                TextView labelNom = new TextView(this);
                labelNom.setLayoutParams(param);
                labelNom.setText(recette.getLibelleRecette());
                row.addView(labelNom);

                TextView labelPrenom = new TextView(this);
                labelPrenom.setLayoutParams(param);
                labelPrenom.setText(String.format("%d€", (int) recette.getPrixListeProduit()));
                row.addView(labelPrenom);

                TableRow.LayoutParams paramButton = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                );
                ImageButton deleteClient = new ImageButton(this);
                deleteClient.setLayoutParams(paramButton);
                deleteClient.setImageResource(com.android.car.ui.R.drawable.car_ui_icon_delete);
                deleteClient.setBackground(null);
                row.addView(deleteClient);
                deleteClient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            daoRecette.delete(recette);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        ((ViewGroup) row.getParent()).removeView(row);
                    }
                });
                ImageButton modifClient = new ImageButton(this);
                modifClient.setLayoutParams(paramButton);
                modifClient.setImageResource(com.android.car.ui.R.drawable.car_ui_icon_edit);
                modifClient.setBackground(null);
                row.addView(modifClient);
                modifClient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent monIntent = new Intent(View_recette.this, View_ajout_recette.class);
                        monIntent.putExtra("idProduit", recette.getIdRecette());
                        startActivity(monIntent);
                    }
                });
                containerProduits.addView(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}