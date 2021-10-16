package com.example.graphic.views.lab3;

import android.graphics.Color;
import android.graphics.Paint;

public class MyPaint {
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final int BLUE = 2;
    public static final int RED = 3;
    public static final int GREEN = 4;

    private static Paint paint = new Paint();

    static Paint getWhitePaint() {
        paint.setColor(Color.WHITE);
        return paint;
    }
    static Paint getBlackPaint() {
        paint.setColor(Color.BLACK);
        return paint;
    }
    static Paint getRedPaint() {
        paint.setColor(Color.RED);
        return paint;
    }
    static Paint getGreenPaint() {
        paint.setColor(Color.GREEN);
        return paint;
    }
    static Paint getBluePaint() {
        paint.setColor(Color.BLUE);
        return paint;
    }
    static Paint getPaint(int color){
        switch (color){
            case WHITE:
                return getWhitePaint();
            case BLUE:
                return getBluePaint();
            case RED:
                return getRedPaint();
            case GREEN:
                return getGreenPaint();
            default:
                return getBlackPaint();
        }
    }
}

