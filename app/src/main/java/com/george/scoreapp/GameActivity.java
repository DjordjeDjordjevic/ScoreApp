package com.george.scoreapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	RecyclerView.Adapter adapter;
	RecyclerView.LayoutManager layoutManager;

	ArrayList<Player> ALplayersGame;
	ArrayList<Player> ALplayersSort;

	int listSize;
	int limitValue;

	Player player;

	InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();

		listSize = Integer.parseInt(intent.getStringExtra("NumberOfPlayers"));
		limitValue = Integer.parseInt(intent.getStringExtra("Limit"));

		recyclerView = (RecyclerView)findViewById(R.id.LVplayersGame);
		recyclerView.setHasFixedSize(true);

		ALplayersGame = new ArrayList<>();
		ALplayersSort = new ArrayList<>();

		layoutManager = new LinearLayoutManager(this);

		recyclerView.setLayoutManager(layoutManager);
		adapter = new RecyclerViewAdapter(ALplayersGame);


		imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

		Log.i("GameActivity", "Player number: " + listSize);
		Log.i("GameActivity", "Limit value: " + limitValue);

		for (int i = 0; i < listSize; i++) {
			player = new Player(intent.getStringExtra("Player" + i));

			player.setLimit(limitValue);

			ALplayersGame.add(i, player);
			Log.i("GameActivity", "Player added");
		}
		recyclerView.setAdapter(adapter);
	}
}
