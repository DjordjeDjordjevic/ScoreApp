package com.george.scoreapp;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Djordje on 3/2/2017.
 */

public class Player {

    public String name;
    public int score;
    int limit;

    TextView nameTV;
    EditText scoreToAddET;
    TextView scoreTV;

    public Player(String name, int limit)
    {
        this.name = name;
        this.limit = limit;
    }

    //Postavljanje novog rezultata
    public void setScore() {
        Log.i("Value to add", scoreToAddET.getText().toString());
        score += Integer.parseInt(scoreToAddET.getText().toString());
        scoreTV.setText(String.valueOf(score));
        scoreToAddET.setText("");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void resetScore()
    {
        score = 0;
        scoreTV.setText("0");
    }

    //Provera da li je ukupan rezultat veci ili jednak od granice do koje se igra
    public boolean isGameOver()
    {
        if(score >= limit)
            return true;
        else
        return false;
    }

    //Provera da li je EditText za unos novog rezultata prazan
    boolean isEmpty()
    {
        if(scoreToAddET.getText().toString().isEmpty())
            return true;
        return false;
    }
}
