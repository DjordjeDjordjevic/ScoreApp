package com.george.scoreapp;

 import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> ALplayers = new ArrayList<String>();
    ArrayAdapter<String> ALplayersAdapter;

	int limitValue = 0;
	EditText ETlimit;
	TextView TVlimit;
    public Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TESTING WITH 10 PLAYERES
        //Test(6  ,100);

		//NIJE GOTOVO!!!!
		//VRACANJE IZ GAMEACTIVITY
//		if(getIntent().getExtras() != null)
//		{
//			limitValue = Integer.parseInt(getIntent().getStringExtra("Limit"));
//			TVlimit.setText(getIntent().getStringExtra("Limit"));
//			TVlimit.setVisibility(View.VISIBLE);
//			ETlimit.setVisibility(View.INVISIBLE);
//		}

        ETlimit = (EditText)findViewById(R.id.ETlimit);
        final EditText ETplayersName = (EditText)findViewById(R.id.ETplayersName);

		TVlimit = (TextView)findViewById(R.id.TVlimit);

        final ListView LVplayers = (ListView)findViewById(R.id.LVplayers);
        ALplayersAdapter = new ArrayAdapter<String>(this, R.layout.players_text_view,R.id.TVplayersName, ALplayers);
        LVplayers.setAdapter(ALplayersAdapter);

		ETlimit.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if((event.getAction()==KeyEvent.ACTION_DOWN) &&
						keyCode == KeyEvent.KEYCODE_ENTER)
				{
					if(ETlimit.getText().toString().equals(""))
					{
						Log.i("MainActivity","EditText limit is empty");
						return true;
					}
					else
					{
						limitValue = Integer.parseInt(ETlimit.getText().toString());
						Log.i("MainActivity",String.format("Limit value: %d",limitValue));

						TVlimit.setText(String.format("%d", limitValue));
						TVlimit.setVisibility(View.VISIBLE);
						ETlimit.setVisibility(View.INVISIBLE);

					}
				}
				return false;
			}
		});
		TVlimit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ETlimit.setVisibility(View.VISIBLE);
				TVlimit.setVisibility(View.INVISIBLE);
				ETlimit.requestFocus();
			}
		});


         ETplayersName.setOnKeyListener(new View.OnKeyListener() {
             public boolean onKey(View v, int keyCode, KeyEvent event)
             {
                 if((event.getAction()==KeyEvent.ACTION_DOWN) &&
                         keyCode == KeyEvent.KEYCODE_ENTER)
                 {
                     if(ETplayersName.getText().toString().equals(""))
                     {
                         Log.i("MainActivity", "EditText player name has no text");

                     }
                     else
                     {
						 if(ALplayers.size()>=14)
						 {
							 Log.i("MainActivity", "Player size above 14");
							 ETplayersName.setText("");
							 return true;
						 }
                         String name = ETplayersName.getText().toString();
                         ALplayers.add(name);
                         ALplayersAdapter.notifyDataSetChanged();
                         ETplayersName.setText("");
						 ETplayersName.requestFocus();
                     }
                 }
                 return false;
             }
         });

		LVplayers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				ALplayers.remove(i);
				ALplayersAdapter.notifyDataSetChanged();
				return false;
			}
		});
		LVplayers.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if(b)
					LVplayers.clearFocus();
			}
		});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.BTnext)
        {
			if(limitValue == 0 || ALplayers.size() < 2)
			{
				ETlimit.requestFocus();
			}
			else
			{
				intent = new Intent(MainActivity.this, GameActivity.class);



				intent.putExtra("Limit", String.format("%d", limitValue));
				intent.putExtra("NumberOfPlayers", String.format("%d", ALplayers.size()));
				Log.i("MainActivity", "Number of players sent with intent " + intent.getStringExtra("NumberOfPlayers"));


				for (int i= 0 ; i<ALplayers.size(); i++)
				{
					//String code = "Player"+i;
					intent.putExtra(String.format("Player%d", i), ALplayers.get(i));
					Log.i(String.format("Player%d", i), "" + intent.getStringExtra(String.format("Player%d", i)));
				}

				startActivity(intent);
			}

        }
        return super.onOptionsItemSelected(item);
    }

	public void Test(int numberOfPlayers, int limit )
    {
        int players = numberOfPlayers;
        intent = new Intent(MainActivity.this, GameActivity.class);

        intent.putExtra("NumberOfPlayers", Integer.toString(players));
		intent.putExtra("Limit", Integer.toString(limit));

        for(int i = 0; i<players; i++)
        {
            intent.putExtra(String.format("Player%d", i),  String.format("Player %d", i));
        }

        startActivity(intent);
    }
}
