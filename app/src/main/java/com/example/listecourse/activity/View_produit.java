package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.listecourse.model.ProduitModel;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class View_produit extends AppCompatActivity {
    private TableLayout containerProduits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_produit);
        containerProduits = findViewById(R.id.container_produit);
        Button buttonAjoutProduits = findViewById(R.id.button_ajout_produit);
        buttonAjoutProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProduits = new Intent(View_produit.this, View_ajout_produit.class);
                startActivity(intentProduits);
            }
        });
        DatabaseLinker linker = new DatabaseLinker(this);
        ProduitModel.createProduit(linker, containerProduits, this);
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
                Intent intentProduitCourse = new Intent(View_produit.this, View_produit.class);
                startActivity(intentProduitCourse);
                return true;
            case R.id.recette:
                Intent intentRecetteCourse = new Intent(View_produit.this, View_recette.class);
                startActivity(intentRecetteCourse);
                return true;
            case R.id.listeCourse:
                Intent intentListeCourse = new Intent(View_produit.this, View_ListeCourse.class);
                startActivity(intentListeCourse);
                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }
}