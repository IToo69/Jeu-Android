package com.example.jeu;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Case {

    int valeur,x ,y;

    MainActivity context;
    ImageView image;


    public Case(MainActivity context,int valeur) {
        this.context=context;
        context = new MainActivity();
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    public ImageView creerImage(int x, int y) {
                this.x=x;
                this.y=y;

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(100, 100);
                image = new ImageView(context);
                image.setLayoutParams(params);
                if (context.tableauDeJeu[x][y].getValeur() == 0) {
                    image.setBackgroundResource(R.drawable.noir);
                }
                if (context.tableauDeJeu[x][y].getValeur() == 1) {
                    image.setBackgroundResource(R.drawable.blanc);
                }
                if (context.tableauDeJeu[x][y].getValeur() == 3) {
                    image.setBackgroundResource(R.drawable.bleu);
                }
                if (context.tableauDeJeu[x][y].getValeur() == 2) {
                    image.setBackgroundResource(R.drawable.rond);
                }
                image.setX(x * 110);
                image.setY(y * 110);

                return image;
            }

    public void modifierImage(int valeur) {
        this.valeur = valeur;
        creerImage(this.x, this.y);
    }
}


