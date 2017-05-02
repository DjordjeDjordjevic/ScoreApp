package com.george.scoreapp;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Djordje on 3/2/2017.
 */

public class PlayersAdapter extends ArrayAdapter<Player> {

    ArrayList<Player> players;

	private static class ViewHolder
	{
		TextView TVplayersName;
		TextView TVscore;
		EditText ETscoreToAdd;
	}

    public PlayersAdapter(Context context, ArrayList<Player> objects) {
        super(context, 0, objects);
        this.players = objects;
    }

	@Override
    public View getView(final int pos, View convertView, final ViewGroup parent)
    {
		final Player player = getItem(pos);

		final ViewHolder viewHolder;

		final View view = convertView;

        if(convertView == null)
        {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.players_text_view_with_score,null);
			viewHolder.TVplayersName = (TextView) convertView.findViewById(R.id.TVplayersName);
			viewHolder.TVscore = (TextView)convertView.findViewById(R.id.TVplayersScore);
			viewHolder.ETscoreToAdd = (EditText)convertView.findViewById(R.id.ETscoreToAdd);

			convertView.setTag(viewHolder);
        }
        else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		viewHolder.TVplayersName.setText(player.name);
		viewHolder.TVscore.setText(String.format("%d",player.score));

        return convertView;
    }

}
