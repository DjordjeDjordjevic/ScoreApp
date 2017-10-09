package com.george.scoreapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Djordje on 10/8/2017.
 */

public class GameOverDialogAdapter extends ArrayAdapter<Player> {

    ArrayList<Player> players;

    public GameOverDialogAdapter(Context context, ArrayList<Player> obj)
    {
        super(context,0,obj);
        this.players = obj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Player player = getItem(position);
        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.player_text_view_score, null);
            player.nameTV = (TextView)convertView.findViewById(R.id.TVplayersName);
            player.scoreTV = (TextView)convertView.findViewById(R.id.TVplayersScore);

            convertView.setTag(player);
        }
        player.nameTV.setText(player.name);
        player.scoreTV.setText(String.format("%d",player.score));

        return convertView;
    }

}
