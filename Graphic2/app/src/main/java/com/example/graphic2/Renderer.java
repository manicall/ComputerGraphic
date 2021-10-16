package com.example.graphic2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Renderer {
    private Canvas canvas;
    Renderer(Canvas canvas){
        this.canvas = canvas;
    }
    //Функция вывода отрезка по алгоритму Брезенхема
    void bresenham(int ix0, int iy0, int ix1, int iy1, int color)
    {
        Paint mPaint = new Paint();
        mPaint.setColor(color);
        int ix, iy, delta_x, delta_y, esh, sx, sy;
        int register, temp, swab, i;
        ix=ix0; iy=iy0;
        delta_x=Math.abs(ix1-ix0); delta_y=Math.abs(iy1-iy0);
        if (ix1-ix0>=0) sx=1; else sx=-1;
        if (iy1-iy0>=0) sy=1; else sy=-1;
        if (ix1==ix0) sx=0;
        if (iy1==iy0) sy=0;
        //Обмен значений delta_x delta_y в зависимости от угла
        if (delta_y > delta_x)
        {
            temp=delta_x;
            delta_x=delta_y;
            delta_y=temp;
            swab=1;
        } else swab=0;
        //Инициализация Е с поправкой на половину пиксела
        esh=2*delta_y-delta_x;
        for(i=0;i<=delta_x;i++)
        {
            canvas.drawPoint (ix,iy, mPaint);
            if(esh>=0)
            {
                if (swab==1) ix=ix+sx; else iy=iy+sy;
                esh=esh-2*delta_x;
            }
            if (swab==1)iy=iy+sy; else ix=ix+sx;
            esh=esh+2*delta_y;
        }
    }


    void simplecda(int x0, int y0, int x1, int y1, int color)
    {
        Paint mPaint = new Paint();
        mPaint.setColor(color);

        int length; // максимум приращений по x и по y
        int i;      // счетчик, изменяется от 0 до length-1
        int x,y;    // координаты точки
        int maxacc;
        int accx,accy; // изменяются от 0 до maxacc-1
        int deltax, deltay;
        length= Math.abs(x1-x0); if ((i= Math.abs(y1-y0))>length) length=i;
        maxacc = 2*length; // максимальное значение аккумулятора
        deltax=2*(x1-x0);
        deltay=2*(y1-y0);
        x=x0; y=y0; accx= accy = length;
        for (i=0; i<=length; i++)
        {
            canvas.drawPoint (x, y, mPaint);
            accx+=deltax; accy+= deltay;
            if (accx>=maxacc)
            {
                accx-=maxacc; x++;
            } else if (accx<0)
            {
                accx+=maxacc; x--;
            }
            if (accy>=maxacc)
            {
                accy-=maxacc; y++;
            } else if (accy<0)
            {
                accy+=maxacc; y--;
            }

        }
    }



}
