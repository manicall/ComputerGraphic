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

    public Point clone() {return new Point(x, y); }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int diffX(Point point){
        return this.x - point.x;
    }

    int diffY(Point point){
        return this.y - point.y;
    }

    public void increaseX(int dx){
        x += dx;
    }

    public void increaseY(int dy){
        y += dy;
    }

    public void decreaseX(int dx){
        x -= dx;
    }

    public void decreaseY(int dy){
        y -= dy;
    }

    public void incrementX() { ++x; }

    public void incrementY() { ++y; }

    public void increment(int coordinate) {
        if (coordinate == x) incrementX();
        else if (coordinate == y) incrementY();
    }

    public void decrement(int coordinate) {
        if (coordinate == x) decrementX();
        else if (coordinate == y) decrementY();
    }

    public void decrementX() { --x; }

    public void decrementY() { --y; }

    public int getColor(){
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void swap(){
        int temp = x;
        x = y;
        y = temp;
    }
}
