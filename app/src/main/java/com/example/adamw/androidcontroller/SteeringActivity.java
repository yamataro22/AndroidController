package com.example.adamw.androidcontroller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.media.MediaCas;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.Session;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SteeringActivity extends Activity implements JoystickView.JoystickListener{

    public static final String USERNAME_MESSAGE = "ms4";
    public static final String HOSTNAME_MESSAGE = "ms5";
    public static final String PASSWORD_MESSAGE = "ms6";


    private JoystickBaseClass mFirstJoystick;
    private JoystickBaseClass mSecondJoystick;
    private Session session;

    private String username = "pi";
    private String password = "";
    private String hostname = "192.168.4.1";
    private IndicatorListener listener;

    String[] joystickCoords;
    private dimensionsThread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steering);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        init();


        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Intent intent = getIntent();
            username = intent.getStringExtra(USERNAME_MESSAGE);
            password = intent.getStringExtra(PASSWORD_MESSAGE);
            hostname = intent.getStringExtra(HOSTNAME_MESSAGE);

        }
        listener= new IndicatorListener();
        joystickCoords = new String[3];
        joystickCoords[0] = "0";
        joystickCoords[1] = "0";
        joystickCoords[2] = "0";

        thread = new dimensionsThread();
    }

    private class dimensionsThread extends Thread
    {
        @Override
        public void run()
        {
            while(true)
            {
                String msg = "echo " + joystickCoords[0]+ " " + joystickCoords[1] + " " + joystickCoords[2] + " > dimensions";
                Log.i("dimensions", msg);

                new AsyncCoordSender().execute((Object)session, (Object)msg);
                try {
                    Thread.sleep(100);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
    }

    private class AsyncInitializerSteering extends AsyncInitializer
    {
        @Override
        protected void onPostExecute(Session session)
        {
            String message = (session!= null && session.isConnected()) ? "Połączono" : "Nie udało się połączyć..";
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

        }
    }


    private class IndicatorListener extends Thread  //nową klasę należy założyć, powielanie kodu
    {
        Indicator indicator;

        public void run()
        {
            init();

            if(session!=null) indicator.setState(session.isConnected()? true : false);
            indicator.invalidate();
            sleepMilis(1000);
        }
        private void init()
        {
            if(indicator == null)
            {
                indicator = findViewById(R.id.view_indicator);
            }
        }
        private void sleepMilis(long milis)
        {
            try
            {
                Thread.sleep(milis);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    private void init()
    {
        mFirstJoystick = findViewById(R.id.firstJoystick);
        mSecondJoystick = findViewById(R.id.secondJoystick);
        enableJoysticks(false);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) {
        if(source == 2131558561)    //left joystick
        {
            int x = (int)(xPercent*100);
            int y = (int)(-yPercent*100);
            joystickCoords[0] = x+"";
            joystickCoords[1] = y+"";
        }
        else if (source == 2131558563)  //right joystick
        {
            int y = (int)(-yPercent*100);
            joystickCoords[2] = y+"";
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void onClickConnectSteering(View view)
    {

            try {
                session = new AsyncInitializerSteering().execute(username,password,hostname).get();
            } catch (Exception e) {
                e.printStackTrace();
            }


        if(!listener.isAlive())
        {
            listener.run();
        }
    }


    public void onEnableSwitchClicked(View view) {

        boolean on = ((Switch) view).isChecked();

        // "odblokuj joysticki"
        if (on){
            enableJoysticks(true);
            if(!thread.isAlive()) thread.start();
        }

        //w naszym wypadku bedzie to: "zablokuj joysticki"
        else
        {
            enableJoysticks(false);
            //thread.stop();
        }
    }

    private void enableJoysticks(boolean enable_value) {
        mFirstJoystick.setEnabled(enable_value);
        mSecondJoystick.setEnabled(enable_value);
    }

    private class AsyncCoordSender extends AsyncCommunicator
    {
        @Override
        protected Boolean doInBackground(Object... objects) {
            try
            {
                Session session = (Session)objects[0];
                if(session == null || !session.isConnected()) return false;
                Log.i("dimensions", "wysyłam "+(String)objects[1]);
                SSHClient.sendCommand(session,(String)objects[1]);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        @Override
        public void onPostExecute(Boolean result)
        {
            Log.i("dimensions", "wysłano");
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        thread.interrupt();
    }
}
