package com.example.graphic2;

import android.graphics.Canvas;

public class CenterPoint {
    private int x;
    private int y;


    CenterPoint(Canvas canvas, int scale, int x, int y){
        this.x = x + canvas.getWidth() / scale / 2;
        this.y = y + canvas.getHeight() / scale / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
