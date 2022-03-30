package com.example.listecourse.tools;

import com.example.listecourse.bdd.Produit;

public class produitQuantiter extends Produit {
    private  int quantiter;

    public produitQuantiter(int quantiter) {
        this.quantiter = quantiter;
    }


    public int getQuantiterR() {
        return quantiter;
    }

    public void setQuantiter(int quantiter) {
        this.quantiter = quantiter;
    }
}
