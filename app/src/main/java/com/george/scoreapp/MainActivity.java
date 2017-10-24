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
 import android.widget.Toast;

 import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> ALplayers = new ArrayList<String>();		//Lista sa imenima igraca
    ArrayAdapter<String> ALplayersAdapter;					//Adapter za popunjavanje ListView sa imenima igraca

	int limitValue = 0;				//Granica do koliko se igra
	EditText ETlimit;				//EditText u koji unosimo vrednost granice
	TextView TVlimit;				//TextView koji pokazuje koja je vednost granice
	EditText ETplayersName;			//EditText u koji unosimo imena igraca
	ListView LVplayers;				//ListView za prikazivanje liste imena igraca

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETlimit = (EditText)findViewById(R.id.ETlimit);
        ETplayersName = (EditText)findViewById(R.id.ETplayersName);

		TVlimit = (TextView)findViewById(R.id.TVlimit);

        LVplayers = (ListView)findViewById(R.id.LVplayers);
        ALplayersAdapter = new ArrayAdapter<String>(this, R.layout.players_text_view,R.id.TVplayersName, ALplayers);
        LVplayers.setAdapter(ALplayersAdapter);

		ETlimit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if(!view.hasFocus())
				{
					//Proveravamo da li je EditText prazan
					//Ako jeste ispisujemo obavestenje da je EditText prazan i izlazimo iz funkcije
					if(ETlimit.getText().toString().equals(""))
					{
						Log.i("MainActivity","EditText limit is empty");
						return;
					}
					//Ako EditText nije prazan
					//Cuvamo vrednost EditText u promenljivoj limitValue
					//Menjamo tekst TVlimit u vrednost promenljive limitValue
					//Prikazujemo TVlimit i krijemo ETlimit
					else
					{
						limitValue = Integer.parseInt(ETlimit.getText().toString());
						Log.i("MainActivity",String.format("Limit value: %d",limitValue));

						TVlimit.setText(String.format("%d", limitValue));
						TVlimit.setVisibility(View.VISIBLE);
						ETlimit.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
//		ETlimit.setOnKeyListener(new View.OnKeyListener()
//		{
//			@Override
//			public boolean onKey(View view, int keyCode, KeyEvent event)
//			{
//				//Proveravamo da li je pritisnut Enter dok je EditText u fokusu
//				if((event.getAction()==KeyEvent.ACTION_DOWN) &&
//						keyCode == KeyEvent.KEYCODE_ENTER)
//				{
//					//Proveravamo da li je EditText prazan
//					//Ako jeste ispisujemo obavestenje da je EditText prazan i izlazimo iz funkcije
//					if(ETlimit.getText().toString().equals(""))
//					{
//						Log.i("MainActivity","EditText limit is empty");
//						return true;
//					}
//					//Ako EditText nije prazan
//					//Cuvamo vrednost EditText u promenljivoj limitValue
//					//Menjamo tekst TVlimit u vrednost promenljive limitValue
//					//Prikazujemo TVlimit i krijemo ETlimit
//					else
//					{
//						limitValue = Integer.parseInt(ETlimit.getText().toString());
//						Log.i("MainActivity",String.format("Limit value: %d",limitValue));
//
//						TVlimit.setText(String.format("%d", limitValue));
//						TVlimit.setVisibility(View.VISIBLE);
//						ETlimit.setVisibility(View.INVISIBLE);
//					}
//				}
//				return false;
//			}
//		});
		//Kada kliknemo na TVlimit
		//Prikazujemo ETlimit za ponovno unosenje granice
		//Krijemo TVlimit
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
				 //Proveravamo da li je pritisnut Enter dok je EditText u fokusu
                 if((event.getAction()==KeyEvent.ACTION_DOWN) &&
                         keyCode == KeyEvent.KEYCODE_ENTER)
                 {
					 //Proveravamo da li je EditText prazan
					 //Ako jeste ispisujemo obavestenje da je EditText prazan i izlazimo iz funkcije
                     if(ETplayersName.getText().toString().equals(""))
                     {
                         Log.i("MainActivity", "EditText player name has no text");
						 return true;
                     }
                     //Ako EditText nije prazan
                     else
                     {
						 //Proveravamo da li je prekoracen broj igraca
						 if(ALplayers.size()>=10)
						 {
							 Log.i("MainActivity", "Player size above 10");
							 ETplayersName.setText("");
							 return true;
						 }

						 //U listu igraca dodajemo igraca sa imenom iz EditTexta
						 //Obavestavamo adapter o promenama i brisemo tekst iz EditTexta
                         ALplayers.add(ETplayersName.getText().toString());
                         ALplayersAdapter.notifyDataSetChanged();
                         ETplayersName.setText("");
						 ETplayersName.requestFocus();
                     }
                 }
                 return false;
             }
         });

		//Dugi klik na ime igraca brise igraca iz liste igraca
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
    public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent;
		//Proveravamo da li je pritisnuto Next dugme
        if(item.getItemId() == R.id.BTnext)
        {
			if(limitValue == 0 )
			{
				ETlimit.requestFocus();
			}
			//Pravimo intent ka GameActivity
			//Prosledjujemo granicu, broj igraca i imena igraca
			else
			{
				intent = new Intent(MainActivity.this, GameActivity.class);
				intent.putExtra("Limit", String.format("%d", limitValue));
				intent.putExtra("NumberOfPlayers", String.format("%d", ALplayers.size()));

				Log.i("MainActivity", "Number of players sent with intent " + intent.getStringExtra("NumberOfPlayers"));

				for (int i= 0 ; i<ALplayers.size(); i++)
				{
					intent.putExtra(String.format("Player%d", i), ALplayers.get(i));
					Log.i(String.format("Player%d", i), "" + intent.getStringExtra(String.format("Player%d", i)));
				}
				startActivity(intent);
			}

        }
        return super.onOptionsItemSelected(item);
    }
//Testing
//	public void Test(int numberOfPlayers, int limit )
//    {
//		Intent intent;
//        int players = numberOfPlayers;
//        intent = new Intent(MainActivity.this, GameActivity.class);
//
//        intent.putExtra("NumberOfPlayers", Integer.toString(players));
//		intent.putExtra("Limit", Integer.toString(limit));
//
//        for(int i = 0; i<players; i++)
//        {
//            intent.putExtra(String.format("Player%d", i),  String.format("Player %d", i));
//        }
//
//        startActivity(intent);
//    }
}
