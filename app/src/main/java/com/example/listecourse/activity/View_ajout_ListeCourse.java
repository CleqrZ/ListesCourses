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
import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class View_ajout_ListeCourse extends AppCompatActivity {
    private int idListe = 0;
    private EditText editLabel;
    private EditText editQuantiter;
    private EditText editPrix;

    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_listecourse);
        validateButton = findViewById(R.id.button_validate);
        Intent intent = this.getIntent();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idListe = intent.getIntExtra("idListe",0);
        ListeCourse listeCourse=null;
        DatabaseLinker linker = new DatabaseLinker(this);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifInfos();

            }
        });
        try {
            Dao<ListeCourse, Integer> daoListeCourse= linker.getDao(ListeCourse.class);
            listeCourse= daoListeCourse.queryForId(idListe);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        linker.close();
        editLabel = findViewById(R.id.edit_label);
        editQuantiter = findViewById(R.id.edit_quantiter);
        editPrix = findViewById(R.id.edit_prix);
        if(listeCourse != null){
            editLabel.setText(listeCourse.getNomListe());
            editPrix.setText(String.valueOf(listeCourse.getPrixProduit())+"€");

            Log.e("bouton", "Liste value : "+ listeCourse.getNomListe()+" "+listeCourse.getPrixProduit());
        }
    }
    public void modifInfos(){
        String label = editLabel.getText().toString();
        String quantiter = editQuantiter.getText().toString();
        String prix = editPrix.getText().toString();
        DatabaseLinker linker = new DatabaseLinker(this);
        ListeCourse listeCourse;
        try {
            Dao<ListeCourse, Integer> daoListeCourse= linker.getDao(ListeCourse.class);
            if (idListe != 0){
                listeCourse = daoListeCourse.queryForId(idListe);
                listeCourse.setNomListe(label);
                listeCourse.setPrixProduit(Double.parseDouble(prix));
                daoListeCourse.update(listeCourse);
            }else{
                if (label.matches("") || quantiter.matches("") || prix.matches("")  ) {
                    Log.e("création : ", "erreur");
                    Toast leToast = Toast.makeText(View_ajout_ListeCourse.this,
                            "Remplir touts les champs", Toast.LENGTH_LONG);
                    leToast.show();
                }else {
                    listeCourse = new ListeCourse(label,Double.parseDouble(prix));
                    daoListeCourse.create(listeCourse);
                    Intent main = new Intent( View_ajout_ListeCourse.this, MainActivity.class);
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
        Intent monIntent = new Intent(View_ajout_ListeCourse.this, MainActivity.class);
        startActivity(monIntent);
    }
}
