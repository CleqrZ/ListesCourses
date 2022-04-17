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
import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.bdd.ListeCourseProduit;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.bdd.RecetteProduit;
import com.example.listecourse.tools.CustomAdapter;
import com.example.listecourse.tools.CustomAdapterRecette;
import com.example.listecourse.tools.DatabaseLinker;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class View_ajout_ListeCourse extends AppCompatActivity {
    private int idListe = 0;
    private EditText editLabel;
    private TableLayout containerSpinner;
    private TableLayout containerSpinner2;
    private TextView prix_recette;
    private double prix = 0;
    private Button validateButton;
    private List<Produit> produitList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ajout_listecourse);

        //init liste cas 1 : Création
        ListeCourse listeCourse = null;

        //init recette cas 2 : Modif
        Intent intent = this.getIntent();
        idListe = intent.getIntExtra("idListe",0);
        DatabaseLinker linker = new DatabaseLinker(this);

        //recup ListeCourse  avec id envoyer en extra
        try {
            Dao<ListeCourse, Integer> daoListeCourse= linker.getDao(ListeCourse.class);
            listeCourse= daoListeCourse.queryForId(idListe);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //EditText :affichage/edition. nom recette
        editLabel = findViewById(R.id.edit_label);

        //TextView :Affichage prix estimer de la recette
        prix_recette = findViewById(R.id.prix_recette);

        //TableLayout :Affichage produit dans spinner
        containerSpinner = findViewById(R.id.container_produit_ajout_l);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Button : pour ajouter un produit a notre liste appelle setSpinner voir plus bas
        Button button_ajout_produit = findViewById(R.id.button_ajout_produit);
        button_ajout_produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creation spinner et compteur qte
                setSpinnerProduit(null,0);
            }
        });

        //TableLayout :Affichage recette dans spinner
        containerSpinner2 = findViewById(R.id.container_recette_ajout_l);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Button : pour ajouter une recette a notre liste appelle setSpinner voir plus bas
        Button button_ajout_recette = findViewById(R.id.button_ajout_recette);
        button_ajout_recette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creation spinner et compteur qte
                setSpinnerRecette(null,0);
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

        //si listeCourse n'est pas nul alors init des valeur de la recette selectionner
        if(listeCourse != null){
            // affichage Nom recette EditText
            editLabel.setText(listeCourse.getNomListe());
            try {
                //recupe produit avec Qte
                List<ListeCourseProduit> recetteProduitListe = listeCourse.getListeP(this);
                for (ListeCourseProduit ProduitListe : recetteProduitListe){
                    Log.e("teste QTE :", String.valueOf(ProduitListe.getQte()));
                    setSpinnerProduit(ProduitListe.getIdProduitP(),ProduitListe.getQte());
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //Affichage prix TextView
            prix_recette.setText(String.valueOf(listeCourse.getPrixProduit())+"€");
        }
    }

    //Function de création et modification
    public void modifInfos(){
        String label = editLabel.getText().toString();
        DatabaseLinker linker = new DatabaseLinker(this);
        ListeCourse listeCourse;
        try {
            Dao<ListeCourse, Integer> daoListeCourse= linker.getDao(ListeCourse.class);
            Dao<ListeCourseProduit, Integer> daoListeCourseProduit = linker.getDao(ListeCourseProduit.class);
            if (idListe != 0) {
                listeCourse = daoListeCourse.queryForId(idListe);
                listeCourse.setNomListe(label);
                listeCourse.setPrixProduit(prix);
                List<Produit> produitList = getListSpinne();
                List<ListeCourseProduit> produitListRe = new ArrayList<>();
                List<Integer> qteListe = getListSpinneqte();
                Dao<ListeCourseProduit, Integer> ListProduitDao = linker.getDao(ListeCourseProduit.class);
                List<ListeCourseProduit> ProduitList = ListProduitDao.queryForAll();
                for (ListeCourseProduit listprod : ProduitList) {
                    if (listprod.getIdListeCourseP().getIdListeCourse() == listeCourse.getIdListeCourse()) {
                        ListProduitDao.delete(listprod);
                    }
                }
                if( produitList.size() == qteListe.size()){
                    for (int cpt = 0 ; cpt<produitList.size();cpt++){
                        ListeCourseProduit val = new ListeCourseProduit(produitList.get(cpt), listeCourse,qteListe.get(cpt));
                        daoListeCourseProduit.create(val);
                    }
                }
                daoListeCourse.update(listeCourse);
            } else if (label.matches("") || prix == 0  ) {
                Toast leToast = Toast.makeText(com.example.listecourse.activity.View_ajout_ListeCourse.this,
                        "Remplir touts les champs", Toast.LENGTH_LONG);
                leToast.show();
            }else {
                listeCourse = new ListeCourse(label,prix);
                daoListeCourse.create(listeCourse);
                List<Produit> produitList = getListSpinne();
                List<ListeCourseProduit> produitListRe = new ArrayList<>();
                List<Integer> qteListe = getListSpinneqte();
                if( produitList.size() == qteListe.size()){
                    for (int cpt = 0 ; cpt<produitList.size();cpt++){
                        ListeCourseProduit val = new ListeCourseProduit(produitList.get(cpt), listeCourse,qteListe.get(cpt));
                        daoListeCourseProduit.create(val);
                    }
                }
            }

            Intent main = new Intent( com.example.listecourse.activity.View_ajout_ListeCourse.this, MainActivity.class);
            startActivity(main);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //Function de création des tablbRow avec spinner et Edittext Qte
    //valeur utiliser a cas de produit selectionner
    private Produit produit;
    public void setSpinnerProduit(Produit produit, int prodQte) {
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
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }

                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                            // code appelé lorsqu'un item est sélectionné
                            setPrixRecette();
                            view.setText(String.valueOf(prodQte));
                            Produit prod = (Produit) snpProduit.getSelectedItem();
                            Toast leToast = Toast.makeText(com.example.listecourse.activity.View_ajout_ListeCourse.this,
                                    "item sélectionné : " + prod.getLibelleProduit(), Toast.LENGTH_LONG);
                            leToast.show();
                        }
                    });
            //Spinner : récupération de valeur produit a afficher dans le spinner
            Dao<Produit, Integer> daoProduit = linker.getDao(Produit.class);
            List<Produit> participantJsonList = daoProduit.queryForAll();
            CustomAdapter adapter = new CustomAdapter(this,
                    R.layout.spinner_layout_ressource,
                    R.id.textView_item_name,
                    R.id.quantiter,
                    R.id.prix,
                    participantJsonList);
            snpProduit.setAdapter(adapter);
            rowS.addView(snpProduit);

            //si Modif
            if (produit != null) {
                for (int pos = 0; pos < adapter.getCount(); pos++) {
                    //si id produit donnée a la fontcion corresspond a un prod du spinner il l'affiche
                    if (produit.getIdProduit() == adapter.getItemId(pos)) {
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
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText())) + 1));
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
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText())) - 1));
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

                    if (rowS.getParent() != null) {
                        ((ViewGroup) rowS.getParent()).removeView(rowS); // <- fix
                    }
                    if (rowQ.getParent() != null) {
                        ((ViewGroup) rowQ.getParent()).removeView(rowQ); // <- fix
                    }
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
        produitList.clear();
        //List<>: init list produit
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

    public void setSpinnerRecette(Recette recette, int prodQte) {
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
            Spinner snpRecette;
            snpRecette = new Spinner(this);
            snpRecette.setLayoutParams(paramSpinner);
            snpRecette.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }

                        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                            // code appelé lorsqu'un item est sélectionné
                            setPrixRecette();
                            view.setText(String.valueOf(prodQte));
                            Recette recet = (Recette) snpRecette.getSelectedItem();
                            Toast leToast = Toast.makeText(com.example.listecourse.activity.View_ajout_ListeCourse.this,
                                    "item sélectionné : " + recet.getLibelleRecette(), Toast.LENGTH_LONG);
                            leToast.show();
                        }
                    });
            //Spinner : récupération de valeur recette a afficher dans le spinner
            Dao<Recette, Integer> daoRecette = linker.getDao(Recette.class);
            List<Recette> participantJsonList = daoRecette.queryForAll();
            CustomAdapterRecette adapter = new CustomAdapterRecette(this,
                    R.layout.spinner_layout_ressource,
                    R.id.textView_item_name,
                    R.id.quantiter,
                    R.id.prix,
                    participantJsonList);
            snpRecette.setAdapter(adapter);
            rowS.addView(snpRecette);

            //si Modif
            if (recette != null) {
                for (int pos = 0; pos < adapter.getCount(); pos++) {
                    //si id produit donnée a la fontcion corresspond a un prod du spinner il l'affiche
                    if (recette.getIdRecette() == adapter.getItemId(pos)) {
                        snpRecette.setSelection(pos);
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
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText())) + 1));
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
                    view.setText(String.valueOf(Integer.parseInt(String.valueOf(view.getText())) - 1));
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

                    if (rowS.getParent() != null) {
                        ((ViewGroup) rowS.getParent()).removeView(rowS); // <- fix
                    }
                    if (rowQ.getParent() != null) {
                        ((ViewGroup) rowQ.getParent()).removeView(rowQ); // <- fix
                    }
                }
            });

            rowQ.addView(deleteLigne);
            //ajout au container
            containerSpinner2.addView(rowS);
            containerSpinner2.addView(rowQ);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}