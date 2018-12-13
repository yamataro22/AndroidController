package com.example.adamw.androidcontroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by adamw on 11.12.2018.
 */

public class JoystickView extends JoystickBaseClass{

    float centerX;
    float centerY;
    float baseRadius;
    float hatRadius;

    public JoystickView(Context context)
    {
        super(context);
    }
    public JoystickView(Context c, AttributeSet a, int style)
    {
        super(c,a,style);
    }

    public JoystickView(Context c, AttributeSet a)
    {
        super(c,a);
    }

    private void setupDimensions()
    {
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        baseRadius = Math.min(getWidth(), getHeight()) / 3;
        hatRadius = Math.min(getWidth(), getHeight()) / 5;
    }

    private void drawJoystick(float newX, float newY)
    {

        if(getHolder().getSurface().isValid())
        {
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);   //czyści tło
            colors.setARGB(255,50,50,50);                           //kolor podstawy joysticka
            myCanvas.drawCircle(centerX,centerY,baseRadius,colors);            //rysuj podstawę
            colors.setARGB(255,0,0,255);                           //kolor podstawy joysticka
            myCanvas.drawCircle(newX,newY,hatRadius,colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }










    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setupDimensions();
        drawJoystick(centerX,centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.equals(this))
        {
            if(motionEvent.getAction() != MotionEvent.ACTION_UP)
            {
                float displacement = (float) Math.sqrt(Math.pow(motionEvent.getX() - centerX, 2) + Math.pow(motionEvent.getY() - centerY, 2));
                if(displacement < baseRadius)
                {
                    drawJoystick(motionEvent.getX(), motionEvent.getY());
                    joystickCallback.onJoystickMoved((motionEvent.getX() - centerX) / baseRadius,
                            (motionEvent.getY() - centerY) / baseRadius, getId());
                }
                else
                {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (motionEvent.getX() - centerX) * ratio;
                    float constrainedY = centerY + (motionEvent.getY() - centerY) * ratio;
                    drawJoystick(constrainedX, constrainedY);
                    joystickCallback.onJoystickMoved((constrainedX - centerX) / baseRadius,
                            (constrainedY - centerY) / baseRadius, getId());
                }

            }
            else
            {
                drawJoystick(centerX, centerY);
                joystickCallback.onJoystickMoved(0, 0, getId());
            }
        }
        return true;
    }
}
