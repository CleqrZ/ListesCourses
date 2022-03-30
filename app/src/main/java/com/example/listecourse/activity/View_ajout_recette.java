package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listecourse.R;
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

public class View_ajout_recette extends AppCompatActivity {
    private int idRecette = 0;
    private EditText editLabel;
    private EditText editPrix;
    private TableLayout containerSpinner;

    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_recette);
        Intent intent = this.getIntent();
        containerSpinner = findViewById(R.id.container_produit_ajout_r);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idRecette = intent.getIntExtra("idRecette",0);
        Recette recette = null;
        DatabaseLinker linker = new DatabaseLinker(this);
        Button button_ajout_produit = findViewById(R.id.button_ajout_produit);
        button_ajout_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSpinnerProduit();
            }
        });
        validateButton = findViewById(R.id.button_validate);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifInfos();
            }
        });
        try {
            Dao<Recette, Integer> daoRecette= linker.getDao(Recette.class);
            recette= daoRecette.queryForId(idRecette);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        linker.close();
        editLabel = findViewById(R.id.edit_label);
        editPrix = findViewById(R.id.edit_prix);
        if(recette != null){
            editLabel.setText(recette.getLibelleRecette());
            //editQuantiter.setText(recette.getQuantiter());
            editPrix.setText(String.valueOf((int) recette.getPrixListeProduit()));

            Log.e("bouton", "Produit value : "+ recette.getIdRecette()+" "+/* recette.getQuantiter()+*/" "+recette.getPrixListeProduit());
        }
    }

    public void modifInfos(){
        String label = editLabel.getText().toString();
        String prix = editPrix.getText().toString();
        DatabaseLinker linker = new DatabaseLinker(this);
        Recette recette;
        try {
            Dao<Recette, Integer> daoRecette= linker.getDao(Recette.class);
            if (idRecette != 0){
                recette = daoRecette.queryForId(idRecette);
                recette.setLibelleRecette(label);
                recette.setPrixListeProduit(Double.parseDouble(prix));
                daoRecette.update(recette);
            }else{
                if (label.matches("") || prix.matches("")  ) {
                    Log.e("création : ", "erreur");
                    Toast leToast = Toast.makeText(View_ajout_recette.this,
                            "Remplir touts les champs", Toast.LENGTH_LONG);
                    leToast.show();
                }else {
                    recette = new Recette(label,Double.parseDouble(prix));
                    daoRecette.create(recette);
                    Intent main = new Intent( View_ajout_recette.this, MainActivity.class);
                    startActivity(main);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setSpinnerProduit(){
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            TableRow rowS = new TableRow(this);
            rowS.setGravity(Gravity.CENTER_VERTICAL);
            rowS.setWeightSum(8);
            TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                8f
        );
        Spinner snpProduit;
        snpProduit = new Spinner(this);
        snpProduit.setLayoutParams(paramSpinner);
        Dao<Produit, Integer> daoProduit= linker.getDao(Produit.class);
        List<Produit>participantJsonList = daoProduit.queryForAll();
        CustomAdapter adapter = new CustomAdapter(this,
                R.layout.spinner_layout_ressource,
                R.id.textView_item_name,
                R.id.quantiter,
                R.id.prix,
                participantJsonList);
        snpProduit.setAdapter(adapter);
        rowS.addView(snpProduit);
        containerSpinner.addView(rowS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**public double setPrixRecette(List<Produit> listep){

    }*/
}
