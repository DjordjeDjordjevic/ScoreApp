package com.george.scoreapp;

import android.content.Context;
import android.support.annotation.NonNull;
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

class PlayersAdapter extends ArrayAdapter<Player> {

    private ArrayList<Player> players;

    PlayersAdapter(Context context, ArrayList<Player> objects) {
        super(context, 0, objects);
        this.players = objects;
    }

	@NonNull
    @Override
    public View getView(final int pos, View convertView, @NonNull final ViewGroup parent)
    {
		final Player player = getItem(pos);

        if(convertView == null)
        {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.players_text_view_score_to_add,null);
            player.setNameTV((TextView) convertView.findViewById(R.id.TVplayersName));
			player.setScoreTV((TextView)convertView.findViewById(R.id.TVplayersScore));
			player.setScoreToAddET((EditText)convertView.findViewById(R.id.ETscoreToAdd));

			convertView.setTag(player);
        }

		player.getNameTV().setText(player.getName());
		player.getScoreTV().setText(String.format("%d",player.getScore()));

        return convertView;
    }

}
