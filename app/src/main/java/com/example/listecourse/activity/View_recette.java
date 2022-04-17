package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listecourse.R;
import com.example.listecourse.model.RecetteModel;
import com.example.listecourse.tools.DatabaseLinker;

public class View_recette extends AppCompatActivity {

    private TableLayout containerRecette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recette);
        containerRecette = findViewById(R.id.container_Recette);
        Button buttonAjoutRecette = findViewById(R.id.button_ajout_Recette);
        buttonAjoutRecette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRecette = new Intent(View_recette.this, View_ajout_recette.class);
                startActivity(intentRecette);
            }
        });
        DatabaseLinker linker = new DatabaseLinker(this);
        RecetteModel.createRecette(linker,containerRecette,this);
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
                Intent intentProduitCourse = new Intent(View_recette.this, View_produit.class);
                startActivity(intentProduitCourse);
                return true;
            case R.id.recette:
                Intent intentRecetteCourse = new Intent(View_recette.this, View_recette.class);
                startActivity(intentRecetteCourse);
                return true;
            case R.id.listeCourse:
                Intent intentListeCourse = new Intent(View_recette.this, View_ListeCourse.class);
                startActivity(intentListeCourse);
                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }
}
