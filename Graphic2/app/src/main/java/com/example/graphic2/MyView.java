package com.example.graphic2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class MyView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    int SCALE = 10;
    int X_OFFSET;
    int Y_OFFSET;

    public MyView(Context context) {
        super(context);
        this.setDrawingCacheEnabled(true);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        X_OFFSET = canvas.getWidth() / 2;
        Y_OFFSET = canvas.getHeight() / 2;


        canvas.scale(SCALE,SCALE);


        int x0 = 80, y0 = 80;
        int x1 = 100, y1 = 80;
        int x2 = 100, y2 = 100;
        int x3 = 80, y3 = 100;
        Renderer renderer = new Renderer(canvas);
        renderer.bresenham(x0, y0, x1, y1, Color.RED);
        renderer.bresenham(x1, y1, x2, y2, Color.RED);
        renderer.bresenham(x2, y2, x3, y3, Color.RED);
        renderer.bresenham(x3, y3, x0, y0, Color.RED);

        this.buildDrawingCache();
        Bitmap drawingCache = this.getDrawingCache(true);

        canvas.drawPoint(90, 90, mPaint);

        Filler filler = new Filler(drawingCache, canvas, SCALE);
        filler.flRec(Color.RED,90,90);
    }







}
