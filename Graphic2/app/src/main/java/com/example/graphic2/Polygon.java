package com.example.graphic2;

import android.graphics.Canvas;

import java.util.ArrayList;

class Polygon {

    private float left;
    private float right;
    private float top;
    private float bottom;

    private ArrayList<Point> vertexes = new ArrayList<>();
    private ArrayList<Point> mboVertexes = new ArrayList<>();


    Polygon(int numOfVertexes, Canvas canvas, int scale) {
        for (int i = 0; i < numOfVertexes; i++) {
            vertexes.add(
                    new Point(rnd(0, canvas.getWidth() / scale),
                            rnd(0, canvas.getHeight() / scale)));
        }
        createMboVertexes();
        setLeft();
        setRight();
        setTop();
        setBottom();
    }

     int rotate(Point A, Point B, Point C) {
         return (B.getX() - A.getX()) *
                 (C.getY() - B.getY()) -
                 (B.getY() - A.getY()) *
                 (C.getX() - B.getX());
     }

    void createMboVertexes() {
        int n = vertexes.size();
        ArrayList<Integer> P = new ArrayList<>();

        for (int i = 0; i < vertexes.size(); i++) {
            P.add(i);
        }

        for (int i = 1; i < n; i++) {
            if (vertexes.get(P.get(i)).getX() < vertexes.get(P.get(0)).getX()) {
                // swap
                int x = P.get(i);
                P.set(i, P.get(0));
                P.set(0, x);
            }
        }

        ArrayList<Integer> H = new ArrayList<>();
        H.add(P.get(0));
        P.remove(0);
        P.add(H.get(0));

        while (true) {
            int right = 0;
            for (int i = 1; i < P.size(); i++) {
                if (rotate(vertexes.get(H.get(H.size() - 1)),
                        vertexes.get(P.get(right)),
                        vertexes.get(P.get(i))) < 0)
                    right = i;
            }
            if (P.get(right) == H.get(0)) {
                break;
            } else {
                H.add(P.get(right));
                P.remove(right);
            }
        }
        for (int index : H){
            mboVertexes.add(vertexes.get(index));
        }
    }

    public ArrayList<Point> getMboVertexes() {
        return mboVertexes;
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
