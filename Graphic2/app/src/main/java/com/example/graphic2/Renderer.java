package com.example.graphic2;

import java.util.ArrayList;

public class Renderer {
    private ArrayList<ArrayList<Point>> points2D;

    Renderer(ArrayList<ArrayList<Point>> points2D) {
        this.points2D = points2D;
    }

    //Функция вывода отрезка по алгоритму Брезенхема
    void bresenham(Point firstPoint, Point secondPoint, int color) {
        Point point = firstPoint.clone();
        Point delta = new Point(
                Math.abs(secondPoint.diffX(firstPoint)),
                Math.abs(secondPoint.diffY(firstPoint)));

        int incrementX = getIncrement(secondPoint.diffX(firstPoint));
        int incrementY = getIncrement(secondPoint.diffY(firstPoint));

        //Обмен значений delta_x delta_y в зависимости от угла
        boolean swab = false;
        if (delta.getY() > delta.getX()) {
            delta.swap();
            swab = true;
        }

        //Инициализация Е с поправкой на половину пиксела
        int esh = 2 * delta.getY() - delta.getX();
        for (int i = 0; i <= delta.getX(); i++) {
            setPixel(point, color);
            if (esh >= 0) {
                if (swab) point.increaseX(incrementX);
                else point.increaseY(incrementY);
                esh -= 2 * delta.getX();
            }
            if (!swab) point.increaseX(incrementX);
            else point.increaseY(incrementY);
            esh += 2 * delta.getY();
        }
    }

    int getIncrement(int different){
        if (different > 0) return  1;
        else if (different < 0) return -1;
        else return 0;
    }

    void simplecda(Point firstPoint, Point secondPoint, int color) {
        // максимум приращений по x и по y
        int length = Math.max(
                Math.abs(secondPoint.diffX(firstPoint)),
                Math.abs(secondPoint.diffY(firstPoint)));

        // изменяются от 0 до maxacc-1
        int accumulatorX = length;
        int accumulatorY = length;
        int maxAccumulator = 2 * length;

        Point delta = new Point(
                2 * (secondPoint.diffX(firstPoint)),
                2 * (secondPoint.diffY(firstPoint)));

        Point point = firstPoint.clone();
        for (int i = 0; i <= length; i++) {
            setPixel(point, color);
            accumulatorX += delta.getX();
            accumulatorY += delta.getY();

            if (accumulatorX >= maxAccumulator) {
                accumulatorX -= maxAccumulator;
                point.incrementX();
            } else if (accumulatorX < 0) {
                accumulatorX += maxAccumulator;
                point.decrementX();
            }
            if (accumulatorY >= maxAccumulator) {
                accumulatorY -= maxAccumulator;
                point.incrementY();
            } else if (accumulatorY < 0) {
                accumulatorY += maxAccumulator;
                point.decrementY();
            }
        }
    }

    int changePointCoordinate(int accumulator, int maxAccumulator, Point point, int coordinate){
        if (accumulator >= maxAccumulator) {
            accumulator -= maxAccumulator;
            point.increment(coordinate);
        } else if (accumulator < 0) {
            accumulator += maxAccumulator;
            point.decrement(coordinate);
        }
        return accumulator;
    }

    void setPixel(Point point, int color) {
        points2D.get(point.getX()).get(point.getY()).setColor(color);
    }
}
