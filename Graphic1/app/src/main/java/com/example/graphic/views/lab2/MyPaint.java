package com.example.graphic.views.lab2;

import android.graphics.Color;
import android.graphics.Paint;

public class MyPaint {
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
    static Paint getCyanPaint() {
        paint.setColor(Color.CYAN);
        return paint;
    }
    static Paint getMagentaPaint() {
        paint.setColor(Color.MAGENTA);
        return paint;
    }
}

