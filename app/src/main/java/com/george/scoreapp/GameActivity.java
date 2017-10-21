package com.george.scoreapp;

import android.app.AlertDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameActivity extends AppCompatActivity {

	ListView LVplayers;                     //ListView koji prikazuje pojedinacne igrace

	PlayersAdapter adapter;                 //Adapter za popunjavanje ListView
    GameOverDialogAdapter adapterGameOver;  //Adapter za popunjavanje menija na kraju igre

	Player player;

	ArrayList<Player> ALplayersGame;        //Lista igraca
	ArrayList<Player> ALplayersSort;        //Lista sortiranih igraca

	int listSize;                           //Broj igraca
	int limitValue;                         //Granica
	boolean gameOver = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();

		LVplayers = (ListView)findViewById(R.id.LVplayers);

        //Preuzimamo podatke iz intenta
		listSize = Integer.parseInt(intent.getStringExtra("NumberOfPlayers"));
		limitValue = Integer.parseInt(intent.getStringExtra("Limit"));

		ALplayersGame = new ArrayList<>();
		ALplayersSort = new ArrayList<>();

		adapter = new PlayersAdapter(this,ALplayersGame);

		Log.i("GameActivity", "Player number: " + listSize);
		Log.i("GameActivity", "Limit value: " + limitValue);

        //Kreiramo nove Player objekte
        //Dodajemo ih u listu igraca
		for (int i = 0; i < listSize; i++) {
			player = new Player(intent.getStringExtra("Player" + i), limitValue);
			ALplayersGame.add(i, player);
			Log.i("GameActivity", "Player "+player.getName()+" added");
			Log.i("GameActivity", "Limit  "+limitValue);
		}
		//Postavljamo adapter na ListView
		LVplayers.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_game,menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        //Dugme za dodavanje rezultata
		if(item.getItemId() == R.id.BTNadd)
		{
            //Prolazimo kroz listu igraca
			for(int i=0;i<ALplayersGame.size();i++)
			{
                //Provera da li je unesena vrednost za dodavanje
                if(adapter.getItem(i).isEmpty())
				{
                    //Ako igrac nema vrednost za dodavanje
					//EditText tog igraca dobija fokus
					Log.i("ERROR", "Player has no text to add");
					adapter.getItem(i).getScoreToAddET().requestFocus();
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

			//Postavnjanje menija za resetovanje rezultata ili za vracanje na predhodni meni
			if(gameOver)
            {
                adapter.getItem(1).getScoreTV().setText("0");
                Log.i("GAME OVER", "GAME OVER");
                AlertDialog dialog = GameOverDialog();
                dialog.show();

            }
        }
		return super.onOptionsItemSelected(item);
	}

	//Meni na kraju igre
	public AlertDialog GameOverDialog()
    {
        //Kopiramo listu igraca u novu listu
        ALplayersSort = (ArrayList<Player>) ALplayersGame.clone();

        //Sortiramo novu listu
        Collections.sort(ALplayersSort, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getScore() - p2.getScore();
            }
        });

        //Kreiramo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        LayoutInflater inflater = GameActivity.this.getLayoutInflater();

        //Kreiramo ListView sa imenom i rezultatom igraca
        ListView LVgameOver = (ListView) inflater.inflate(R.layout.game_over_dialog_list_view, null);

        adapterGameOver = new GameOverDialogAdapter(this, ALplayersSort);

        //Postavljamo adapter za popunjavanje ListView
        LVgameOver.setAdapter(adapterGameOver);

        //Postavaljmo naslov, ListView, i dva dugmeta
        builder.setTitle("Game over")
                .setView(LVgameOver)
                //Resetovanje aktivnosti
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                //Vracanje na predhodni meni
                .setNegativeButton("Main menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        return builder.create();
    }
}
