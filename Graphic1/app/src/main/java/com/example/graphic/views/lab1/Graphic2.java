package com.example.graphic.views.lab1;

import android.graphics.Canvas;

public class Graphic2 extends AbstractGraphic {
    private static final float step = 0.01f; // шаг
    private static final float BEGIN = -5;
    private static final float END = 5;

    @Override
    public void createGraphic(Canvas canvas, float scale){
        MyPoint screenCenter = GraphicCreator.getCenter(canvas);
        for (float i = BEGIN; i <= END; i += step) {
            float x =  (getX(i) * scale) + screenCenter.getX();
            float y =  -(getY(i) * scale) + screenCenter.getY(); // -1 разврорачивает систему координат

            canvas.drawCircle(x, y, 3f, MyPaint.getRedPaint());
        }
    }

    private float getX(float t){
        return (float) (Math.pow(t,2)/(1 + Math.pow(t,2)));
    }
    private float getY(float t){
        return (float) (t*(1-Math.pow(t,2))/(1+Math.pow(t,2)));
    }
}