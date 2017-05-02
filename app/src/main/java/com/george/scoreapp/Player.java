package com.george.scoreapp;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Djordje on 3/2/2017.
 */

public class Player {

    public String name;
    public int score = 0;
    int limit = 0;

    boolean available = true;

    public Player(String name)
    {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }



    public void resetScore()
    {
        score = 0;
    }

    public void addToScore(int value)
    {
        score = score + value;
    }

    boolean isGameOver()
    {
        if(score >= limit) return true;
        else return false;
    }
}
