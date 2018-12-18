package com.example.adamw.androidcontroller;
import android.util.Log;
import com.jcraft.jsch.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
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

        try {
            session.connect(500);
        }catch(JSchException e)
        {
            return false;
        }

        return true;
    }

    public Session getSession()
    {
        return session;
    }

    static public boolean sendCommand(Session ses, String command) throws Exception
    {
        // Execute command

        ChannelExec channelssh = (ChannelExec)ses.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        channelssh.setCommand(command);
        channelssh.connect();
        channelssh.disconnect();
        return true;
    }

    static public String sendCommandforResponse(Session ses, String command) throws Exception
    {
        // Execute command

        ChannelExec channelssh = (ChannelExec)ses.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);


        channelssh.setCommand(command);
        InputStream in= channelssh.getInputStream();
        channelssh.connect();
        byte[] tmp=new byte[1024];
        String wiad = "pusto";
        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                wiad = new String(tmp, 0, i);
            }
            if(channelssh.isClosed()){
                if(in.available()>0) continue;
                break;
            }
            try{Thread.sleep(1000);}catch(Exception ee){}
        }
        channelssh.disconnect();
        return wiad;
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

        InputStream in= channelssh.getInputStream();




        // Execute command
        channelssh.setCommand("mkdir nowa");
        channelssh.connect();

        byte[] tmp=new byte[1024];
        while(true){
            while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
            }
            if(channelssh.isClosed()){
                if(in.available()>0) continue;
                break;
            }
            try{Thread.sleep(1000);}catch(Exception ee){}
        }

        channelssh.disconnect();
        return baos.toString();
    }
    public static boolean checkConnection(Session session)
    {
        return (session!=null && session.isConnected()) ? true:false;
    }


}
