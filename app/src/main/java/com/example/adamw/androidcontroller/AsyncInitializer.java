package com.example.adamw.androidcontroller;

import android.os.AsyncTask;

import com.jcraft.jsch.Session;

public class AsyncInitializer extends AsyncTask<String,Void,Session> {

    SSHClient client;

    @Override
    protected Session doInBackground(String... strings) {
        try
        {
            client = new SSHClient();
            client.createSession(strings[0], strings[1],strings[2], 22);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client.getSession();
    }
}
