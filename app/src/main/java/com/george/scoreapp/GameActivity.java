package com.george.scoreapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

	ListView LVplayers;
    ListView LVplayersGameOver;

	PlayersAdapter adapter;
    GameOverDialogAdapter adapterGameOver;

	Player player;

	ArrayList<Player> ALplayersGame;
	ArrayList<Player> ALplayersSort;

	int listSize;
	int limitValue;
	boolean gameOver = false;

	InputMethodManager imm;

    AlertDialog.Builder builder;
    View listView;
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

        builder = new AlertDialog.Builder(GameActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        listView = inflater.inflate(R.layout.game_over_dialog_list_view, null);

		imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

		Log.i("GameActivity", "Player number: " + listSize);
		Log.i("GameActivity", "Limit value: " + limitValue);

		for (int i = 0; i < listSize; i++) {
			player = new Player(intent.getStringExtra("Player" + i), limitValue);
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


                builder.setTitle("Game Over");
                builder.setView(listView);

                LVplayersGameOver = (ListView)listView.findViewById(R.id.LVplayersGameOver);
                adapterGameOver = new GameOverDialogAdapter(this, ALplayersGame);
                LVplayersGameOver.setAdapter(adapterGameOver);

                builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        //Restartovanje rezultata
                        for(int i=0;i<ALplayersGame.size();i++)
                        {
                            Log.i("Payer "+adapterGameOver.getItem(i).name, "Score: "+adapterGameOver.getItem(i).score);
                            adapter.getItem(i).resetScore();
//                            Log.i("Payer "+adapterGameOver.getItem(i).name, "Score: "+adapterGameOver.getItem(i).score);
                            Log.i("Payer "+adapter.getItem(i).name, "Score: "+adapter.getItem(i).score);
                            Log.i("Payer "+adapter.getItem(i).name, "Score: "+adapter.getItem(i).scoreTV.getText().toString());
                            gameOver = false;
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("Main menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Vracanje u glavni meni
                    }
                });
                builder.show();
                for(int i=0;i<ALplayersGame.size();i++)
                adapter.getItem(i).resetScore();

            }
        }
		return super.onOptionsItemSelected(item);
	}
}
