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
import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.model.ListeCourseModel;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class View_ListeCourse extends AppCompatActivity {
    private TableLayout containerListeCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listecourse);
        containerListeCourse = findViewById(R.id.container_listecourse);
        Button buttonAjoutsListeCourse = findViewById(R.id.button_ajout_listecourse);
        buttonAjoutsListeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentListeCourse = new Intent(View_ListeCourse.this, View_ajout_ListeCourse.class);
                startActivity(intentListeCourse);
            }
        });
        DatabaseLinker linker = new DatabaseLinker(this);
        ListeCourseModel.createListeCourse(linker, containerListeCourse, this);
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
                Intent intentProduitCourse = new Intent(View_ListeCourse.this, View_produit.class);
                startActivity(intentProduitCourse);
                return true;
            case R.id.recette:
                Intent intentRecetteCourse = new Intent(View_ListeCourse.this, View_recette.class);
                startActivity(intentRecetteCourse);
                return true;
            case R.id.listeCourse:
                Intent intentListeCourse = new Intent(View_ListeCourse.this, View_ListeCourse.class);
                startActivity(intentListeCourse);
                return true;
            case R.id.Magasin:
                Intent intentGlobale = new Intent(View_ListeCourse.this, View_gobal_liste.class);
                startActivity(intentGlobale);
                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }

}
