package com.example.jeu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;


    ConstraintLayout fenetreDeJeu;
    LinearLayout info;
    private TextView nbcoups, max;
    private GestureDetectorCompat gestureDetector;
    Case tableauDeJeu [] [];
    int xBoule, yBoule, cpt=-1, coupsmax;
    boolean verif=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fenetreDeJeu = findViewById(R.id.fenetreDeJeu);
        info = findViewById(R.id.info);
        nbcoups = (TextView) findViewById(R.id.nbcoups);
        max = (TextView) findViewById(R.id.max);

        tableauDeJeu = new Case[7][7];

        gestureDetector = new GestureDetectorCompat(this, this);

        lancerNouveauNiveau();

    }

    public void afficherTableau(){
        cpt++;
        if(cpt > coupsmax){
            perdu();
        }
        if(verifwin() && cpt <= coupsmax){
            gagne();
        }
        nbcoups.setText(cpt + " coup(s)");
        for(int x =0; x<=tableauDeJeu.length-1;x++){
            for(int y =0; y<= tableauDeJeu.length-1;y++){

                fenetreDeJeu.addView(tableauDeJeu[x][y].creerImage(x,y));

            }
        }

    }

    private void gagne() {
        TextView gagner = new TextView(this);
        gagner.setText("Gagné ! avec "+cpt+ " coups");
        info.addView(gagner);

        lancerNouveauNiveau();
    }

    private void perdu() {
        TextView perdre = new TextView(this);
        perdre.setText("Perdu !");
        info.addView(perdre);

        lancerNouveauNiveau();
    }

    private void lancerNouveauNiveau() {
        cpt=-1;
        coupsmax=26;
        max.setText(coupsmax+ " coups");
        nbcoups.setText(cpt + " coup(s)");
        for(int i=0; i<=tableauDeJeu.length-1;i++){
            for(int j=0 ; j<=tableauDeJeu.length-1;j++) {
                tableauDeJeu[i][j] = new Case(this, 1);
            }
        }

        tableauDeJeu[0][0].modifierImage(0);
        tableauDeJeu[0][1].modifierImage(0);
        tableauDeJeu[2][2].modifierImage(0);
        tableauDeJeu[6][2].modifierImage(0);
        tableauDeJeu[5][2].modifierImage(0);
        tableauDeJeu[4][4].modifierImage(0);
        tableauDeJeu[4][5].modifierImage(0);
        tableauDeJeu[2][3].modifierImage(0);
        tableauDeJeu[3][1].modifierImage(0);
        tableauDeJeu[1][0].modifierImage(0);
        tableauDeJeu[6][4].modifierImage(0);
        tableauDeJeu[0][6].modifierImage(0);
        tableauDeJeu[1][6].modifierImage(0);
        tableauDeJeu[2][5].modifierImage(0);
        tableauDeJeu[1][5].modifierImage(0);

        tableauDeJeu[3][3].modifierImage(2);

        afficherTableau();

    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }


    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float deltaX = e1.getX() - e2.getX();
        float deltaY = e1.getY() - e2.getY();
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                onLeftSwipe();
            }else
            {
                onRightSwipe();
            }
        }

        else if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y))
        {
            if(deltaY > 0)
            {
                onUpSwipe();
            }else
            {
                onDownSwipe();
            }
        }


        return true;
    }

    private boolean verifwin(){
        boolean win=true;
        for(int x =0; x<=tableauDeJeu.length-1;x++){
            for(int y =0; y<= tableauDeJeu.length-1;y++) {
                if(tableauDeJeu[x][y].getValeur() == 1){
                    win=false;
                }

            }
        }
        return win;
    }

    private void updateBoule(){
        for(int x =0; x<=tableauDeJeu.length-1;x++){
            for(int y =0; y<= tableauDeJeu.length-1;y++){

                if(tableauDeJeu[x][y].getValeur() == 2){
                    xBoule=x;
                    yBoule=y;
                }

            }
        }

    }

    private void onRightSwipe() {

        Log.e("Erreur", " onFling droite");

        updateBoule();

        while(xBoule != tableauDeJeu.length-1 && tableauDeJeu[xBoule+1][yBoule].getValeur() != 0){
            tableauDeJeu[xBoule][yBoule].modifierImage(3);
            tableauDeJeu[xBoule+1][yBoule].modifierImage(2);
            updateBoule();
            verif=true;
        }
        if(verif == true){
            afficherTableau();
        }
        verif=false;



    }

    private void onLeftSwipe() {
        Log.e("Erreur", " onFling gauche");

        updateBoule();

        while(xBoule > 0 && tableauDeJeu[xBoule-1][yBoule].getValeur() != 0){
            tableauDeJeu[xBoule][yBoule].modifierImage(3);
            tableauDeJeu[xBoule-1][yBoule].modifierImage(2);
            updateBoule();
            verif=true;
        }
        if(verif == true){
            afficherTableau();
        }
        verif=false;
    }
    private void onUpSwipe() {
        Log.e("Erreur", " onFling haut");

        updateBoule();

        while(yBoule > 0 && tableauDeJeu[xBoule][yBoule-1].getValeur() != 0){
            tableauDeJeu[xBoule][yBoule].modifierImage(3);
            tableauDeJeu[xBoule][yBoule-1].modifierImage(2);
            updateBoule();
            verif=true;
        }
        if(verif == true){
            afficherTableau();
        }
        verif=false;
    }

    private void onDownSwipe() {
        Log.e("Erreur", " onFling bas");

        updateBoule();

        while(yBoule != tableauDeJeu.length-1 && tableauDeJeu[xBoule][yBoule+1].getValeur() != 0){
            tableauDeJeu[xBoule][yBoule].modifierImage(3);
            tableauDeJeu[xBoule][yBoule+1].modifierImage(2);
            updateBoule();
            verif=true;
        }
        if(verif == true){
            afficherTableau();
        }
        verif=false;
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
