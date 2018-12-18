package com.example.adamw.androidcontroller;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Session;
import java.util.ArrayList;

/*
Class to send communicates to ssh host. ArrayList of object
in argument list, first array element is a session object,
second command as String
 */

public class AsyncCommunicator extends AsyncTask<Object, Void, Boolean>{
        String message;
        @Override
        protected Boolean doInBackground(Object... objects) {
            try
            {
                Session session = (Session)objects[0];
                if(session == null || !session.isConnected()) return false;
                Log.i("wiadomosc","wysylam wiadomosc");
                message = SSHClient.sendCommandforResponse(session,(String)objects[1]);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
}

