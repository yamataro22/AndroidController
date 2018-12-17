package com.example.adamw.androidcontroller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainScreen extends AppCompatActivity {

    private class LoginData
    {
        String username;
        String hostname;
        String pass;

        public LoginData(String username, String hostname, String pass) {
            this.username = username;
            this.hostname = hostname;
            this.pass = pass;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }



        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }


        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

    }

    private LoginData loginParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginParameters = new LoginData("pi","192.168.4.1","xxx");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent intent = new Intent(this, SettingsActivity.class);
            //startActivity(intent);

            Intent intent = new Intent(this, SettingsActivity.class);
            int requestCode = 2; // Or dowolny
            startActivityForResult(intent, requestCode);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSteering(View view)
    {
        Intent intent = new Intent(this, SteeringActivity.class);
        startActivity(intent);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if(requestCode == 2)
        {
            loginParameters.setUsername(data.getStringExtra(SettingsActivity.USERNAME_MESSAGE));
            loginParameters.setHostname(data.getStringExtra(SettingsActivity.HOSTNAME_MESSAGE));
            loginParameters.setPass(data.getStringExtra(SettingsActivity.PASSWORD_MESSAGE));
        }
    }
}
