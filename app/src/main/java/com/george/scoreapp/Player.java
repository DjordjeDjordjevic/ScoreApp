package com.george.scoreapp;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Djordje on 3/2/2017.
 */

class Player {

    private String name;
    private int score;
    private int limit;

    private TextView nameTV;
    private EditText scoreToAddET;
    private TextView scoreTV;

    Player(String name, int limit)
    {
        this.name = name;
        this.limit = limit;
    }

    //Postavljanje novog rezultata
    void setScore() {
        Log.i("Value to add", scoreToAddET.getText().toString());
        score += Integer.parseInt(scoreToAddET.getText().toString());
        scoreTV.setText(String.valueOf(score));
        scoreToAddET.setText("");
    }

    //Getter Setter
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    TextView getNameTV() {
        return nameTV;
    }

    void setNameTV(TextView nameTV) {
        this.nameTV = nameTV;
    }

    EditText getScoreToAddET() {
        return scoreToAddET;
    }

    void setScoreToAddET(EditText scoreToAddET) {
        this.scoreToAddET = scoreToAddET;
    }

    TextView getScoreTV() {
        return scoreTV;
    }

    void setScoreTV(TextView scoreTV) {
        this.scoreTV = scoreTV;
    }

    //Provera da li je ukupan rezultat veci ili jednak od granice do koje se igra
    boolean isGameOver()
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
