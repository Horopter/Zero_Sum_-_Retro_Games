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
import android.widget.EditText;
import android.widget.Toast;


public class PlayerRegistrationActivity extends Activity {
    String Player1, Player2;
    SharedPreferences MySharedPrefs;
    EditText Player1_name, Player2_name;
    Button saveBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MySharedPrefs =PreferenceManager.getDefaultSharedPreferences(this);
        Player1 = MySharedPrefs.getString("key1", "Player1");
        Player2 = MySharedPrefs.getString("key2", "Player2");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Player1_name = (EditText)findViewById(R.id.Player1_name);
        Player2_name = (EditText)findViewById(R.id.Player2_name);
        saveBtn = (Button)findViewById(R.id.BtnSave);
        backBtn = (Button)findViewById(R.id.BtnBack);

        if(!Player1.equals("Player1"))
            Player1_name.setText(Player1);
        if(!Player2.equals("Player2"))
            Player2_name.setText(Player2);
        saveBtn.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
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

    public void save(View v){
        if(!Player1_name.getText().toString().equals("") && !Player2_name.getText().toString().equals("") &&
                Player1_name.getText().toString().length() < 21 && Player2_name.getText().toString().length() < 21)
        {
            SharedPreferences.Editor editor = MySharedPrefs.edit();
            editor.putString("key1", Player1_name.getText().toString());
            editor.putString("key2", Player2_name.getText().toString());
            editor.apply();
            Toast toast = Toast.makeText(getApplicationContext(), "Names have been saved", Toast.LENGTH_SHORT);
            toast.show();

        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Name fields can not be empty and max 20 characters", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void back(View v){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
