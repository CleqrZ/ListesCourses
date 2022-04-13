package com.example.listecourse.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listecourse.bdd.ListeCourse;
import com.example.listecourse.bdd.ListeCourseProduit;
import com.example.listecourse.bdd.ListeCourseRecette;
import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.bdd.RecetteProduit;
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
            TableUtils.createTable( connectionSource, RecetteProduit.class );

            Log.i( "DATABASE", "onCreate invoked" );
        } catch( Exception exception ) {
            Log.e("DATABASE", "Can't create Database", exception);
        }
        try {
            ///appel des DAO
            Dao<Recette, Integer> daoRecette = this.getDao(Recette.class);
            Dao<Produit, Integer> daoProduits = this.getDao(Produit.class);
            Dao<RecetteProduit, Integer> daoRecetteProduit = this.getDao(RecetteProduit.class);
            Dao<ListeCourse, Integer> daoListeCourse = this.getDao(ListeCourse.class);
            Dao<ListeCourseProduit, Integer> daoListeCourseProduit = this.getDao(ListeCourseProduit.class);
            Dao<ListeCourseRecette, Integer> daoListeCourseRecette = this.getDao(ListeCourseRecette.class);
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

            Recette Crepes = new Recette("Crepes", 13);
            daoRecette.create(Crepes);
            RecetteProduit recetteProduit = new RecetteProduit(Farine,Crepes,1);
            daoRecetteProduit.create(recetteProduit);
            RecetteProduit recetteProduit1 = new RecetteProduit(Lait,Crepes,1);
            daoRecetteProduit.create(recetteProduit1);
            RecetteProduit recetteProduit2 = new RecetteProduit(Oeuf,Crepes,1);
            daoRecetteProduit.create(recetteProduit2);
            RecetteProduit recetteProduit3 = new RecetteProduit(Beurre,Crepes,1);
            daoRecetteProduit.create(recetteProduit3);
            RecetteProduit recetteProduit4 = new RecetteProduit(Nutella,Crepes,1);
            daoRecetteProduit.create(recetteProduit4);

            /*Recette Sandwich = new Recette("Sandwich", 9);
            daoRecette.create(Sandwich);
            RecetteProduit recetteProduit5 = new RecetteProduit(Jambon,Sandwich,2);
            daoRecetteProduit.create(recetteProduit5);
            RecetteProduit recetteProduit6 = new RecetteProduit(Pain,Sandwich,2);
            daoRecetteProduit.create(recetteProduit6);
            RecetteProduit recetteProduit7 = new RecetteProduit(Beurre,Sandwich,1);
            daoRecetteProduit.create(recetteProduit7);
            RecetteProduit recetteProduit8 = new RecetteProduit(Emental,Sandwich,1);
            daoRecetteProduit.create(recetteProduit8);
            RecetteProduit recetteProduit9 = new RecetteProduit(Nutella,Sandwich,1);
            daoRecetteProduit.create(recetteProduit9);*/



            //Creation Liste de Course

            ListeCourse listeCourse = new ListeCourse("test", 15);
            daoListeCourse.create(listeCourse);

            ListeCourseProduit farine = new ListeCourseProduit( Farine,listeCourse,  32);
            daoListeCourseProduit.create(farine);

            ListeCourseRecette Crepe = new ListeCourseRecette(Crepes, listeCourse, 25 );
            daoListeCourseRecette.create(Crepe);




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