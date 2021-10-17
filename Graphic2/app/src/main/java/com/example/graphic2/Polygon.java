package com.example.graphic2;

import android.graphics.Canvas;

import java.util.ArrayList;

class Polygon {

    private float left;
    private float right;
    private float top;
    private float bottom;

    private ArrayList<Point> vertexes = new ArrayList<>();

    Polygon(int numOfVertexes, Canvas canvas, int scale) {
        for (int i = 0; i < numOfVertexes; i++) {
            vertexes.add(
                    new Point(rnd(0, canvas.getWidth() / scale),
                            rnd(0, canvas.getHeight() / scale)));
        }

        setLeft();
        setRight();
        setTop();
        setBottom();
    }

    private void setLeft() {
        float min = vertexes.get(0).getX();
        for (int i = 1; i < vertexes.size(); i++) {
            min = Math.min(vertexes.get(i).getX(), min);
        }
        left = min;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    private void setRight() {
        float max = vertexes.get(0).getX();
        for (int i = 1; i < vertexes.size(); i++) {
            max = Math.max(vertexes.get(i).getX(), max);
        }
        right = max;
    }

    public float getTop() {
        return top;
    }

    private void setTop() {
        float min = vertexes.get(0).getY();
        for (int i = 1; i < vertexes.size(); i++) {
            min = Math.min(vertexes.get(i).getY(), min);
        }
        top = min;
    }

    public float getBottom() {
        return bottom;
    }

    private void setBottom() {
        float max = vertexes.get(0).getY();
        for (int i = 1; i < vertexes.size(); i++) {
            max = Math.max(vertexes.get(i).getY(), max);
        }
        bottom = max;
    }

    public ArrayList<Point> getVertexes() {
        return vertexes;
    }


    static int rnd(float min, float max) {
        max -= min;
        return (int) ((Math.random() * ++max) + min);
    }
}
