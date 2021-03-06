package com.example.graphic2;

import java.util.ArrayList;

public class Renderer {
    private ArrayList<ArrayList<Point>> points2D;

    public Renderer(ArrayList<ArrayList<Point>> points2D) {
        this.points2D = points2D;
    }

    //Функция вывода отрезка по алгоритму Брезенхема
    public void bresenham(Point firstPoint, Point secondPoint, int color) {
        Point point = firstPoint.clone();
        Point delta = new Point(
                Math.abs(secondPoint.diffX(firstPoint)),
                Math.abs(secondPoint.diffY(firstPoint)));

        int incrementX = getIncrement(secondPoint.diffX(firstPoint));
        int incrementY = getIncrement(secondPoint.diffY(firstPoint));

        //Обмен значений delta_x delta_y в зависимости от угла
        boolean isDeltaYBiggerThanDeltaX = false;
        if (delta.getY() > delta.getX()) {
            delta.swap();
            isDeltaYBiggerThanDeltaX = true;
        }

        //Инициализация Е с поправкой на половину пиксела
        int esh = 2 * delta.getY() - delta.getX();
        for (int i = 0; i <= delta.getX(); i++) {
            setPixel(point, color);
            if (esh >= 0) {
                if (isDeltaYBiggerThanDeltaX) point.increaseX(incrementX);
                else point.increaseY(incrementY);
                esh -= 2 * delta.getX();
            }
            if (!isDeltaYBiggerThanDeltaX) point.increaseX(incrementX);
            else point.increaseY(incrementY);
            esh += 2 * delta.getY();
        }
    }

    private int getIncrement(int different){
        if (different > 0) return  1;
        else if (different < 0) return -1;
        else return 0;
    }

    public void simpleDDA(Point firstPoint, Point secondPoint, int color) {
        String X = "x";
        String Y = "y";

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

            accumulatorX = changePointCoordinate(accumulatorX, maxAccumulator, point, X);
            accumulatorY = changePointCoordinate(accumulatorY, maxAccumulator, point, Y);
        }
    }

    private int changePointCoordinate(int accumulator, int maxAccumulator, Point point, String coordinate){
        if (accumulator >= maxAccumulator) {
            accumulator -= maxAccumulator;
            point.increment(coordinate);
        } else if (accumulator < 0) {
            accumulator += maxAccumulator;
            point.decrement(coordinate);
        }
        return accumulator;
    }

    private void setPixel(Point point, int color) {
        points2D.get(point.getX()).get(point.getY()).setColor(color);
    }
}
