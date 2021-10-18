package com.example.graphic2;

import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

public class Filler {

    ArrayList<ArrayList<Point>> points2D;

    public Filler(ArrayList<ArrayList<Point>> points2D) {
        this.points2D = points2D;
    }

    void recFill(int oldColor, int newColor, int x, int y) {
        if (getPixel(x, y) != oldColor) return;
        setPixel(x, y, newColor);
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

    void setPixel(Point point, int color) {
        points2D.get(point.getX()).get(point.getY()).setColor(color);
    }

    class FillerWithStoringBorderPointsInTheStack {
        public void fillStack(int borderColor, int color) {
            Stack<Point> sPoints = getStack(borderColor);
            while (!sPoints.empty()){
                Renderer renderer = new Renderer(points2D);

                Point startPoint = sPoints.pop();
                Point [] points = findPoints(startPoint, sPoints);

                if (sPoints.empty()) return;

                startPoint = points[0];
                Point endPoint = points[1];

                Point cloneStartPoint = startPoint.clone();
                Point cloneEndPoint = endPoint.clone();
                // сужаем разрыв между точками, чтобы
                // заливка не попала на границу многоугольника
                cloneStartPoint.decrementY();
                cloneEndPoint.incrementY();

                renderer.bresenham(cloneStartPoint, cloneEndPoint, color);
            }

        }

        // находим две точки на одной оси x между которорыми разрыв
        // составляет больше единицы
        Point [] findPoints(Point startPoint, Stack<Point> sPoints){
            Point endPoint;
            while (true)
            {
                if (sPoints.empty()) return new Point[] { null };
                endPoint = sPoints.pop();
                // если новая точка не сместилась по оси Х
                if (endPoint.getX() == startPoint.getX()) {
                    // если разрыв  между точками не составляет больше единицы
                    if(endPoint.getY() == startPoint.getY() - 1) {
                        startPoint = endPoint;
                        continue;
                    }
                    else break;
                }
                startPoint = endPoint;
            }
            return new Point[] {startPoint, endPoint};
        }

        private Stack<Point> getStack(int borderColor){
            Stack<Point> sPoints = new Stack<>();
            for (int i = 0; i < points2D.size(); i++){
                for (int j = 0; j < points2D.get(i).size(); j++){
                    if (getPixel(i, j) == borderColor) {
                        sPoints.push(points2D.get(i).get(j));
                    }
                }
            }
            return sPoints;
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

        void flstr(int oldColor, int newColor, Point point) {

            int xCurrent, xLeft, xRight;
            int xEnter, flag, i;
            push(x, y);
            while (deep > 0) {

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

