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
            default:
                return super.onOptionsItemSelected(_item);
        }
    }
    public void createListeCourse() {
        containerListeCourse.removeAllViews();
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            Dao<ListeCourse, Integer> daoListeCourse = linker.getDao(ListeCourse.class);
            List<ListeCourse> CourseListe = daoListeCourse.queryForAll();
            for (ListeCourse listeCourse : CourseListe) {
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

                TextView labelPrenom = new TextView(this);
                labelPrenom.setLayoutParams(param);
                labelPrenom.setText(String.valueOf(listeCourse.getPrixProduit())+"€");
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
                        Log.i("aaaaaaaaaaaaaaaa", "test");
                        Intent monIntent = new Intent(View_ListeCourse.this, View_ajout_ListeCourse.class);
                        monIntent.putExtra("idListeCourse", listeCourse.getIdListeCourse());
                        startActivity(monIntent);
                    }
                });
                containerListeCourse.addView(row);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
