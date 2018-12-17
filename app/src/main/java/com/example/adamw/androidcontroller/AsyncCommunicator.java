package com.example.adamw.androidcontroller;

import android.os.AsyncTask;
import com.jcraft.jsch.Session;
import java.util.ArrayList;

/*
Class to send communicates to ssh host. ArrayList of object
in argument list, first array element is a session object,
second command as String
 */

public class AsyncCommunicator extends AsyncTask<ArrayList<Object>, Void, Boolean>{

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
}

