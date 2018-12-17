package com.example.adamw.androidcontroller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jcraft.jsch.Session;

import java.util.ArrayList;

public class SettingsActivity extends Activity {

    public static final String USERNAME_MESSAGE = "m1";
    public static final String HOSTNAME_MESSAGE = "m2";
    public static final String PASSWORD_MESSAGE = "m3";


    private String ip_adress;
    private String password;
    private String username;
    private Session session;

    private IndicatorListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        listener = new IndicatorListener();
    }

    @SuppressLint("StaticFieldLeak")
    public void onClickConnect(View view)
    {
        getLoginData();
        try{
            session = new AsyncInitializerSettions().execute(username,password,ip_adress).get();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if(!listener.isAlive())
        {
            listener.run();
        }
    }

    public void onClickSendCommand(View view) {
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
        EditText editText = findViewById(R.id.editText_ip_number);
        ip_adress = editText.getText().toString();
        EditText passwordText = findViewById(R.id.editText_password);
        password = passwordText.getText().toString();
        EditText usernameText = findViewById(R.id.editText_username);
        username = usernameText.getText().toString();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();

            EditText editText = findViewById(R.id.editText_username);
            String username = editText.getText().toString();
            intent.putExtra(USERNAME_MESSAGE, username);

            editText = findViewById(R.id.editText_ip_number);
            String hostname = editText.getText().toString();
            intent.putExtra(HOSTNAME_MESSAGE, hostname);

            editText = findViewById(R.id.editText_password);
            String password = editText.getText().toString();
            intent.putExtra(PASSWORD_MESSAGE, password);

            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return false;
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

    private class IndicatorListener extends Thread
    {
        Indicator indicator;

        public void run()
        {
            init();
            if(session!=null) indicator.setState(session.isConnected()? true : false);
            indicator.invalidate();
            try
            {
                Thread.sleep(500);
            } catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        private void init()
        {
            if(indicator == null)
            {
                indicator = findViewById(R.id.view_indicator);
            }
        }
    }

    private class AsyncInitializerSettions extends AsyncInitializer
    {
        @Override
        protected void onPostExecute(Session result)
        {
            String message = (result!= null && result.isConnected()) ? "Połaczono" : "Nie udało się połączyć..";
            Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
        }
    }
}
