package com.example.adamw.androidcontroller;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by adamw on 13.12.2018.
 */

public abstract class JoystickBaseClass  extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    float centerX;
    float centerY;
    public JoystickView.JoystickListener joystickCallback;

    public interface JoystickListener
    {
        void onJoystickMoved(float xPercent, float yPercent, int source);
    }

    public JoystickBaseClass(Context context) {
        super(context);
        setOnTouchListener(this);


        getHolder().addCallback(this);

        //ustawia przeźroczyste tło
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        if(context instanceof JoystickListener)
            joystickCallback = (JoystickListener) context;
    }

    public JoystickBaseClass(Context c, AttributeSet a, int style)
    {
        super(c,a,style);
        setOnTouchListener(this);

        //ustawia przeźroczyste tło
        getHolder().addCallback(this);
        setZOrderOnTop(true);


        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        if(c instanceof JoystickListener)
            joystickCallback = (JoystickListener) c;
    }

    public JoystickBaseClass(Context c, AttributeSet a)
    {
        super(c,a);
        setOnTouchListener(this);
        getHolder().addCallback(this);
        //ustawia przeźroczyste tło
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        if(c instanceof JoystickListener)
            joystickCallback = (JoystickListener) c;
    }

    @Override
    abstract public void surfaceCreated(SurfaceHolder surfaceHolder);

    @Override
    abstract public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2);

    @Override
    abstract public void surfaceDestroyed(SurfaceHolder surfaceHolder);

    @Override
    abstract public boolean onTouch(View view, MotionEvent motionEvent);

}
