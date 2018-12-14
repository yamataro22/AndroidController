package com.example.adamw.androidcontroller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class SteeringActivity extends Activity implements JoystickView.JoystickListener{

    private TextView mDioda;
    private JoystickView mFirstJoystick;
    private JoystickView mSecondJoystick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steering);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) {
        Log.i("dimensions:" , xPercent+" "+yPercent+ " ");
    }

    //probny switch czy dziala zmienianie koloru
    public void onSwitchClicked(View view) {

        boolean on = ((Switch) view).isChecked();

        //w naszym wypadku bedzie to: "jesli polaczono"
        if (on){
            mDioda.setBackgroundResource(R.drawable.circle);
        }

        //w naszym wypadku bedzie to: "jesli nie polaczono"
        else
        {
            mDioda.setBackgroundResource(R.drawable.circle2);
        }
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
