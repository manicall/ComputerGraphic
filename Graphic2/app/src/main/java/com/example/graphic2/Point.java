package com.example.graphic2;

import android.graphics.Paint;

public class Point {
    private int x;
    private int y;
    private int color;


    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    Point(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor(){
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
