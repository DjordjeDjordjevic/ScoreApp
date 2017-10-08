package com.george.scoreapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Djordje on 3/2/2017.
 */

public class PlayersAdapter extends ArrayAdapter<Player> {

    ArrayList<Player> players;

    public PlayersAdapter(Context context, ArrayList<Player> objects) {
        super(context, 0, objects);
        this.players = objects;
    }

	@Override
    public View getView(final int pos, View convertView, final ViewGroup parent)
    {
		final Player player = getItem(pos);

        if(convertView == null)
        {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.players_text_view_score_to_add,null);
			player.nameTV = (TextView) convertView.findViewById(R.id.TVplayersName);
			player.scoreTV = (TextView)convertView.findViewById(R.id.TVplayersScore);
			player.scoreToAddET = (EditText)convertView.findViewById(R.id.ETscoreToAdd);

			convertView.setTag(player);
        }

		player.nameTV.setText(player.name);
		player.scoreTV.setText(String.format("%d",player.score));

        return convertView;
    }

}
