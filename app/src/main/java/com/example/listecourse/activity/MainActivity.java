package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.listecourse.R;
import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.tools.CustomAdapter;
import com.example.listecourse.tools.DatabaseLinker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TableLayout containerProduits;
    private TableLayout containerRecette;
    private Button buttonAjoutProduits;
    private Button buttonAjoutRecette;
    private TableLayout containerListecourse;
    private Button buttonAjoutListecourse;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.deleteDatabase("bdd.db");
        //Produits
        drawerLayout = findViewById(R.id.drawerLayout);
        TextView produit =findViewById(R.id.produitMenu);
        produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProduits = new Intent(MainActivity.this, View_produit.class);
                startActivity(intentProduits);
            }
        });
        TextView recette = findViewById(R.id.recetteMenu);
        recette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRecette = new Intent(MainActivity.this, View_recette.class);
                startActivity(intentRecette);
            }
        });
        TextView liste = findViewById(R.id.listeMenu);
        containerProduits = findViewById(R.id.container_produit);
        buttonAjoutProduits = findViewById(R.id.button_ajout_produit);
        buttonAjoutProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProduits = new Intent(MainActivity.this, View_ajout_produit.class);
                startActivity(intentProduits);
            }
        });

        createProduits();
        //Recette
        containerRecette = findViewById(R.id.container_Recette);
        buttonAjoutRecette =findViewById(R.id.button_ajout_Recette);
        buttonAjoutRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRecette = new Intent(MainActivity.this, View_ajout_recette.class);
                startActivity(intentRecette);
            }
        });

        createRecette();
        //Liste
        containerListecourse = findViewById(R.id.container_listecourse);
        buttonAjoutListecourse =findViewById(R.id.button_ajout_listecourse);
        buttonAjoutListecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListeCourse = new Intent(MainActivity.this, View_ajout_ListeCourse.class);
                startActivity(intentListeCourse);
            }
        });
        createListeCourse();
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

    public void createProduits(){
        containerProduits.removeAllViews();
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            Dao<Produit, Integer> daoProduits = linker.getDao(Produit.class);
            List<Produit> produitList = daoProduits.queryForAll();
            for (Produit produit : produitList) {
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
                labelNom.setText(produit.getLibelleProduit());
                row.addView(labelNom);

                TextView labelPrenom = new TextView(this);
                labelPrenom.setLayoutParams(param);
                labelPrenom.setText(String.format("%d€", (int) produit.getPrixProduit()));
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
                            daoProduits.delete(produit);
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
                        Intent monIntent = new Intent(MainActivity.this, View_ajout_produit.class);
                        monIntent.putExtra("idProduit", produit.getIdProduit());
                        startActivity(monIntent);
                    }
                });
                containerProduits.addView(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createRecette() {
        containerRecette.removeAllViews();
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
                TableRow.LayoutParams paramp = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0f
                );
                TextView labelPrenom = new TextView(this);
                labelPrenom.setLayoutParams(paramp);
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
                        Intent monIntent = new Intent(MainActivity.this, View_ajout_recette.class);
                        monIntent.putExtra("idRecette", recette.getIdRecette());
                        startActivity(monIntent);
                    }
                });

              //Spinner
                TableRow rowS = new TableRow(this);
                rowS.setGravity(Gravity.CENTER_VERTICAL);
                rowS.setWeightSum(8);
                TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );
                String listeS = recette.getListeProduit();
                Log.e("Liste Produit", listeS +"ok ");
                ObjectMapper mapper = new ObjectMapper();
                List<String> listS = new ArrayList<>();
                Spinner snpProduit;

                snpProduit = new Spinner(this);
                snpProduit.setLayoutParams(paramSpinner);
                try {
                    List<Produit>participantJsonList = Arrays.asList(mapper.readValue(listeS, Produit[].class));
                    for (Produit produit : participantJsonList){
                        listS.add(produit.getLibelleProduit().toString());

                    }
                    CustomAdapter adapter = new CustomAdapter(MainActivity.this,
                            R.layout.spinner_layout_ressource,
                            R.id.textView_item_name,
                            R.id.quantiter,
                            R.id.prix,
                            participantJsonList);
                    snpProduit.setAdapter(adapter);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> asnpProduits = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listS);
                asnpProduits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rowS.addView(snpProduit);

                containerRecette.addView(row);
                containerRecette.addView(rowS);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public  void createListeCourse(){
        containerListecourse.removeAllViews();
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            Dao<ListeCourse, Integer> daoListeCourse = linker.getDao(ListeCourse.class);
            List<ListeCourse> listeCourses = daoListeCourse.queryForAll();
            for (ListeCourse listeCourse : listeCourses) {
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
                labelNom.setText(listeCourse.getNomListe());
                row.addView(labelNom);
                TableRow.LayoutParams paramp = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0f
                );
                TextView labelPrenom = new TextView(this);
                labelPrenom.setLayoutParams(paramp);
                labelPrenom.setText(String.format("%d€", (int) listeCourse.getPrixProduit()));
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
                            daoListeCourse.delete(listeCourse);
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
                        Intent monIntent = new Intent(MainActivity.this, View_ajout_recette.class);
                        monIntent.putExtra("idRecette", listeCourse.getNomListe());
                        startActivity(monIntent);
                    }
                });

                //Spinner
                TableRow rowS = new TableRow(this);
                rowS.setGravity(Gravity.CENTER_VERTICAL);
                rowS.setWeightSum(8);
                TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        8f
                );
                String listeS = listeCourse.getListeProduit();
                Log.e("Liste Produit", listeS +"ok ");
                ObjectMapper mapper = new ObjectMapper();
                List<String> listS = new ArrayList<>();
                Spinner snpProduit;

                snpProduit = new Spinner(this);
                snpProduit.setLayoutParams(paramSpinner);
                try {
                    List<ListeCourse> participantJsonList = Arrays.asList(mapper.readValue(listeS, ListeCourse[].class));
                    for (ListeCourse listeCourse2 : participantJsonList){
                        listS.add(listeCourse2.getNomListe().toString());

                    }
                    CustomAdapter adapter = new CustomAdapter(MainActivity.this,
                            R.layout.spinner_layout_ressource,
                            R.id.textView_item_name,
                            R.id.quantiter,
                            R.id.prix,
                            participantJsonList);
                    snpProduit.setAdapter(adapter);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> asnpProduits = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listS);
                asnpProduits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rowS.addView(snpProduit);

                containerListecourse.addView(row);
                containerListecourse.addView(rowS);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}