package com.example.listecourse.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseLinker extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "bdd.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseLinker(Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            ///Creation Tables
            TableUtils.createTable( connectionSource, ListeCourse.class );
            TableUtils.createTable( connectionSource, Recette.class );
            TableUtils.createTable( connectionSource, Produit.class );
            Log.i( "DATABASE", "onCreate invoked" );
        } catch( Exception exception ) {
            Log.e("DATABASE", "Can't create Database", exception);
        }
        try {
            ///appel des DAO
            Dao<Recette, Integer> daoRecette = this.getDao(Recette.class);
            Dao<Produit, Integer> daoProduits = this.getDao(Produit.class);
            Dao<ListeCourse, Integer> daoListeCourse = this.getDao(ListeCourse.class);
            //Creation Produits

            Produit tomate = new Produit("Tomate","1kg", 5 );
            daoProduits.create( tomate);

            Produit Steak = new Produit("Steak de Boeuf","500g", 9.9 );
            daoProduits.create( Steak);

            Produit Oignon = new Produit("Oignon","1kg", 5 );
            daoProduits.create( Oignon);

            Produit Pates = new Produit("Pates","1kg", 2 );
            daoProduits.create( Pates);

            Produit Lait = new Produit("Bouteille de lait","10", 4 );
            daoProduits.create( Lait);

            Produit Oeuf = new Produit("oeuf","12 ", 2 );
            daoProduits.create( Oeuf);

            Produit Farine = new Produit("Farine","1kg", 2 );
            daoProduits.create( Farine);

            Produit Jambon = new Produit("Jambom Tranche","10",3 );
            daoProduits.create( Jambon);

            Produit Beurre = new Produit("Beurre","200g", 2 );
            daoProduits.create( Beurre);

            Produit Emental = new Produit("Emental","500g", 3 );
            daoProduits.create( Emental);

            Produit Nutella = new Produit("Pot Nutella","200g", 3 );
            daoProduits.create( Nutella);

            Produit Pain = new Produit("Pain","1", 1 );
            daoProduits.create( Pain);

            Produit Yogurt = new Produit("Yogurt","6", 3 );
            daoProduits.create( Yogurt);
            //Creation Recettes

            Recette Bolognaise = new Recette("Bolognaise", "{'libelleProduit':'1kg Tomate','quantiter':'1kg',prixProduit':5.0,'idProduit':1}, " +
                            "{'libelleProduit':'Steak de Boeuf','quantiter':'500g','prixProduit':9.9,'idProduit':2} ," +
                            "{'libelleProduit':'Oignon','quantiter':'1kg','prixProduit':5.0,'idProduit':3}," +
                            "{'libelleProduit':'Pates','quantiter':'1kg','prixProduit':2.0,'idProduit':4}", 21.99);
            daoRecette.create(Bolognaise);

            List<String> listCrepe = new ArrayList<>();
            listCrepe.add(new Gson().toJson(Farine));
            listCrepe.add(new Gson().toJson(Lait));
            listCrepe.add(new Gson().toJson(Oeuf));
            listCrepe.add(new Gson().toJson(Beurre));
            listCrepe.add(new Gson().toJson(Nutella));
            Recette Crepes = new Recette("Crepes", listCrepe.toString(), 13);
            daoRecette.create(Crepes);

            List<String> listeSandwich = new ArrayList<>();
            listeSandwich.add(new Gson().toJson(Jambon)+"{'nombre': 2}");
            listeSandwich.add(new Gson().toJson(Pain));
            listeSandwich.add(new Gson().toJson(Beurre));
            listeSandwich.add(new Gson().toJson(Emental));
            Recette Sandwich = new Recette("Sandwich", listeSandwich.toString(),9);
            daoRecette.create(Sandwich);
            //Creation Liste de Course
            List<String> listP1 = new ArrayList<>();
            listP1.add(new Gson().toJson(Nutella));
            listP1.add(new Gson().toJson(Yogurt));
            listP1.add(new Gson().toJson(Oeuf));
            List<String> listR1 = new ArrayList<>();
            listR1.add(new Gson().toJson(Crepes));
            listR1.add(new Gson().toJson(Sandwich));
            ListeCourse Semain1 = new ListeCourse("Semain1", listP1.toString(),listR1.toString(),30 );
            daoListeCourse.create(Semain1);

            List<String> listP2 = new ArrayList<>();
            listP2.add(new Gson().toJson(Steak));
            listP2.add(new Gson().toJson(Emental));
            listP2.add(new Gson().toJson(Oeuf));
            List<String> listR2 = new ArrayList<>();
            listR2.add(new Gson().toJson(Crepes));
            listR2.add(new Gson().toJson(Bolognaise));
            ListeCourse Semain2 = new ListeCourse("Semain2", listP2.toString(),listR2.toString(),30 );
            daoListeCourse.create(Semain2);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i( "DATABASE", "onUpgrade invoked" );
        } catch( Exception exception ) {
            Log.e( "DATABASE", "Can't upgrade Database", exception );
        }
    }

}