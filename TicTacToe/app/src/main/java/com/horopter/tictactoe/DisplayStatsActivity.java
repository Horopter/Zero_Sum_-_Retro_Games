package com.horopter.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DisplayStatsActivity extends Activity {
    SharedPreferences MySharedPrefs;
    String Player1, Player2, modifiedPlayer1, modifiedPlayer2;
    int SPlayer1, SPlayer2;
    TextView StatP1, StatP2;
    Button reset, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        MySharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        SPlayer1 = MySharedPrefs.getInt("key3", 0);
        SPlayer2 = MySharedPrefs.getInt("key4", 0);
        Player1 = MySharedPrefs.getString("key1", "Player1");
        Player2 = MySharedPrefs.getString("key2", "Player2");
        StatP1 = (TextView)findViewById(R.id.StatP1);
        StatP2 = (TextView)findViewById(R.id.StatP2);

        if(Player1.equals("Player1"))
            modifiedPlayer1 = "";
        else modifiedPlayer1 = " (" + Player1  + ")";

        if(Player2.equals("Player2"))
            modifiedPlayer2 = "";
        else modifiedPlayer2 = " (" + Player2 + ")";

       StatP1.setText("Player 1" + modifiedPlayer1  + ": " + SPlayer1 + " wins");
       StatP2.setText("Player 2" + modifiedPlayer2  + ": " + SPlayer2 + " wins");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void back(View v){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void reset_stats(View v){
        SPlayer1 = 0;
        SPlayer2 = 0;
        StatP1.setText("Player 1" + modifiedPlayer1  + ": " + SPlayer1 + " wins");
        StatP2.setText("Player 2" + modifiedPlayer2  + ": " + SPlayer2 + " wins");
        SharedPreferences.Editor editor = MySharedPrefs.edit();
        editor.putInt("key3", SPlayer1);
        editor.putInt("key4", SPlayer2);
        editor.apply(); // save stats to pref storage
    }
}
