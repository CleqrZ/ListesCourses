package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listecourse.R;
import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.bdd.ListeCourseRecette;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class View_Checkbox extends AppCompatActivity {
    private TableLayout containerCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_recette);
        containerCheckbox = this.findViewById(R.id.container_magasin);
        //init Liste cas 2 : Modif
        Intent intent = this.getIntent();
        Integer idListe = intent.getIntExtra("idListe", 0);
        DatabaseLinker linker = new DatabaseLinker(this);
        Dao<ListeCourse, Integer> daoListeCourse = null;
        try {
            daoListeCourse = linker.getDao(ListeCourse.class);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
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
            labelNom.setText(listeCourse.getNomListe());
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
            List<ListeCourseRecette> listeRecette = listeCourse.getListeR(this);
           /* for (ListeCourseRecette recetteL : listeRecette){
                Recette recette = recetteL.getIdRecetteR();

            }*/

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
