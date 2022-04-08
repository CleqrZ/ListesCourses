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
    private double prix = 0;
    private Button validateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_recette);

        //init recette cas 1 : Création
        Recette recette = null;

        //init recette cas 2 : Modif
        Intent intent = this.getIntent();
        idRecette = intent.getIntExtra("idRecette",0);
        DatabaseLinker linker = new DatabaseLinker(this);
        //recup recette  avec id envoyer en extra
        try {
            Dao<Recette, Integer> daoRecette= linker.getDao(Recette.class);
            recette= daoRecette.queryForId(idRecette);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //EditText :affichage/edition. nom recette
        editLabel = findViewById(R.id.edit_label);

        //TextView :Affichage prix estimer de la recette
        prix_recette = findViewById(R.id.prix_recette);

        //TableLayout :Affichage produit dans spinner
        containerSpinner = findViewById(R.id.container_produit_ajout_r);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Button : pour ajouter un produit a notre recette appelle setSpinner voir plus bas
        Button button_ajout_produit = findViewById(R.id.button_ajout_produit);
        button_ajout_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creation spinner et compteur qte
                setSpinnerProduit(null,0);
            }
        });
        //button : pour valider les changement ou la création un recette
        validateButton = findViewById(R.id.button_validate);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set le prix avant de valider pour s'assurer qu'il soit corect
                setPrixRecette();
                //création ou modif en bdd
                modifInfos();
            }
        });
        linker.close();

        //si recette n'est pas nul alors init des valeur de la recette selectionner
        if(recette != null){
            // affichage Nom recette EditText
            editLabel.setText(recette.getLibelleRecette());
            try {
                //recupe produit avec Qte
                List<RecetteProduit> recetteProduitListe = recette.getListeProduit(this);
                for (RecetteProduit recetteProduit : recetteProduitListe){
                    Log.e("teste QTE :", String.valueOf(recetteProduit.getQte()));
                    setSpinnerProduit(recetteProduit.getIdProduitR(),recetteProduit.getQte());
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //Affichage prix TextView
            prix_recette.setText(String.valueOf(recette.getPrixListeProduit())+"€");
        }
    }

    //Function de création et modification
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
    //Function de création des tablbRow avec spinner et Edittext Qte
    //valeur utiliser a cas de produit selectionner
    private Produit produit;
    public void setSpinnerProduit(Produit produit, int prodQte){
        DatabaseLinker linker = new DatabaseLinker(this);
        try {
            //TableRow 1 : elle contien un spinner init avec tout les produit en bdd
            TableRow rowS = new TableRow(this);
            rowS.setGravity(Gravity.CENTER_VERTICAL);
            rowS.setWeightSum(8);
            //TableRow 2 : contien deux button et un edit text pour la qte
            TableRow rowQ = new TableRow(this);
            rowS.setGravity(Gravity.CENTER_VERTICAL);
            rowS.setWeightSum(8);
            //EditText : Quantiter
            TableRow.LayoutParams paramEdit = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f);
            EditText view = new EditText(this);
            view.setLayoutParams(paramEdit);
            view.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            view.setText(String.valueOf(prodQte));
            rowQ.addView(view);
            //Spinner :initialisation Spinner
            TableRow.LayoutParams paramSpinner = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                8f);
            Spinner snpProduit;
            snpProduit = new Spinner(this);
            snpProduit.setLayoutParams(paramSpinner);
            snpProduit.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onNothingSelected(AdapterView<?> arg0) { }
                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                            // code appelé lorsqu'un item est sélectionné
                            setPrixRecette();
                                view.setText("0");
                                Produit prod = (Produit) snpProduit.getSelectedItem();
                                Toast leToast = Toast.makeText(View_ajout_recette.this,
                                        "item sélectionné : " + prod.getLibelleProduit(), Toast.LENGTH_LONG);
                                leToast.show();
                            }
                    });
            //Spinner : récupération de valeur produit a afficher dans le spinner
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

            //si Modif
            if (produit != null){
                for (int pos =0;pos<adapter.getCount();pos++){
                    //si id produit donnée a la fontcion corresspond a un prod du spinner il l'affiche
                    if (produit.getIdProduit() == adapter.getItemId(pos)){
                        snpProduit.setSelection(pos);
                    }
                }
            }

            //Button 1: button plus pour quantiter
            TableRow.LayoutParams paramButton = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    3f
            );
            ImageButton buttonPlus = new ImageButton(this);
            buttonPlus.setLayoutParams(paramButton);
            buttonPlus.setImageResource(R.drawable.ajouter);
            buttonPlus.setBackground(null);
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText()))+1));
                    setPrixRecette();
                }
            });
            rowQ.addView(buttonPlus);

            //Button 2 : button moins pour quantiter
            ImageButton buttonMoins = new ImageButton(this);
            buttonMoins.setLayoutParams(paramButton);
            buttonMoins.setImageResource(R.drawable.retirer);
            buttonMoins.setBackground(null);
            rowQ.addView(buttonMoins);
            buttonMoins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText()))-1));
                    setPrixRecette();
                }
            });

            //button pour delete la ligne en cas erreur
            ImageButton deleteLigne = new ImageButton(this);
            deleteLigne.setLayoutParams(paramEdit);
            deleteLigne.setImageResource(R.drawable.delete);
            deleteLigne.setBackground(null);
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

            //ajout au container
            containerSpinner.addView(rowS);
            containerSpinner.addView(rowQ);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //function qui recupére les valuer Produit contunu dans les spinner de containerspinner
    public List<Produit> getListSpinne() {
        //List<>: init list produit
        List<Produit> produitList =new ArrayList<>();
        // int : nb element TableLayout pour les parcourire
        int childParts = containerSpinner.getChildCount();
        if (containerSpinner != null) {//sinon ne pas parcourire
            for (int i = 0; i < childParts; i++) {
                View viewChild = containerSpinner.getChildAt(i);//recupe tableRow
                if (viewChild instanceof TableRow) {
                    int rowChildParts = ((TableRow) viewChild).getChildCount();//compte element row
                    for (int j = 0; j < rowChildParts; j++) {
                        View viewChild2 = ((TableRow) viewChild).getChildAt(j);//recupe ele row Spinne
                        if (viewChild2 instanceof Spinner) {
                            // get text from edit text
                            Spinner text = ((Spinner) viewChild2);//recupe produit du spinner
                            Produit produitT = (Produit) text.getSelectedItem();
                                produitList.add(produitT);

                        }
                    }
                }
            }
        }
        return produitList;
    }

    //function qui recup les QTE
    public List<Integer> getListSpinneqte() {
        //meme shéma que function getspinner mais pour qte avec les editText
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

    //function qui calcul le prix des produit selectionner selon la qte selcy
   public void setPrixRecette(){
        //init prix a 0 pour eviter les buge et recalcul tout les element du tableLayout a chaque appelle
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



