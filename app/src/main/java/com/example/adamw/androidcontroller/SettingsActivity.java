package com.example.adamw.androidcontroller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    SSHClient client;
    private String ip_adress;
    private String password;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        client = new SSHClient();
    }

    public void onClickConnect(View view)
    {
        getLoginData();
        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    client.createSession(username, password,ip_adress, 22);
                    client.sendCommand("mkdir dupa");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
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
