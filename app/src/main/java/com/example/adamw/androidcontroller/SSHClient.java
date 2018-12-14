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
    private ChannelExec channelssh;

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
        channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);
        return true;
    }

    public boolean sendCommand(String command) throws Exception
    {
        // Execute command
        channelssh.setCommand(command);
        channelssh.connect();
        channelssh.disconnect();
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
