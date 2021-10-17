package com.example.graphic2;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

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

    void fillWithStoringInStack(int[] arx, int[] ary, int dimar){
        FillerWithStoringBorderPointsInTheStack filler =
                new FillerWithStoringBorderPointsInTheStack();
        filler.fl(arx, ary, dimar);
    }

    int getPixel(int x, int y) {
        return points2D.get(x).get(y).getColor();
    }

    void setPixel(int x, int y, int color) {
        points2D.get(x).get(y).setColor(color);
    }

    class FillerWithStoringBorderPointsInTheStack {
        int deep = 0;
        int[] stx = new int[1000];

        void push(int x) {
            stx[deep++] = x;
        }

        void horline(int y, int x0, int x1, int color) {
            int i;
            if (x0 > x1) {
                i = x1;
                x1 = x0;
                x0 = i;
            }

            for (i = x0; i <= x1; i++) {
                Log.d("TAG", "horline: " + i + " " + y);
                setPixel(i, y, color);
            }
        }

        void fl(int[] arx, int[] ary, int dimar) {



            int i0, iglob, ix0, iy0, ix1, iy1;
            int ymin;
            int ix, iy, deltax, deltay, esh, sx, sy;
            int temp, swab, i;
            //Находим наименьший ary[i]
            i0 = 0;
            ymin = ary[0];
            for (i = 0; i < dimar; i++) {
                if (ary[i] < ymin) {
                    i0 = i;
                    ymin = ary[i];
                }
            }
            //на отрезке (i, i+1) проводим прямую линию от
            //arx[i], ary[i] до  arx[i+1], ary[i+1]
            iglob = i0;
            do {
                ix0 = arx[iglob];
                iy0 = ary[iglob];
                iglob++;
                if (iglob == dimar) iglob = 0;
                ix1 = arx[iglob];
                iy1 = ary[iglob];
                //Основной цикл — вывод отрезка по алгоритму Брезенхема
                ix = ix0;
                iy = iy0;
                deltax = Math.abs(ix1 - ix0);
                deltay = Math.abs(iy1 - iy0);
                if (ix1 - ix0 >= 0) sx = 1;
                else sx = -1;
                if (iy1 - iy0 >= 0) sy = 1;
                else sy = -1;
/*                if (ix1==ix0) sx=0;
                if (iy1==iy0) sy=0;*/
                if (deltay > deltax) {
                    temp = deltax;
                    deltax = deltay;
                    deltay = temp;
                    swab = 1;
                } else swab = 0;
                esh = 2 * deltay - deltax;
                for (i = 1; i <= deltax; i++) {
                    while (esh >= 0) {
                        if (swab == 1) ix += sx;
                        else {
                            iy += sy;
                            if (sy == 1) push(ix);
                            if (sy == -1) {
                                temp = stx[--deep]; // pop
                                horline(iy, ix, temp, Color.RED);
                            }
                        }
                        esh = esh - 2 * deltax;
                    }
                    if (swab == 1) {
                        iy += sy;
                        if (sy == 1) push(ix);
                        if (sy == -1) {
                            temp = stx[--deep];
                            horline(iy, ix, temp, Color.RED);
                        }
                    } else ix += sx;
                    esh += 2 * deltay;
                }
            }
            while (i0 != iglob);
            for (int i1 = 0; i1 < dimar; i1++){
                setPixel(arx[i1], ary[i1], Color.MAGENTA);
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

