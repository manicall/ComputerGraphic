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

    int SCALE = 15;
    int centerX;
    int centerY;

    public MyView(Context context) {
        super(context);
    }

    ArrayList<ArrayList<Point>> points2D;
    Polygon polygon;



    boolean flag = false;
    @Override
    public void onDraw(@NonNull Canvas canvas) {
        canvas.scale(SCALE, SCALE);



        if (flag == false) {
            polygon = new Polygon(5, canvas, SCALE);
            points2D = createField(canvas, Color.BLACK);
            Renderer renderer = new Renderer(points2D);
            //createSimplecdaPolygon(canvas, renderer, Color.RED);

            fillWithStack(canvas);
            //createBresenhamMBOPolygon(canvas, renderer, Color.GREEN);
            //flag = !flag;
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
    public void createBresenhamPolygon(Canvas canvas, Renderer renderer, int color) {
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

    public void createBresenhamMBOPolygon(Canvas canvas, Renderer renderer, int color) {
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

        for (Point vertex : vertexes){
            renderer.bresenham(
                    vertex.getX(), vertex.getY(), // p1
                    vertex.getX(), vertex.getY(), // p2
                    Color.MAGENTA);
        }
    }

    public void createSimplecdaPolygon(Canvas canvas, Renderer renderer, int color) {
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

    void recFill(int x, int y){
        Filler filler = new Filler(points2D);
        int oldColor = Color.BLACK;
        int newColor = Color.CYAN;
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

    void fillLineByLine(int x, int y){
        int oldColor = Color.BLACK;
        int newColor = Color.CYAN;
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

    void fillWithStack(Canvas canvas){
        Filler filler = new Filler(points2D);
        int [] arx = new int[polygon.getMboVertexes().size()];
        int [] ary = new int[polygon.getMboVertexes().size()];

        for(int i = 0; i < polygon.getMboVertexes().size(); i++){
            arx[i] = polygon.getMboVertexes().get(i).getX();
            ary[i] = polygon.getMboVertexes().get(i).getY();
        }

        filler.fillWithStoringInStack(arx, ary, polygon.getMboVertexes().size());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //recFill(Math.round(event.getX() / SCALE), Math.round(event.getY() / SCALE));
        //fillLineByLine(Math.round(event.getX() / SCALE), Math.round(event.getY() / SCALE));
        Log.d("TAG", "onTouchEvent: " + Math.round(event.getX() / SCALE) +
                " " + Math.round(event.getY() / SCALE));
        //invalidate();
        return super.onTouchEvent(event);
    }

}
