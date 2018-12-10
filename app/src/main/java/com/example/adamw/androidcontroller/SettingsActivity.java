package com.example.adamw.androidcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public void onClickConnect(View view)
    {
        Toast toast = Toast.makeText(this, "jeszcze nie ma nic", Toast.LENGTH_SHORT);
        toast.show();
    }
}
