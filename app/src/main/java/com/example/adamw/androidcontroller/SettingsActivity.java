package com.example.adamw.androidcontroller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.transform.Result;

public class SettingsActivity extends Activity {


    private String ip_adress;
    private String password;
    private String username;
    private Async backgroundSession;
    SSHClient client;


    private class Async extends AsyncTask<String,Void,SSHClient>
    {

        @Override
        protected SSHClient doInBackground(String... strings) {
            try
            {
                client = new SSHClient();
                client.createSession(strings[0], strings[1],strings[2], 22);
            } catch (Exception e) {
                e.printStackTrace();
        }
            return client;
        }
        @Override
        protected void onPostExecute(SSHClient result) {
            Indicator indicator = findViewById(R.id.view_indicator);
            indicator.setState(client.checkConnection()? true : false);
            indicator.invalidate();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backgroundSession = new Async();
    }

    @SuppressLint("StaticFieldLeak")

    public void onClickConnect(View view)
    {
        getLoginData();
        try
        {
            client = new Async().execute(username,password,ip_adress).get();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void onClickSendCommand(View view) throws Exception {
        String command;
        EditText editText = findViewById(R.id.editText_command);
        command = editText.getText().toString();
        client.sendCommand(command);
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
