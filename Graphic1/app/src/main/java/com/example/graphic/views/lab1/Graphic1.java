package com.example.graphic.views.lab1;

import android.graphics.Canvas;

public class Graphic1 extends AbstractGraphic {
    private static float step = 0.01f;    // шаг
    private static final float BEGIN = -5;
    private static final float END = 5;


    @Override
    public void createGraphic(Canvas canvas, float scale){
        MyPoint screenCenter = GraphicCreator.getCenter(canvas);
        for (float i = BEGIN; i <= END; i += step) {

            if (i > -2 && i < 0) step = 0.001f;
            else step = 0.01f;

            float x =  (i * scale) + screenCenter.getX();
            float y =  -(f(i) * scale) + screenCenter.getY(); // -1 разврорачивает систему координат

            canvas.drawCircle(x, y, 3f, MyPaint.getRedPaint());
        }
    }

    private float f(float x){
        return (float) ((Math.pow(x,2) - 4) / (x + 1));
    }
}