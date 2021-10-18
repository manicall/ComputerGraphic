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

    void fillWithStoringInStack(int borderColor, int color){
        FillerWithStoringBorderPointsInTheStack filler =
                new FillerWithStoringBorderPointsInTheStack();
        filler.fillStack(borderColor, color);
    }

    int getPixel(int x, int y) {
        return points2D.get(x).get(y).getColor();
    }

    void setPixel(int x, int y, int color) {
        points2D.get(x).get(y).setColor(color);
    }

    class FillerWithStoringBorderPointsInTheStack {
        void fillStack(int borderColor, int color) {
            Stack<Point> sPoints = new Stack<>();
            for (int i = 0; i < points2D.size(); i++){
                for (int j = 0; j < points2D.get(i).size(); j++){
                    if (getPixel(i, j) == borderColor) {
                        sPoints.push(points2D.get(i).get(j));
                    }
                }
            }

            while (true){
                Renderer renderer = new Renderer(points2D);

                Point point0;
                Point point1;
                if (sPoints.empty()) return;
                point0 = sPoints.pop();
                while (true)
                {
                    if (sPoints.empty()) return;
                    point1 = sPoints.pop();
                    if (point1.getX() == point0.getX()) {
                        if(point1.getY() == point0.getY() - 1) {
                            point0 = point1;
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    point0 = point1;
                }

                Point newPoint0 = point0.clone();
                Point newPoint1 = point1.clone();
                newPoint0.decrementY();
                newPoint1.incrementY();

                renderer.bresenham(newPoint0, newPoint1, color);
            }

        }
    }

    class FillerLineByLineWithSeed {
        int deep = 0;
        int[] stx = new int[1000];
        int[] sty = new int[1000];
        int xmax = points2D.size(), xmin = 0, ymax = points2D.get(0).size(), ymin = 0;

        void push(int a, int b) {
            stx[deep] = a;
            sty[deep++] = b;
        }

        void flstr(int oldColor, int newColor, int x, int y) {
            int xCurrent, xLeft, xRight;
            int xEnter, flag, i;
            push(x, y);
            while (deep > 0) {
                Log.d("TAG", "flstr: " + x + " " + y);
                x = stx[--deep];
                y = sty[deep]; // pop
                if (getPixel(x, y) == oldColor) {
                    setPixel(x, y, newColor);
                    xCurrent = x; //сохранение текущей коорд. x
                    x++;     //перемещение вправо
                    while (getPixel(x, y) == oldColor && x <= xmax) setPixel(x++, y, newColor);
                    xRight = x - 1;
                    x = xCurrent;
                    x--; //перемещение влево
                    while (getPixel(x, y) == oldColor && x >= xmin) setPixel(x--, y, newColor);
                    xLeft = x + 1;
                    x = xCurrent;
                    for (i = 0; i < 2; i++) {
                        // при i=0 проверяем нижнюю, а при i=1 - верхнюю строку
                        if (y <= ymax && y >= ymin) {
                            x = xLeft;
                            y += 1 - i * 2;
                            while (x <= xRight) {
                                flag = 0;
                                while (getPixel(x, y) == oldColor && x <= xRight) {
                                    if (flag == 0) flag = 1;
                                    x++;
                                }
                                if (flag == 1) {
                                    if (x == xRight && getPixel(x, y) == oldColor) {
                                        push(x, y);
                                    } else {
                                        push(x - 1, y);
                                    }
                                    flag = 0;
                                }

                                xEnter = x;
                                while (getPixel(x, y) == newColor && x <= xRight) x++;
                                if (x == xEnter) x++;
                            }
                        }
                        y--;
                    }
                }
            }
        }

    }

}

