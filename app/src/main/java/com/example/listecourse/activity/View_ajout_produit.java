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
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class View_ajout_produit extends AppCompatActivity {
    private int idProduit = 0;
    private EditText editLabel;
    private EditText editQuantiter;
    private EditText editPrix;

    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_produit);
        validateButton = findViewById(R.id.button_validate);
        Intent intent = this.getIntent();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idProduit = intent.getIntExtra("idProduit",0);
        Produit produit=null;
        DatabaseLinker linker = new DatabaseLinker(this);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifInfos();

            }
        });
        try {
            Dao<Produit, Integer> daoProduit= linker.getDao(Produit.class);
            produit= daoProduit.queryForId(idProduit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        linker.close();
        editLabel = findViewById(R.id.edit_label);
        editQuantiter = findViewById(R.id.edit_quantiter);
        editPrix = findViewById(R.id.edit_prix);
        if(produit != null){
            editLabel.setText(produit.getLibelleProduit());
            editQuantiter.setText(produit.getQuantiter());
            editPrix.setText(String.valueOf((int) produit.getPrixProduit()));

            Log.e("bouton", "Produit value : "+ produit.getLibelleProduit()+" "+produit.getQuantiter()+" "+produit.getPrixProduit());
        }
    }
    public void modifInfos(){
        String label = editLabel.getText().toString();
        String quantiter = editQuantiter.getText().toString();
        String prix = editPrix.getText().toString();
        DatabaseLinker linker = new DatabaseLinker(this);
        Produit produit;
        try {
            Dao<Produit, Integer> daoProduit= linker.getDao(Produit.class);
            if (idProduit != 0){
                produit = daoProduit.queryForId(idProduit);
                produit.setLibelleProduit(label);
                produit.setQuantiter(quantiter);
                produit.setPrixProduit(Double.parseDouble(prix));
                daoProduit.update(produit);
            }else{
                if (label.matches("") || quantiter.matches("") || prix.matches("")  ) {
                    Log.e("création : ", "erreur");
                    Toast leToast = Toast.makeText(View_ajout_produit.this,
                            "Remplir touts les champs", Toast.LENGTH_LONG);
                    leToast.show();
                }else {
                    produit = new Produit(label,quantiter,Double.parseDouble(prix));
                    daoProduit.create(produit);
                    Intent main = new Intent( View_ajout_produit.this, MainActivity.class);
                    startActivity(main);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        backToMainActivity();
        return true;
    }
    public void backToMainActivity() {
        Intent monIntent = new Intent(View_ajout_produit.this, MainActivity.class);
        startActivity(monIntent);
    }
}