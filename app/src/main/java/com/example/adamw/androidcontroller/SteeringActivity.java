package com.example.adamw.androidcontroller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

public class SteeringActivity extends Activity implements JoystickView.JoystickListener{

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
}
