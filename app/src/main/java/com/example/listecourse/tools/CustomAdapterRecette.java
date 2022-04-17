package com.example.listecourse.tools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listecourse.bdd.Produit;
import com.example.listecourse.bdd.Recette;
import com.example.listecourse.bdd.RecetteProduit;

import java.util.List;

public class CustomAdapterRecette extends BaseAdapter {
    private LayoutInflater flater;
    private List<Recette> list;
    private int listItemLayoutResource;
    private int textViewItemNameId;
    private int textViewItemPercentId;
    private int textViewItemPePrix;


    // Arguments example:
    //  @listItemLayoutResource: R.layout.spinner_item_layout_resource
    //        (File: layout/spinner_item_layout_resource.xmll)
    //  @textViewItemNameId: R.id.textView_item_name
    //        (A TextVew in file layout/spinner_item_layout_resource.xml)
    //  @textViewItemPercentId: R.id.textView_item_percent
    //        (A TextVew in file layout/spinner_item_layout_resource.xmll)
    public CustomAdapterRecette(Activity context, int listItemLayoutResource,
                                int textViewItemNameId, int textViewItemPercentId,int textViewItemPePrix,
                                List<Recette> list) {
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemNameId = textViewItemNameId;
        this.textViewItemPercentId = textViewItemPercentId;
        this.textViewItemPePrix = textViewItemPePrix;
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
        Recette recette = (Recette) this.getItem(position);
        return recette.getIdRecette();
        // return position; (Return position if you need).
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Recette recette= (Recette) getItem(position);
        // Example: @listItemLayoutResource: R.layout.spinner_item_layout_resource
        // (File: layout/spinner_item_layout_resourcerce.xml)
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        // Example: @textViewItemNameId: R.id.textView_item_name
        // (A TextView in file layout/spinner_item_layout_resourcerce.xml)
        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemNameId);
        textViewItemName.setText(recette.getLibelleRecette());
        // Example: @textViewItemPercentId: R.id.textView_item_percent
        // (A TextView in file layout/spinner_item_layout_resource.xmlxml)
        return rowView;
    }
}

