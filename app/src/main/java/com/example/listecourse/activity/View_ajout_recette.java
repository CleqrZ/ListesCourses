package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listecourse.R;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class View_ajout_recette extends AppCompatActivity {
    private int idRecette = 0;
    private EditText editLabel;
    private EditText editQuantiter;
    private EditText editPrix;

    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_recette);
        validateButton = findViewById(R.id.button_validate);
        Intent intent = this.getIntent();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idRecette = intent.getIntExtra("idRecette",0);
        Recette recette=null;
        DatabaseLinker linker = new DatabaseLinker(this);

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
        editQuantiter = findViewById(R.id.edit_quantiter);
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
        Log.i("", label);
        //String quantiter = editQuantiter.getText().toString();
        String prix = editPrix.getText().toString();
        DatabaseLinker linker = new DatabaseLinker(this);
        Recette recette;
        try {
            Dao<Recette, Integer> daoRecette= linker.getDao(Recette.class);
            if (idRecette != 0){
                recette = daoRecette.queryForId(idRecette);
                recette.setLibelleRecette(label);
                //produit.setQuantiter(quantiter);
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

}
