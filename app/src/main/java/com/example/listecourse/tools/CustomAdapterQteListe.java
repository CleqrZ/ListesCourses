package com.example.listecourse.tools;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listecourse.bdd.ListeCourseProduit;
import com.example.listecourse.bdd.Produit;


import java.util.List;

public class CustomAdapterQteListe extends BaseAdapter{
    private LayoutInflater flater;
    private List<ListeCourseProduit> list;
    private int textViewItemUnits;
    private int listItemLayoutResource;
    private int textViewItemNameId;
    private int textViewItemPercentId;
    private int textViewItemPePrix;

    public CustomAdapterQteListe(Activity context, int listItemLayoutResource, int textViewItemUnits,
                                 int textViewItemNameId, int textViewItemPercentId, int textViewItemPePrix,
                                 List<ListeCourseProduit> list) {
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemNameId = textViewItemNameId;
        this.textViewItemPercentId = textViewItemPercentId;
        this.textViewItemPePrix = textViewItemPePrix;
        this.textViewItemUnits =textViewItemUnits;
        this.list = list;
        this.flater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        if(this.list == null)  {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        ListeCourseProduit produit = (ListeCourseProduit) this.getItem(position);
        return produit.getIdListeCourseProduit();
        // return position; (Return position if you need).
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListeCourseProduit produitR = (ListeCourseProduit) getItem(position);
        Produit produit = produitR.getIdProduitP();
        // Example: @listItemLayoutResource: R.layout.spinner_item_layout_resource
        // (File: layout/spinner_item_layout_resourcerce.xml)
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        // Example: @textViewItemNameId: R.id.textView_item_name
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemNameId);
        if (produit != null){
            textViewItemName.setText(produit.getLibelleProduit());
        }

        TextView textViewUnits = (TextView)rowView.findViewById(this.textViewItemUnits);
        if (produitR != null){
            textViewUnits.setText("x "+String.valueOf(produitR.getQte()));
        }else {
            textViewUnits.setText(String.valueOf("x "+0));
        }

        // Example: @textViewItemPercentId: R.id.textView_item_percent
        TextView textViewItemPercent = (TextView) rowView.findViewById(textViewItemPercentId);
        textViewItemPercent.setText(produit.getQuantiter());
        TextView textViewItemPrix= (TextView) rowView.findViewById(textViewItemPePrix);
        textViewItemPrix.setText(String.valueOf(produit.getPrixProduit())+"€");
        return rowView;
    }
}

