package com.example.graphic2;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

public class Filler {

    ArrayList<ArrayList<Point>> points2D;

    public Filler(ArrayList<ArrayList<Point>> points2D) {
        this.points2D = points2D;

    }

    void recFill(int oldColor, int newColor, int x, int y) {
        if (points2D.get(x).get(y).getColor() != oldColor) return;
        points2D.get(x).get(y).setColor(newColor);
        recFill(oldColor, newColor, x - 1, y);
        recFill(oldColor, newColor, x + 1, y);
        recFill(oldColor, newColor, x, y - 1);
        recFill(oldColor, newColor, x, y + 1);
    }

    void fillLineByLine(int oldColor, int newColor, int x, int y) {
        FillerLineByLineWithSeed filler = new FillerLineByLineWithSeed();
        filler.flstr(oldColor, newColor, x, y);
    }

    void fillWithStoringInStack(Polygon polygon){
        FillerWithStoringBorderPointsInTheStack filler =
                new FillerWithStoringBorderPointsInTheStack();
        filler.fillStack(polygon);
    }

    int getPixel(int x, int y) {
        return points2D.get(x).get(y).getColor();
    }

    void setPixel(int x, int y, int color) {
        points2D.get(x).get(y).setColor(color);
    }

    class FillerWithStoringBorderPointsInTheStack {
        void fillStack(Polygon polygon) {
            Stack<Point> sPoints = new Stack<>();
            for (int i = 0; i < points2D.size(); i++){
                for (int j = 0; j < points2D.get(i).size(); j++){
                    if (getPixel(i, j) == Color.GREEN) {
                        sPoints.push(points2D.get(i).get(j));
                    }
                }
            }

            while (!sPoints.empty()){
                Point point = sPoints.pop();
                setPixel(point.getX(), point.getY(), Color.BLUE);
                //setPixel(point.getX() + 1, point.getY(), Color.GREEN);
            }

        }
    }

    class FillerLineByLineWithSeed {

        int deep = 0;
        int[] stx = new int[1000];
        int[] sty = new int[1000];
        int xmax = points2D.size(), xmin = 0, ymax = points2D.get(0).size(), ymin = 0;

        /*void pop(){
            x=stx[--deep];y=sty[deep];
        }*/
        void push(int a, int b) {
            stx[deep] = a;
            sty[deep++] = b;
        }

        void flstr(int oldColor, int newColor, int x, int y) {
            int tempx, xleft, xright;
            int xenter, flag, i;
            push(x, y);
            while (deep > 0) {
                Log.d("TAG", "flstr: " + x + " " + y);
                x = stx[--deep];
                y = sty[deep]; // pop
                if (getPixel(x, y) == oldColor) {
                    setPixel(x, y, newColor);
                    tempx = x; //сохранение текущей коорд. x
                    x++;     //перемещение вправо
                    while (getPixel(x, y) == oldColor && x <= xmax) setPixel(x++, y, newColor);
                    xright = x - 1;
                    x = tempx;
                    x--; //перемещение влево
                    while (getPixel(x, y) == oldColor && x >= xmin) setPixel(x--, y, newColor);
                    xleft = x + 1;
                    x = tempx;
                    for (i = 0; i < 2; i++) {
                        // при i=0 проверяем нижнюю, а при i=1 - верхнюю строку
                        if (y <= ymax && y >= ymin) {
                            x = xleft;
                            y += 1 - i * 2;
                            while (x <= xright) {
                                flag = 0;
                                while (getPixel(x, y) == oldColor && x <= xright) {
                                    if (flag == 0) flag = 1;
                                    x++;
                                }
                                if (flag == 1) {
                                    if (x == xright && getPixel(x, y) == oldColor) {
                                        push(x, y);
                                    } else {
                                        push(x - 1, y);
                                    }
                                    flag = 0;
                                }

                                xenter = x;
                                while (getPixel(x, y) == newColor && x <= xright) x++;
                                if (x == xenter) x++;
                            }
                        }
                        y--;
                    }
                }
            }
        }

    }



}

