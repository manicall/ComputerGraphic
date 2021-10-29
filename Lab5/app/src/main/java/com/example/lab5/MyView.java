package com.example.lab5;

import static java.lang.Math.sqrt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View {

    Canvas canvas;
    int SCALE = 10;
    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        //canvas.scale(SCALE, SCALE);
        draw();

    }

    Complex i = new Complex(0, 1);
    Complex[] root = new Complex[3];

    Complex f(Complex z) {
        Complex temp;
        temp = z.mul(z);
        temp = temp.mul(z);
        temp = temp.sub(new Complex(1));

        return temp;
    }

    Complex fs(Complex z) {
        Complex temp;
        temp = z.mul(z);
        temp = temp.mul(new Complex(3));
        return temp;
    }


    void draw() {
        double xmin=-1.5, ymin=-1, xmax=1.5, ymax=1;
        double maxX = canvas.getWidth(), maxY = canvas.getHeight();
        double xinc=(xmax-xmin)/maxX, yinc=(ymax-ymin)/maxY;

        int [] colors = new int[6];
        colors[0]=Color.rgb(127,0,0);colors[1]=Color.rgb(255,0,0);
        colors[2]=Color.rgb(0,127,0);colors[3]=Color.rgb(0,255,0);
        colors[4]=Color.rgb(0,0,127);colors[5]=Color.rgb(0,0,255);

        double re, im;
        int ncolors = 0, j, n;
        Complex z;
        ncolors = 3;
        root[0] = new Complex(1.);
        root[1] = new Complex(-0.5, sqrt(3.) / 2);
        root[2] = new Complex(-0.5, -sqrt(3.) / 2);


        for (re = xmin; re < xmax; re += xinc)
            for (im = ymin; im < ymax; im += yinc) {
                z = new Complex(re, im);
                n = 0;
                do {
                    if (fs(z).abs() < 0.0001) n = -1;
                    else {
                        z = z.sub(f(z).div(fs(z)));
                        n++;
                    }
                } while (n >= 0 && n < 100 && f(z).abs() >= 0.01);
                if (n < 0) continue;
                for (j = 0; j < ncolors; j++) {
                    if ((z.sub(root[j])).abs() < 0.01)
                        putpoint(re, im, colors[2 * j + n % 2]); //pput=1;
                }
            }
    }
    void putpoint (double x, double y, int color)
    {
        double xmin=-1.5, ymin=-1, xmax=1.5, ymax=1;
        double maxX = canvas.getWidth(), maxY = canvas.getHeight();

        Paint paint = new Paint();
        paint.setColor(color);

        if(x<xmax&&x>xmin&&y<ymax&&y>ymin)
            canvas.drawPoint(
                    (float) ((x-xmin)*maxX/(xmax-xmin)),
                    (float) ((ymax-y)*maxY/(ymax-ymin)),
                    paint);
    }

}
