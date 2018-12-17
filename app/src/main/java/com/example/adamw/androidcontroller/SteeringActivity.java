package com.example.adamw.androidcontroller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaCas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.jcraft.jsch.Session;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class SteeringActivity extends Activity implements JoystickView.JoystickListener{

    public static final String USERNAME_MESSAGE = "m1";
    public static final String HOSTNAME_MESSAGE = "m2";
    public static final String PASSWORD_MESSAGE = "m3";


    private JoystickBaseClass mFirstJoystick;
    private JoystickBaseClass mSecondJoystick;
    private Session session;
    private String username = "pi";
    private String password = "kawasaki12Z";
    private String hostname = "192.168.4.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steering);
        init();
    }

    private class IndicatorListener extends Thread  //nową klasę należy założyć, powielanie kodu
    {
        Indicator indicator;

        public void run()
        {
            init();
            if(session!=null) indicator.setState(session.isConnected()? true : false);
            indicator.invalidate();
            try
            {
                Thread.sleep(500);
            } catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        private void init()
        {
            if(indicator == null)
            {
                indicator = findViewById(R.id.view_indicator);
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
        Log.i("dimensions:" , xPercent+" "+yPercent+ " ");
    }

    public void onClickConnectSteering(View view)
    {
        if(session == null)
        {
            try {
                session = new AsyncInitializer().execute(username,password,hostname).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new IndicatorListener().run();
    }


    public void onEnableSwitchClicked(View view) {

        boolean on = ((Switch) view).isChecked();

        //w naszym wypadku bedzie to: "odblokuj joysticki"
        if (on){
            enableJoysticks(true);
        }

        //w naszym wypadku bedzie to: "zablokuj joysticki"
        else
        {
            enableJoysticks(false);
        }
    }

    private void enableJoysticks(boolean enable_value) {
        mFirstJoystick.setEnabled(enable_value);
        mSecondJoystick.setEnabled(enable_value);
    }

}
