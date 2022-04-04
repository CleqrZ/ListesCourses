package com.example.listecourse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listecourse.R;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.bdd.RecetteProduit;
import com.example.listecourse.tools.CustomAdapter;
import com.example.listecourse.tools.DatabaseLinker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View_ajout_recette extends AppCompatActivity {
    private int idRecette = 0;
    private EditText editLabel;
    private TableLayout containerSpinner;
    private TextView prix_recette;
    private double prix;
    private Button validateButton;
    private Produit produit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_recette);
        Intent intent = this.getIntent();
        prix = 0;
        containerSpinner = findViewById(R.id.container_produit_ajout_r);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        idRecette = intent.getIntExtra("idRecette",0);
        prix_recette = findViewById(R.id.prix_recette);
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
                setPrixRecette();
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
        if(recette != null){
            editLabel.setText(recette.getLibelleRecette());
            //editQuantiter.setText(recette.getQuantiter());
            prix_recette.setText(String.valueOf(recette.getPrixListeProduit())+"€");

            Log.e("bouton", "Produit value : "+ recette.getIdRecette()+" "+/* recette.getQuantiter()+*/" "+recette.getPrixListeProduit());
        }
    }

    public void modifInfos(){
        String label = editLabel.getText().toString();
        DatabaseLinker linker = new DatabaseLinker(this);
        Recette recette;
        try {
            Dao<Recette, Integer> daoRecette= linker.getDao(Recette.class);
            Dao<RecetteProduit, Integer> daoRecetteProd= linker.getDao(RecetteProduit.class);
            if (idRecette != 0){
                recette = daoRecette.queryForId(idRecette);
                recette.setLibelleRecette(label);
                recette.setPrixListeProduit(prix);
                daoRecette.update(recette);
            }else{
                if (label.matches("") || prix == 0  ) {
                    Log.e("création : ", "erreur");
                    Toast leToast = Toast.makeText(View_ajout_recette.this,
                            "Remplir touts les champs", Toast.LENGTH_LONG);
                    leToast.show();
                }else {
                    recette = new Recette(label,prix);
                    daoRecette.create(recette);
                    List<Produit> produitList = getListSpinne();
                    List<RecetteProduit> produitListRe = new ArrayList<>();
                    List<Integer> qteListe = getListSpinneqte();
                    if( produitList.size() == qteListe.size()){
                        for (int cpt = 0 ; cpt<produitList.size();cpt++){
                           RecetteProduit val = new RecetteProduit(produitList.get(cpt), recette,qteListe.get(cpt));
                           daoRecetteProd.create(val);
                           produitListRe.add(val);
                        }
                    }
                    recette.setListe(produitListRe);
                    Intent main = new Intent( View_ajout_recette.this, MainActivity.class);
                    startActivity(main);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void setSpinnerProduit(){
        int unite = 0;
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            TableRow rowS = new TableRow(this);
            rowS.setGravity(Gravity.CENTER_VERTICAL);
            rowS.setWeightSum(8);
            TableRow rowQ = new TableRow(this);
            rowS.setGravity(Gravity.CENTER_VERTICAL);
            rowS.setWeightSum(8);
            TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                8f);
            TableRow.LayoutParams paramButton = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    3f
            );
            ImageButton buttonPlus = new ImageButton(this);
            buttonPlus.setLayoutParams(paramButton);
            buttonPlus.setImageResource(R.drawable.ajouter);
            buttonPlus.setBackground(null);
            rowQ.addView(buttonPlus);

            TableRow.LayoutParams paramEdit = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f);
            EditText view = new EditText(this);
            view.setLayoutParams(paramEdit);
            view.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            view.setText("0");
            rowQ.addView(view);
            ImageButton buttonMoins = new ImageButton(this);
            buttonMoins.setLayoutParams(paramButton);
            buttonMoins.setImageResource(R.drawable.retirer);
            buttonMoins.setBackground(null);
            rowQ.addView(buttonMoins);

            ImageButton deleteLigne = new ImageButton(this);
            deleteLigne.setLayoutParams(paramEdit);
            deleteLigne.setImageResource(R.drawable.delete);
            deleteLigne.setBackground(null);

            Spinner snpProduit;
            snpProduit = new Spinner(this);
            snpProduit.setLayoutParams(paramSpinner);
            snpProduit.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onNothingSelected(AdapterView<?> arg0) { }
                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                            // code appelé lorsqu'un item est sélectionné
                            setPrixRecette();
                            int uniter;
                            if(produit != null){
                                uniter = Integer.parseInt(String.valueOf(view.getText()));
                                if (uniter != 0){
                                    view.setText("0");
                                }
                            }
                            Produit prod = (Produit)snpProduit.getSelectedItem();
                            Toast leToast = Toast.makeText(View_ajout_recette.this,
                                    "item sélectionné : " + prod.getLibelleProduit(), Toast.LENGTH_LONG);
                            leToast.show();

                        }
                    });
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
            buttonMoins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText()))-1));
                    produit = (Produit) snpProduit.getSelectedItem();
                    setPrixRecette();
                }
            });
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText()))+1));
                    produit = (Produit) snpProduit.getSelectedItem();
                    setPrixRecette();
                }
            });
            deleteLigne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rowS.getParent() != null) {
                        ((ViewGroup)rowS.getParent()).removeView(rowS); // <- fix
                    }
                    if(rowQ.getParent() != null) {
                        ((ViewGroup)rowQ.getParent()).removeView(rowQ); // <- fix
                    }
                        setPrixRecette();
                }
            });
            rowQ.addView(deleteLigne);
            containerSpinner.addView(rowS);
            containerSpinner.addView(rowQ);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public List<Produit> getListSpinne() {
        List<Produit> produitList =new ArrayList<>();
        int childParts = containerSpinner.getChildCount();
        if (containerSpinner != null) {
            for (int i = 0; i < childParts; i++) {
                View viewChild = containerSpinner.getChildAt(i);
                if (viewChild instanceof TableRow) {
                    int rowChildParts = ((TableRow) viewChild).getChildCount();
                    for (int j = 0; j < rowChildParts; j++) {
                        View viewChild2 = ((TableRow) viewChild).getChildAt(j);
                        if (viewChild2 instanceof Spinner) {
                            // get text from edit text
                            Spinner text = ((Spinner) viewChild2);
                            Produit produitT = (Produit) text.getSelectedItem();
                            Log.e("teste getliste :", produitT.getLibelleProduit());
                                produitList.add(produitT);

                        }
                    }
                }
            }
        }
        return produitList;
    }


    public List<Integer> getListSpinneqte() {
        List<Integer> listeQte = new ArrayList<>();
        int childParts = containerSpinner.getChildCount();
        if (containerSpinner != null) {
            for (int i = 0; i < childParts; i++) {
                View viewChild = containerSpinner.getChildAt(i);
                if (viewChild instanceof TableRow) {
                    int rowChildParts = ((TableRow) viewChild).getChildCount();
                    for (int j = 0; j < rowChildParts; j++) {
                        View viewChild2 = ((TableRow) viewChild).getChildAt(j);
                        if (viewChild2 instanceof EditText) {
                            // get text from edit text
                            EditText text = ((EditText) viewChild2);
                            listeQte.add(Integer.valueOf(String.valueOf(text.getText())));
                        }
                    }
                }
            }
        }
        return listeQte;
    }


   public void setPrixRecette(){
        prix = 0;
        List<Produit> produitList = getListSpinne();
        List<Integer> qteListe = getListSpinneqte();
        if(produitList !=null) {
            for (int cpt = 0; cpt < produitList.size(); cpt++) {
                double prixP = produitList.get(cpt).getPrixProduit();
                int uniter = qteListe.get(cpt);
                prix = prix + (uniter * prixP);
           }
       }
       prix_recette.setText(prix+"€");
       }
   }



