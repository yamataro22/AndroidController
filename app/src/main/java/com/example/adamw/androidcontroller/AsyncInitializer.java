package com.example.adamw.androidcontroller;

import android.os.AsyncTask;

import com.jcraft.jsch.Session;

import java.net.InetAddress;

public class AsyncInitializer extends AsyncTask<String,Void,Session> {

    SSHClient client;

    @Override
    protected Session doInBackground(String... strings) {
        try
        {
            if(InetAddress.getByName(strings[2]).isReachable(100))
            {
                client = new SSHClient();
                client.createSession(strings[0], strings[1],strings[2], 22);
            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client.getSession();
    }

}
