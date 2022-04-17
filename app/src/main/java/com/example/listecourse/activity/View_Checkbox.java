package com.example.listecourse.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listecourse.R;
import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.bdd.ListeCourseProduit;
import com.example.listecourse.bdd.ListeCourseRecette;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.bdd.RecetteProduit;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class View_Checkbox extends AppCompatActivity {
    private TableLayout containerCheckbox;
    private Button buttonFinCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_checkbox);
        containerCheckbox = this.findViewById(R.id.container_magasin);
        //recupération liste en magasin
        Intent intent = this.getIntent();
        Integer idListe = intent.getIntExtra("idListe", 0);
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            Dao<ListeCourse,Integer> daoListeCourse = linker.getDao(ListeCourse.class);
            ListeCourse listeCourse = daoListeCourse.queryForId(idListe);
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setWeightSum(8);

            TableRow.LayoutParams param = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    8f
            );
            //label Liste

            TextView labelNom = new TextView(this);
            labelNom.setLayoutParams(param);
            labelNom.setText("Nom de la liste : "+listeCourse.getNomListe());
            labelNom.setTypeface(null, Typeface.BOLD);
            row.addView(labelNom);
            TableRow.LayoutParams paramp = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    0f
            );
            //prix liste
            TextView prixListe = new TextView(this);
            prixListe.setLayoutParams(paramp);
            prixListe.setText(String.valueOf(listeCourse.getPrixProduit()) + "€");
            row.addView(prixListe);
            containerCheckbox.addView(row);
            //Produit contenu dans les recette de la liste
            List<ListeCourseRecette> listeRecette = listeCourse.getListeR(this);
            for (ListeCourseRecette recetteL : listeRecette){
                Recette recette = recetteL.getIdRecetteR();
                List<RecetteProduit> listProdRec = recette.getListeProduit(this);
                TextView nomRecette = new TextView(this);
                nomRecette.setText("Recette de votre Liste : "+ recette.getLibelleRecette());
                nomRecette.setTypeface(null, Typeface.BOLD);
                containerCheckbox.addView(nomRecette);
                for (RecetteProduit recetteProduit : listProdRec){
                    for (int qte = 0; qte<recetteProduit.getQte();qte++){
                        TableRow rowProdRecette = new TableRow(this);
                        rowProdRecette.setGravity(Gravity.CENTER_VERTICAL);
                        rowProdRecette.setWeightSum(8);
                        Produit prodRecette = recetteProduit.getIdProduitR();
                        //nom produit d'un recette
                        TextView labelProdR = new TextView(this);
                        labelProdR.setText(prodRecette.getLibelleProduit());
                        rowProdRecette.addView(labelProdR);
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setText(String.valueOf(prodRecette.getPrixProduit()) + "€");
                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CheckBox cb = (CheckBox) view;
                                if(cb.isChecked()){
                                    ((ViewGroup) rowProdRecette.getParent()).removeView(rowProdRecette);

                                }
                            }
                        });
                        rowProdRecette.addView(checkBox);
                        containerCheckbox.addView(rowProdRecette);
                    }

                }

            }
            //Produit de la liste
            TextView nomRecette = new TextView(this);
            nomRecette.setText("Produits de votre Liste : ");
            nomRecette.setTypeface(null, Typeface.BOLD);
            containerCheckbox.addView(nomRecette);
            List<ListeCourseProduit> listeCourseProduits = listeCourse.getListeP(this);
            for (ListeCourseProduit produitListe : listeCourseProduits){
                for (int qte = 0; qte<produitListe.getQte();qte++) {
                    Produit produit = produitListe.getIdProduitP();
                    TableRow rowProdRecette = new TableRow(this);
                    rowProdRecette.setGravity(Gravity.CENTER_VERTICAL);
                    rowProdRecette.setWeightSum(8);
                    //nom produit d'un recette
                    TextView labelProdR = new TextView(this);
                    labelProdR.setText(produit.getLibelleProduit());
                    rowProdRecette.addView(labelProdR);
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setText(String.valueOf(produit.getPrixProduit()) + "€");
                    rowProdRecette.addView(checkBox);
                    containerCheckbox.addView(rowProdRecette);
                }
            }
        buttonFinCourse = findViewById(R.id.courseFinit);
            buttonFinCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent monIntent = new Intent( View_Checkbox.this ,MainActivity.class);
                    startActivity(monIntent);
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
