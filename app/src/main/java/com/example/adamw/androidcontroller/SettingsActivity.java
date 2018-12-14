package com.example.adamw.androidcontroller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jcraft.jsch.Session;

import java.util.ArrayList;

public class SettingsActivity extends Activity {

    private String ip_adress;
    private String password;
    private String username;
    SSHClient client;
    Session session;


    private class Async extends AsyncTask<String,Void,Session>
    {

        @Override
        protected Session doInBackground(String... strings) {
            try
            {
                client = new SSHClient();
                client.createSession(strings[0], strings[1],strings[2], 22);
                client.sendCommand("mkdir fakegkjnwg");
            } catch (Exception e) {
                e.printStackTrace();
        }
            return client.getSession();
    }
        @Override
        protected void onPostExecute(Session result) {
            Indicator indicator = findViewById(R.id.view_indicator);
            indicator.setState(client.checkConnection()? true : false);
            indicator.invalidate();
        }
    }

    private class AsyncExe extends AsyncTask<ArrayList<Object>, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(ArrayList<Object>... objects) {
            try
            {
                Session session = (Session)objects[0].get(0);
                if(session == null || !session.isConnected()) return false;
                SSHClient.sendCommand(session,(String)objects[1].get(0));

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean state) {
            if(state)
            {
                Toast.makeText(getApplicationContext(), "Wysłano!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Nie udało się!", Toast.LENGTH_SHORT).show();
            }

    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @SuppressLint("StaticFieldLeak")
    public void onClickConnect(View view)
    {
        getLoginData();
        try{
            session = new Async().execute(username,password,ip_adress).get();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onClickSendCommand(View view) throws Exception {
        String command;
        EditText editText = findViewById(R.id.editText_command);
        command = editText.getText().toString();
        ArrayList<Object> ses = new ArrayList<>();
        ses.add(session);
        ArrayList<Object> str = new ArrayList<>();
        str.add(command);
        new AsyncExe().execute(ses,str);
    }


    public void getLoginData()
    {
        EditText editText = findViewById(R.id.ip_number);
        ip_adress = editText.getText().toString();
        EditText passwordText = findViewById(R.id.password);
        password = passwordText.getText().toString();
        EditText usernameText = findViewById(R.id.editText_username);
        username = usernameText.getText().toString();
    }
}
