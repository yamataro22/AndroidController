package com.example.adamw.androidcontroller;
import android.graphics.PorterDuff;
import android.util.Log;

import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * Created by adamw on 13.12.2018.
 */

public class SSHClient {

    private Session session;

    public boolean createSession(String username,String password,String hostname,int port) throws Exception
    {
        JSch jsch = new JSch();

        session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        return true;
    }

    public Session getSession()
    {
        return session;
    }

    public boolean sendCommand(String command) throws Exception
    {
        // Execute command

        ChannelExec channelssh = (ChannelExec)session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);
        channelssh.setCommand(command);
        channelssh.connect();
        channelssh.disconnect();
        channelssh = null;
        return true;
    }

    static public boolean sendCommand(Session ses, String command) throws Exception
    {
        // Execute command

        ChannelExec channelssh = (ChannelExec)ses.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);
        channelssh.setCommand(command);
        channelssh.connect();
        Log.i("ssh","connect");
        channelssh.disconnect();
        Log.i("ssh","disconnect");
        channelssh = null;
        Log.i("ssh","null, teraz return");
        return true;
    }


    public static String executeRemoteCommand(String username,String password,String hostname,int port)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        channelssh.setCommand("mkdir nowa");
        channelssh.connect();
        channelssh.disconnect();
        return baos.toString();
    }
    boolean checkConnection()
    {
        return session.isConnected() ? true:false;
    }


}
