package com.example.graphic2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class MyView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int SCALE = 20;
    int currentMode = 0;
    int FIELD_COLOR = Color.BLACK;

    int bresenhamMboBorderColor;

    static final int SIMPLE_DDA__STRING_FILL_WITH_SEED = 0;
    static final int BRESENHAM__WITH_STORING_POINTS_OF_BORDER_IN_STACK = 1;
    static final int BRESENHAM__SIMPLE_FILL_WITH_SEED_WITH_RECURSION = 2;

    void setMode(int mode){
        currentMode = mode;
        isPolygonCreated = false;
    }

    public MyView(Context context) {
        super(context);
    }

    ArrayList<ArrayList<Point>> points2D;
    Polygon polygon;



    boolean isPolygonCreated = false;
    @Override
    public void onDraw(@NonNull Canvas canvas) {
        canvas.scale(SCALE, SCALE);

        if (isPolygonCreated == false) {
            polygon = new Polygon(7, canvas, SCALE);
            points2D = createField(canvas, FIELD_COLOR);
            Renderer renderer = new Renderer(points2D);
            switch (currentMode) {
                case SIMPLE_DDA__STRING_FILL_WITH_SEED:
                    createSimplecdaPolygon(renderer, Color.RED);
                    break;
                case BRESENHAM__WITH_STORING_POINTS_OF_BORDER_IN_STACK:
                    bresenhamMboBorderColor = Color.GREEN;
                    createBresenhamMBOPolygon(renderer, bresenhamMboBorderColor);
                    break;
                case BRESENHAM__SIMPLE_FILL_WITH_SEED_WITH_RECURSION:
                    createBresenhamPolygon(renderer, Color.BLUE);
                    break;
            }
            isPolygonCreated = !isPolygonCreated;
        }

        createPicture(canvas);
    }

    ArrayList<ArrayList<Point>> createField(Canvas canvas, int color) {
        ArrayList<ArrayList<Point>> points2D = new ArrayList<>();
        for (int i = 0; i <= canvas.getWidth() / SCALE; i++) {
            ArrayList<Point> points = new ArrayList<>();
            for (int j = 0; j <= canvas.getHeight() / SCALE; j++) {
                points.add(new Point(i, j, color));
            }
            points2D.add(points);
        }
        return points2D;
    }

    void createPicture(Canvas canvas){
        for (ArrayList<Point> points : points2D){
            for (Point point : points){
                Paint paint = new Paint();
                paint.setColor(point.getColor());
                canvas.drawPoint(point.getX(), point.getY(), paint);
            }
        }
    }

    public void createBresenhamPolygon(Renderer renderer, int color) {
        ArrayList<Point> vertexes = polygon.getVertexes();
        //соединение вершин кроме первой и последней
        int j = vertexes.size() - 1;
        for (int i = 0; i < vertexes.size(); i++) {
            renderer.bresenham(
                    vertexes.get(i).getX(), vertexes.get(i).getY(), // p1
                    vertexes.get(j).getX(), vertexes.get(j).getY(), // p2
                    color);
            j = i;
        }
    }

    public void createBresenhamMBOPolygon(Renderer renderer, int color) {
        ArrayList<Point> vertexes = polygon.getMboVertexes();
        //соединение вершин кроме первой и последней
        int j = vertexes.size() - 1;
        for (int i = 0; i < vertexes.size(); i++) {
            renderer.bresenham(
                    vertexes.get(i).getX(), vertexes.get(i).getY(), // p1
                    vertexes.get(j).getX(), vertexes.get(j).getY(), // p2
                    color);
            j = i;
        }


    }

    void paintVertexes(Renderer renderer, ArrayList<Point> vertexes, int color){
        for (Point vertex : vertexes){
            renderer.bresenham(
                    vertex.getX(), vertex.getY(), // p1
                    vertex.getX(), vertex.getY(), // p2
                    color);
        }
    }

    public void createSimplecdaPolygon(Renderer renderer, int color) {
        ArrayList<Point> vertexes = polygon.getVertexes();
        //соединение вершин кроме первой и последней
        int j = vertexes.size() - 1;
        for (int i = 0; i < vertexes.size(); i++) {
            renderer.simplecda(
                    vertexes.get(i).getX(), vertexes.get(i).getY(), // p1
                    vertexes.get(j).getX(), vertexes.get(j).getY(), // p2
                    color);
            j = i;
        }
    }

    void recFill(int oldColor, int newColor, int x, int y){
        Filler filler = new Filler(points2D);
        try {
            // пытаемся заполнить
            filler.recFill(oldColor, newColor, x, y);
        } catch (Exception e) {
            // устраняем последствия не правильного заполнения
            try {
                filler.recFill(newColor, oldColor, x, y);
            } catch (Exception exception) {

            }
        }
    }

    void fillLineByLine(int oldColor, int newColor, int x, int y){
        Filler filler = new Filler(points2D);
        try {
            filler.fillLineByLine(oldColor, newColor, x, y);
        } catch (Exception e) {
            // устраняем последствия не правильного заполнения
            try {
                filler.fillLineByLine(newColor, oldColor, x, y);
            } catch (Exception exception) {

            }
        }
    }

    void fillWithStack(int borderColor, int color){
        Filler filler = new Filler(points2D);
        filler.fillWithStoringInStack(polygon, borderColor, color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int oldColor = FIELD_COLOR;
        switch (currentMode){
            case SIMPLE_DDA__STRING_FILL_WITH_SEED:
                int newColor = Color.CYAN;
                fillLineByLine(oldColor, newColor, Math.round(event.getX() / SCALE), Math.round(event.getY() / SCALE));
                break;
            case BRESENHAM__WITH_STORING_POINTS_OF_BORDER_IN_STACK:
                int color = Color.MAGENTA;
                fillWithStack(bresenhamMboBorderColor, color);
                break;
            case BRESENHAM__SIMPLE_FILL_WITH_SEED_WITH_RECURSION:
                newColor = Color.YELLOW;
                recFill(oldColor, newColor, Math.round(event.getX() / SCALE), Math.round(event.getY() / SCALE));
                break;

        }
        Log.d("TAG", "onTouchEvent: " + Math.round(event.getX() / SCALE) +
                " " + Math.round(event.getY() / SCALE));
        invalidate();
        return super.onTouchEvent(event);
    }

}
