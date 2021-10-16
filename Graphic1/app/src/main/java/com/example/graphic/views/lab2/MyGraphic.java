package com.example.graphic.views.lab2;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

public class MyGraphic extends View {
    public static final int B = 2;
    public static final int L = 12;
    public static final int V = 22;
    Canvas canvas;
    Polygon polygon;
    Letter letter;
    private int numOfVertexes;
    private int filling;

    public MyGraphic(Context context, int numOfVertexes, int filling) {
        super(context);
        this.numOfVertexes = numOfVertexes;
        this.filling = filling;
    }

    static float rnd(float min, float max) {
        max -= min;
        return (float) (Math.random() * ++max) + min;
    }

    public void setFilling(int filling) {
        this.filling = filling;
    }

    // отрисовывающий метод
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        polygon = new Polygon(numOfVertexes, canvas);
        letter = new Letter(polygon.vertexes, canvas);
        // закрашиваем холст белым цветом
        GraphicCreator.setWhiteBackground(canvas);
        createPolygon(canvas);
        letter.drawLetter(filling);

    }

    public void setNumOfVertexes(int numOfVertexes) {
        this.numOfVertexes = numOfVertexes;
    }

    public void createPolygon(Canvas canvas) {
        //соединение вершин кроме первой и последней

        ArrayList<MyPoint> vertexes = polygon.vertexes;
        int j = vertexes.size() - 1;
        for (int i = 0; i < vertexes.size(); i++) {
            canvas.drawLine(vertexes.get(i).getX(), vertexes.get(i).getY(),
                    vertexes.get(j).getX(), vertexes.get(j).getY(),
                    MyPaint.getBlackPaint());
            j = i;
        }
    }

    class Polygon {
        private Canvas canvas;
        private float left;
        private float right;
        private float top;
        private float bottom;

        private ArrayList<MyPoint> vertexes = new ArrayList<>();

        Polygon(int numOfVertexes, Canvas canvas) {
            for (int i = 0; i < numOfVertexes; i++) {
                vertexes.add(new MyPoint(rnd(0, canvas.getWidth()), rnd(0, canvas.getHeight())));
            }
            this.canvas = canvas;
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
    }

    class Letter {
        float WIDTH_INTERVAL = 70;
        float HEIGHT_INTERVAL = 100;

        private Canvas canvas;
        private ArrayList<MyPoint> vertexes;

        private LetterB letterB = new LetterB();
        private LetterL letterL = new LetterL();
        private LetterV letterV = new LetterV();

        Letter(ArrayList<MyPoint> vertexes, Canvas canvas) {
            this.vertexes = vertexes;
            this.canvas = canvas;
        }

        public void drawLetter(int filling) {
            switch (filling) {
                case B:
                    letterB.fill();
                    break;
                case L:
                    letterL.fill();
                    break;
                case V:
                    letterV.fill();
                    break;
            }
        }

        private boolean isPointInPolygon(ArrayList<MyPoint> p, MyPoint point) {
            /*
             * В основе алгоритма лежит идея подсчёта количества пересечений луча,
             * исходящего из данной точки в направлении горизонтальной оси,
             * со сторонами многоугольника. Если оно чётное, точка не принадлежит многоугольнику.
             * */
            boolean result = false;
            int j = p.size() - 1;
            for (int i = 0; i < p.size(); i++) {
                if (
                        /*проверяет попадание point.Y между значениями p[i].Y
                         и p[j].Y, контролирует направление прохода вершины и
                         обеспечивает ненулевой знаменатель основной формулы.*/
                        (p.get(i).getY() < point.getY() && p.get(j).getY() >= point.getY() ||
                                p.get(j).getY() < point.getY() && p.get(i).getY() >= point.getY()) &&
                        /*проверяет нахождение стороны p[i]p[j] слева от точки point.*/
                        (p.get(i).getX() + (point.getY() - p.get(i).getY()) /
                                (p.get(j).getY() - p.get(i).getY()) *
                                (p.get(j).getX() - p.get(i).getX()) < point.getX()))
                    /*
                    формирует отрицательный ответ
                    при чётном количестве сторон слева и
                    положительный — при нечётном.*/
                    result = !result;
                j = i;
            }
            return result;
        }

        class LetterB {
            float VERTICAL_SIZE = 50;
            float ARC_RADIUS_Y = 10;
            float ARC_RADIUS_X = 22;
            float CIRCLE_RADIUS = 1f;

            public void fill() {
                for (float i = polygon.getLeft(); i < polygon.getRight(); i += WIDTH_INTERVAL) {
                    for (float j = polygon.getTop(); j < polygon.getBottom(); j += HEIGHT_INTERVAL) {
                        draw(vertexes, i, j);
                    }
                }
            }

            private void draw(ArrayList<MyPoint> vertexes, float i, float j) {
                // вертикальный отрезок
                drawVertical(i, j);
                // верхняя дуга
                drawArc(i, j, 0);
                // нижняя дуга
                drawArc(i, j, VERTICAL_SIZE / 2);
            }

            private void drawVertical(float i, float j) {
                for (float y = j; y < j + VERTICAL_SIZE; y++) {
                    float x = i;

                    if (isPointInPolygon(vertexes, new MyPoint(x, y))) {
                        canvas.drawCircle(x, y, CIRCLE_RADIUS, MyPaint.getRedPaint());
                    }
                }
            }

            private void drawArc(float i, float j, float offset) {
                for (float y = -ARC_RADIUS_Y; y < ARC_RADIUS_Y; y++) {
                    float x = -(float) (Math.pow(y, 2) / (ARC_RADIUS_Y / 2));
                    float modifiedX = x + i + ARC_RADIUS_X;
                    float modifiedY = y + j + ARC_RADIUS_Y + offset;

                    if (isPointInPolygon(vertexes, new MyPoint(modifiedX, modifiedY))) {
                        canvas.drawCircle(modifiedX, modifiedY, CIRCLE_RADIUS, MyPaint.getRedPaint());
                    }
                }
            }
        }

        class LetterL {
            float VERTICAL_SIZE = 50;
            float HORIZONTAL_SIZE = 30;
            float CIRCLE_RADIUS = 1f;

            public void fill() {
                for (float i = polygon.getLeft(); i < polygon.getRight(); i += WIDTH_INTERVAL) {
                    for (float j = polygon.getTop(); j < polygon.getBottom(); j += HEIGHT_INTERVAL) {
                        draw(vertexes, i, j);
                    }
                }
            }

            private void draw(ArrayList<MyPoint> vertexes, float i, float j) {
                // вертикальный отрезок
                for (float y = j; y < j + VERTICAL_SIZE; y++) {
                    float x = i;
                    if (isPointInPolygon(vertexes, new MyPoint(x, y))) {
                        canvas.drawCircle(x, y, CIRCLE_RADIUS, MyPaint.getRedPaint());
                    }
                }
                // горизонтальный отрезок
                for (float x = i; x < i + HORIZONTAL_SIZE; x++) {
                    float y = j + VERTICAL_SIZE;
                    if (isPointInPolygon(vertexes, new MyPoint(x, y))) {
                        canvas.drawCircle(x, y, CIRCLE_RADIUS, MyPaint.getRedPaint());
                    }
                }
            }
        }

        class LetterV {
            float VERTICAL_SIZE = 50;
            float CIRCLE_RADIUS = 1f;

            private void fill() {
                for (float i = polygon.getLeft(); i < polygon.getRight(); i += WIDTH_INTERVAL) {
                    for (float j = polygon.getTop(); j < polygon.getBottom(); j += HEIGHT_INTERVAL) {
                        draw(vertexes, i, j);
                    }
                }
            }

            public void draw(ArrayList<MyPoint> vertexes, float i, float j) {
                i *= 2; // компенсация последующего деления
                // отрезок: \
                for (float y = j; y < j + VERTICAL_SIZE; y++) {
                    float x = (y - j + i) / 2;
                    if (isPointInPolygon(vertexes, new MyPoint(x, y))) {
                        canvas.drawCircle(x, y, CIRCLE_RADIUS, MyPaint.getRedPaint());
                    }
                }
                // отрезок: /
                for (float y = j + VERTICAL_SIZE; y > j; y--) {
                    float x = (-y + j + i) / 2 + VERTICAL_SIZE;
                    if (isPointInPolygon(vertexes, new MyPoint(x, y))) {
                        canvas.drawCircle(x, y, CIRCLE_RADIUS, MyPaint.getRedPaint());
                    }
                }
            }
        }
    }
}

