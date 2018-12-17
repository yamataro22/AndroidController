package com.example.adamw.androidcontroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Indicator extends View {

    private boolean isEnabled = false;
    private Paint drawPaint;
    private int centerX;
    private int centerY;
    private int radius;

    public Indicator(Context context) {
        super(context);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
    }


    private void init()
    {
        drawPaint = new Paint();
        drawPaint.setColor(Color.RED);
        drawPaint.setStyle(Paint.Style.FILL);

        centerY = getHeight()/2;
        radius = Math.min(getWidth(),getHeight())/3;
        //centerX = getWidth()-2*radius;
        centerX = getWidth()/2;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        init();
        drawPaint.setColor(isEnabled ? Color.GREEN : Color.RED);
        canvas.drawCircle(centerX,centerY,radius,drawPaint);
    }

    public void setState(boolean state)
    {
        isEnabled = state;
    }
    public boolean getState()
    {
        return isEnabled;
    }

}
