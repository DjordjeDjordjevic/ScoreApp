package com.george.scoreapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Djordje on 10/8/2017.
 */

class GameOverDialogAdapter extends ArrayAdapter<Player> {

    private ArrayList<Player> players;

    GameOverDialogAdapter(Context context, ArrayList<Player> obj)
    {
        super(context,0,obj);
        this.players = obj;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Player player = getItem(position);
        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.player_text_view_score, null);
            player.setNameTV((TextView) convertView.findViewById(R.id.TVplayersName));
            player.setScoreTV((TextView)convertView.findViewById(R.id.TVplayersScore));

            convertView.setTag(player);
        }
        assert player != null;
        player.getNameTV().setText(player.getName());
        player.getScoreTV().setText(String.format("%d",player.getScore()));

        return convertView;
    }

}
