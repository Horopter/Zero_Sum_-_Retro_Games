package com.horopter.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class GameActivity extends Activity {
    SharedPreferences MySharedPrefs;
    int SPlayer1, SPlayer2;
    String Player1, Player2;
    TextView[][] Field = new TextView[3][3]; 
    TextView TurnState;
    static int turn = 0 ;
    static boolean TurnFlip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        MySharedPrefs =PreferenceManager.getDefaultSharedPreferences(this); 
        Player1 = MySharedPrefs.getString("key1", "Player1");
        Player2 = MySharedPrefs.getString("key2", "Player2");
        SPlayer1 = MySharedPrefs.getInt("key3", 0);
        SPlayer2 = MySharedPrefs.getInt("key4", 0);

        TurnState = (TextView)findViewById(R.id.TurnState);
        Field[0][0] = (TextView)findViewById(R.id.textView1);
        Field[0][1] = (TextView)findViewById(R.id.textView2);
        Field[0][2] = (TextView)findViewById(R.id.textView3);
        Field[1][0] = (TextView)findViewById(R.id.textView4);
        Field[1][1] = (TextView)findViewById(R.id.textView5);
        Field[1][2] = (TextView)findViewById(R.id.textView6);
        Field[2][0] = (TextView)findViewById(R.id.textView7);
        Field[2][1] = (TextView)findViewById(R.id.textView8);
        Field[2][2] = (TextView)findViewById(R.id.textView9);
        resetField();
        TurnFlip = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void backToMenu(View v){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void useField(View v)
    { 
        TextView field = (TextView) v;
        if(field.getText().toString().equals(""))
        {
            if(turn % 2 != 0)
            {
                if(!TurnFlip) {
                    field.setText("X");
                    field.setTextColor(Color.TRANSPARENT);
                    field.setBackgroundResource(R.drawable.cat);
                    TurnState.setText(Player2 + "'s turn");
                } else {
                    field.setText("O");
                    field.setTextColor(Color.TRANSPARENT);
                    field.setBackgroundResource(R.drawable.dog);
                    TurnState.setText(Player1 + "'s turn");
                }
            }
            else {
                if(!TurnFlip){
                    field.setText("O");
                    field.setTextColor(Color.TRANSPARENT);
                    field.setBackgroundResource(R.drawable.dog);
                    TurnState.setText(Player1 + "'s turn");
                } else {
                    field.setText("X");
                    field.setTextColor(Color.TRANSPARENT);
                    field.setBackgroundResource(R.drawable.cat);
                    TurnState.setText(Player2 + "'s turn");
                }

            }
            if(!checkForWin())
                turn++;
        }

    }

    public void resetField()
    {
        for(int row = 0; row < 3; row++)
            for(int col = 0; col < 3; col++) {
                Field[row][col].setText("");
                Field[row][col].setBackgroundResource(0);
            }
        turn = 0;

        if(TurnFlip)
            TurnState.setText(Player2 + "'s turn");
        else
            TurnState.setText(Player1 + "'s turn");
    }

    public boolean checkForWin(){
        if(turn > 3)
        {
         for(int i =0; i < 3; i++) {
             if (isRowFilled(i) || isColumnFilled(i)) {
                 results();
                 return true;
             }
         }
         if(isDiagonalFilled()) {
             results();
             return true;
         }
         if(turn == 8) {
             results();
         }
        }
        return false;
    }

    private boolean isRowFilled(int row){
        
        return (Field[row][0].getText() != "" && Field[row][0].getText() == Field[row][1].getText() &&
                Field[row][1].getText() == Field[row][2].getText());

    }

    private boolean isColumnFilled(int col){
        return (Field[0][col].getText() != "" && Field[0][col].getText() == Field[1][col].getText() &&
                Field[1][col].getText() == Field[2][col].getText());
    }

    private boolean isDiagonalFilled(){
        return ((Field[0][0].getText() != "" && Field[0][0].getText() == Field[1][1].getText() &&
                Field[1][1].getText() == Field[2][2].getText()) ||
                (Field[0][2].getText() != "" && Field[0][2].getText() == Field[1][1].getText() &&
                        Field[1][1].getText() == Field[2][0].getText()));
    }

    private void results(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(turn == 8){
            builder.setMessage("It is a draw");
        } else if(turn % 2 == 0 && !TurnFlip) // player 1 win
        {
            builder.setMessage(Player1 + " has won!");
            SPlayer1++;

        } else {
            builder.setMessage(Player2 + " has won!");
            SPlayer2++;
        }

        builder.setCancelable(false);
        builder.setNegativeButton("Play Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        resetField();
                    }
                });

        builder.setPositiveButton("Go to Menu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        TurnFlip = !TurnFlip;
    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = MySharedPrefs.edit();
        editor.putInt("key3", SPlayer1);
        editor.putInt("key4", SPlayer2);
        editor.apply();
        super.onPause();
    }
}
