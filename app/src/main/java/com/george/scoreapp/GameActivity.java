package com.george.scoreapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

	ListView LVplayers;

	PlayersAdapter adapter;

	Player player;

	ArrayList<Player> ALplayersGame;
	ArrayList<Player> ALplayersSort;

	int listSize;
	int limitValue;
	boolean gameOver = false;

	InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();

		LVplayers = (ListView)findViewById(R.id.LVplayers);

		listSize = Integer.parseInt(intent.getStringExtra("NumberOfPlayers"));
		limitValue = Integer.parseInt(intent.getStringExtra("Limit"));

		ALplayersGame = new ArrayList<>();
		ALplayersSort = new ArrayList<>();

		adapter = new PlayersAdapter(this,ALplayersGame);

		imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

		Log.i("GameActivity", "Player number: " + listSize);
		Log.i("GameActivity", "Limit value: " + limitValue);

		for (int i = 0; i < listSize; i++) {
			player = new Player(intent.getStringExtra("Player" + i), limitValue);//,(TextView) recyclerView.getChildAt(i).findViewById(R.id.TVplayersName), (EditText) recyclerView.getChildAt(i).findViewById(R.id.ETscoreToAdd),(TextView) recyclerView.getChildAt(i).findViewById(R.id.TVplayersScore));


			ALplayersGame.add(i, player);
			Log.i("GameActivity", "Player "+player.name+" added");
			Log.i("GameActivity", "Limit  "+limitValue);
		}
		LVplayers.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_game,menu);
		return true;
	}

	//Dugme za dodavanje rezultata
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.BTNadd)
		{
			//Provera da li je unesena vrednost za dodavanje
			for(int i=0;i<ALplayersGame.size();i++)
			{
				if(adapter.getItem(i).isEmpty())
				{
					//Ako igrac nema vrednost za dodavanje
					//EditText tog igraca dobija fokus
					Log.i("ERROR", "Player has no text to add");
					adapter.getItem(i).scoreToAddET.requestFocus();
					return super.onOptionsItemSelected(item);
				}
			}

			//Prolazimo kroz sve igrace i pozivamo funkciju dodavanja rezultata
			for(int i=0;i<ALplayersGame.size();i++)
			{
				adapter.getItem(i).setScore();

				//Provera da li je igrac prekoracio granicu igre
				if(adapter.getItem(i).isGameOver())
					gameOver = true;
			}

			//TODO
			//Postavnjanje menija za resetovanje rezultata ili za vracanje na predhodni meni
			if(gameOver)
            {
                Log.i("GAME OVER", "GAME OVER");
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

            }
		}
		return super.onOptionsItemSelected(item);
	}
}
