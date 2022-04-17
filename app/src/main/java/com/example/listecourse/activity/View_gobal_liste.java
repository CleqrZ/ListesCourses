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
import com.example.listecourse.model.globalModel;
import com.example.listecourse.tools.DatabaseLinker;

public class View_gobal_liste extends AppCompatActivity {
    private TableLayout containerGobale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_globale);
        containerGobale = findViewById(R.id.container_golbale);
        DatabaseLinker linker = new DatabaseLinker(this);
        globalModel.createGlobal(linker,containerGobale,this);
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
                Intent intentProduitCourse = new Intent(View_gobal_liste.this, View_produit.class);
                startActivity(intentProduitCourse);
                return true;
            case R.id.recette:
                Intent intentRecetteCourse = new Intent(View_gobal_liste.this, View_recette.class);
                startActivity(intentRecetteCourse);
                return true;
            case R.id.listeCourse:
                Intent intentListeCourse = new Intent(View_gobal_liste.this, View_ListeCourse.class);
                startActivity(intentListeCourse);
                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }
}
