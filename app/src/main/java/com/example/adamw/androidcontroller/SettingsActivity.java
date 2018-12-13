package com.example.adamw.androidcontroller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    SSHClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        client = new SSHClient();
    }

    public void onClickConnect(View view)
    {
        client = new SSHClient();
        new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    client.createSession("pi", "kawasaki12Z","192.168.4.1", 22);
                    client.sendCommand("mkdir dupa");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
    }
}
