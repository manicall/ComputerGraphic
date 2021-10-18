package com.example.graphic2;

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

    void fillLineByLine(int oldColor, int newColor, Point point) {
        FillerLineByLineWithSeed filler = new FillerLineByLineWithSeed();
        filler.flstr(oldColor, newColor, point);
    }

    void fillWithStoringInStack(int borderColor, int color){
        FillerWithStoringBorderPointsInTheStack filler =
                new FillerWithStoringBorderPointsInTheStack();
        filler.fillStack(borderColor, color);
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
        Stack<Point> sPoints = new Stack<>();

        Point min = new Point(0, 0);
        Point max = new Point(points2D.size(), points2D.get(0).size());


        void flstr(int oldColor, int newColor, Point point) {

            int xLeft, xRight;
            int xEnter;
            boolean flag;
            sPoints.push(point);
            while (!sPoints.empty()) {
                point = sPoints.pop();

                if (getPixel(point) == oldColor) {

                    int [] horizontalBorders = paintHorizontalLine(oldColor, newColor, point);
                    xLeft = horizontalBorders[0];
                    xRight = horizontalBorders[1];

                    for (int i = 0; i < 2; i++) {
                        // при i=0 проверяем нижнюю, а при i=1 - верхнюю строку
                        if (point.getY() <= max.getY() && point.getY() >= min.getY()) {
                            point.setX(xLeft);
                            point.increaseY(1 - i * 2);

                            while (point.getX() <= xRight) {
                                flag = false;
                                while (getPixel(point) == oldColor && point.getX() <= xRight) {
                                    if (!flag) flag = true;
                                    point.incrementX();
                                }
                                if (flag) {
                                    if (point.getX() == xRight && getPixel(point) == oldColor) {
                                        sPoints.push(point);
                                    } else {
                                        sPoints.push(new Point(point.getX() - 1, point.getY()));
                                    }
                                }
                                xEnter = point.getX();
                                while (getPixel(point) == newColor && point.getX() <= xRight)
                                    point.incrementX();
                                if (point.getX() == xEnter) point.incrementX();
                            }
                        }
                        point.decrementY();
                    }
                }
            }
        }



        int [] paintHorizontalLine(int oldColor, int newColor, Point point){
            setPixel(point, newColor);
            //сохранение текущей коорд. x
            int xCurrent = point.getX();

            paintRight(oldColor, newColor, point);
            int xRight = point.getX() - 1;
            // возврат c перемещением влево
            point.setX(xCurrent - 1);
            paintLeft(oldColor, newColor, point);

            int xLeft = point.getX() + 1;
            point.setX(xCurrent);

            return new int []{xLeft, xRight};
        }

        void paintRight(int oldColor, int newColor, Point point){
            point.incrementX(); // перемещение вправо
            while (getPixel(point) == oldColor && point.getX() <= max.getX()) {
                setPixel(point, newColor);
                point.incrementX();
            }
        }

        void paintLeft(int oldColor, int newColor, Point point){
            while (getPixel(point) == oldColor && point.getX() >= min.getX()) {
                setPixel(point, newColor);
                point.decrementX();
            }
        }



    }

    int getPixel(int x, int y) {
        return points2D.get(x).get(y).getColor();
    }

    private int getPixel(Point point) {
        return points2D.get(point.getX()).get(point.getY()).getColor();
    }

    void setPixel(int x, int y, int color) {
        points2D.get(x).get(y).setColor(color);
    }

    void setPixel(Point point, int color) {
        points2D.get(point.getX()).get(point.getY()).setColor(color);
    }

}

