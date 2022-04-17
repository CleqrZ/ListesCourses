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
import com.example.listecourse.model.ListeCourseModel;
import com.example.listecourse.model.ProduitModel;
import com.example.listecourse.model.RecetteModel;
import com.example.listecourse.tools.CustomAdapter;
import com.example.listecourse.tools.DatabaseLinker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TableLayout containerProduits;
    private TableLayout containerRecette;
    private Button buttonAjoutRecette;
    private TableLayout containerListecourse;
    private Button buttonAjoutListecourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.deleteDatabase("bdd.db");

        containerProduits = findViewById(R.id.container_produit);
        Button buttonAjoutProduits = findViewById(R.id.button_ajout_produit);
        buttonAjoutProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProduits = new Intent(MainActivity.this, View_ajout_produit.class);
                startActivity(intentProduits);
            }
        });

        DatabaseLinker linker = new DatabaseLinker(this);

        //appelle de la fonction create via ProduitModel
        ProduitModel.createProduit(linker, containerProduits, this);

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

        RecetteModel.createRecette(linker, containerRecette, this);
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
        ListeCourseModel.createListeCourse(linker, containerListecourse, this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem _item) {
        switch(_item.getItemId()){
            case R.id.produits:
                Intent intentProduitCourse = new Intent(MainActivity.this, View_produit.class);
                startActivity(intentProduitCourse);
                return true;
            case R.id.recette:
                Intent intentRecetteCourse = new Intent(MainActivity.this, View_recette.class);
                startActivity(intentRecetteCourse);
                return true;
            case R.id.listeCourse:
                Intent intentListeCourse = new Intent(MainActivity.this, View_ListeCourse.class);
                startActivity(intentListeCourse);
                return true;
            case R.id.Magasin:
                Intent intentGlobale = new Intent(MainActivity.this, View_gobal_liste.class);
                startActivity(intentGlobale);
                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }
}