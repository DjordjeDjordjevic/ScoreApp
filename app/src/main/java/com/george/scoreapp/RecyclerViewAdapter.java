package com.george.scoreapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Djordje on 3/14/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

	ArrayList<Player> players;
	RecyclerView recyclerView;
	boolean gameOver = false;


	public static class ViewHolder extends RecyclerView.ViewHolder
	{


		View playerView;
		TextView TVplayersName;
		TextView TVscore;
		EditText ETscoreToAdd;
		public ViewHolder(View v)
		{
			super(v);
			playerView = v;
//			v.setBackgroundColor(Color.GREEN);
		}
	}

	public RecyclerViewAdapter(ArrayList<Player> pl)
	{
		players = pl;
	}

	@Override
	public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.players_text_view_with_score, parent, false);
		recyclerView = (RecyclerView)parent;



		TextView tvName = (TextView)v.findViewById(R.id.TVplayersName);
		TextView tvScore = (TextView)v.findViewById(R.id.TVplayersScore);

		EditText etToAdd = (EditText)v.findViewById(R.id.ETscoreToAdd);

		ViewHolder vh = new ViewHolder(v);

		vh.TVplayersName = tvName;
		vh.TVscore = tvScore;
		vh.ETscoreToAdd = etToAdd;


		return vh;
	}

	@Override
	public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position)
	{
		holder.TVplayersName.setText(players.get(position).name);
		holder.TVscore.setText(String.format("%d",players.get(position).score));

		holder.ETscoreToAdd.setOnEditorActionListener(new EditText.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
				if(i == EditorInfo.IME_ACTION_NEXT && position < recyclerView.getChildCount())
				{
					if(holder.ETscoreToAdd.getText().toString().equals(""))
					{
						Log.i("MainActivity", players.get(position).getName()+" EditText has no text");
						return true;
					}

					addScore(holder, position);
					if(numberOfAvailable()==players.size())
					{
						if(limitReached())
							gameOverDialog();
						resetAvailable(position);
					}

				}
				return false;
			}
		});
	}

	@Override
	public int getItemCount() {

		return players.size();
	}

	public void gameOverDialog()
	{
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(recyclerView.getContext());
		alertDialog.setTitle("Game Over");
		alertDialog.setMessage("Reset score or go back");
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				for (int j = 0; j < players.size(); j++) {
					players.get(j).resetScore();
					TextView tv = (TextView) recyclerView.getChildAt(j).findViewById(R.id.TVplayersScore);
					tv.setText(String.format("%d", 0));
					Log.i("Player score " + players.get(j).getScore(), "Limit " + players.get(j).getLimit());
				}
			}
		});
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				((Activity)recyclerView.getContext()).finish();
			}
		});
		alertDialog.show();
	}

	public void addScore(ViewHolder holder, final int position)
	{
		Log.i("-----------------------"," ");
		Log.i(String.format("Player %s",players.get(position).getName()),String.format("Score: %d",players.get(position).getScore()));

		/*
		Check if there is value inside EditText
		If EditText is empty ("") show Log message
		Else store EditText value inside variable toAdd
		remove text from EditText, add value to player
		set TextView Score to value of player.score
		*/
		int toAdd = Integer.parseInt(holder.ETscoreToAdd.getText().toString());

		holder.ETscoreToAdd.setText("");
		holder.TVscore.setText(String.format("%d", players.get(position).getScore()));

		players.get(position).available = false;
		players.get(position).addToScore(toAdd);

		holder.ETscoreToAdd.setVisibility(View.INVISIBLE);

		notifyItemChanged(position, toAdd);

		Log.i("PlayersAdapter", String.format("Score to add %s", toAdd));
		Log.i(String.format("Player %s", players.get(position).getName()), String.format("Score: %d", players.get(position).getScore()));
		Log.i("-----------------------", " ");
	}

	public int numberOfAvailable()
	{

		int j = 0;
		for (int i = 0;i<players.size();i++)
		{
			if(players.get(i).available == false)
			{
				j++;
//				recyclerView.getChildAt(i).setBackgroundColor(Color.RED);
			}
		}
		Log.i("Number of available: ", ""+j);
		return j;
	}
	public void resetAvailable(int pos)
	{
		for (int i = 0;i<players.size();i++)
		{
			players.get(i).available = true;
			Log.i("Player "+players.get(i).getName(), "is available "+players.get(i).available );
			recyclerView.getChildAt(i).findViewById(R.id.ETscoreToAdd).setVisibility(View.VISIBLE);
		}
	}

	public boolean limitReached()
	{
		for (int i = 0;i<players.size();i++)
		{
			if(players.get(i).score>= players.get(i).getLimit())
				return true;
		}
		return false;
	}
}

