package com.example.graphic2;

import android.graphics.Color;

import java.util.ArrayList;

public class Filler {

    ArrayList<ArrayList<Point>> points2D;
    public Filler(ArrayList<ArrayList<Point>> points2D){
        this.points2D = points2D;

    }

    void fill(int oldColor, int newColor, int x, int y)
    {
        if (points2D.get(x).get(y).getColor() != oldColor) return;
        points2D.get(x).get(y).setColor(newColor);
        fill(oldColor, newColor, x - 1, y);
        fill(oldColor, newColor, x + 1, y);
        fill(oldColor, newColor, x, y - 1);
        fill(oldColor, newColor, x, y + 1);
    }
//  class FillerWithStoringBorderPointsInTheStack{
    class FillerLineByLineWithSeed{

        /*void pop(){
            x=stx[--deep];y=sty[deep];
        }*/
        void push(int a, int b){
            stx[deep]=a;sty[deep++]=b;
        }

        int deep=0;
        int [] stx = new int[1000];
        int [] sty = new int[1000];
        int xmax=points2D.size()-2, xmin=1, ymax=points2D.get(0).size()-2, ymin=1;

        void flstr(int color, int x, int y)
        {
            int tempx, xleft, xright;
            int xenter, flag, i;
            push(x,y);
            while(deep>0)
            {
                x=stx[--deep];y=sty[deep];
                if(getPixel(x,y)!=color)
                {
                    setPixel(x,y,color) ;
                    tempx=x; //сохранение текущей коорд. x
                    x++;     //перемещение вправо
                    while(getPixel(x,y)!=color && x<=xmax)
                        setPixel(x++, y, color);
                    xright=x-1;
                    x=tempx; x--; //перемещение влево
                    while(getPixel(x,y)!=color && x>=xmin)
                        setPixel(x--, y, color);
                    xleft=x+1;
                    x=tempx;
                    for(i=0;i<2;i++)
                    {
                        // при i=0 проверяем нижнюю, а при i=1 - верхнюю строку
                        if(y<ymax && y>ymin)
                        {
                            x=xleft; y+=1-i*2;
                            while(x<=xright)
                            {
                                flag=0;
                                while(getPixel(x,y)!=color && x<xright)
                                {
                                    if(flag==0) flag=1;
                                    x++;
                                }
                                if (flag==1)
                                {
                                    if(x==xright &&
                                            getPixel(x,y)!=color)
                                    {
                                        push(x,y);
                                    }
                                    else
                                    {push(x-1,y);}
                                    flag=0;
                                }
                                xenter=x;
                                while(getPixel(x,y)==color && x<xright)
                                    x++;
                                if(x==xenter) x++;
                            }
                        }
                        y-- ;
                    }
                }
            }
        }
        int getPixel(int x, int y){
            return points2D.get(x).get(y).getColor();
        }

        void setPixel(int x, int y, int color){
            points2D.get(x).get(y).setColor(color);
        }

    }

    void fillLineByLine(int color, int x, int y){
        FillerLineByLineWithSeed filler = new FillerLineByLineWithSeed();
        filler.flstr(color, x, y);
    }




}

