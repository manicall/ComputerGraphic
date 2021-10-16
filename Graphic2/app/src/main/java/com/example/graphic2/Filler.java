package com.example.graphic2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Filler {
    int scale;
    int xOffset;
    int yOffset;

    Bitmap bitmap;
    Canvas canvas;

    int x, y;

    public Filler(Bitmap bitmap, Canvas canvas, int scale){
        this.bitmap = bitmap;
        this.canvas = canvas;
        xOffset = canvas.getWidth() / 2;
        yOffset = canvas.getHeight() / 2;
        this.scale = scale;
    }


    int TOP_LEFT = 0;
    int TOP_RIGHT = 1;
    int BOTTOM_LEFT = 2;
    int BOTTOM_RIGHT = 3;

    void flRec(int color, int x, int y){

        fl(color, x, y, TOP_LEFT);
        fl(color, x, y, TOP_RIGHT);
        fl(color, x, y, BOTTOM_LEFT);
        fl(color, x, y, BOTTOM_RIGHT);
    }

    void fl (int color, int x, int y, int direction)
    {
        this.x = x; this.y = y;
        AlteredPoint aPoint = new AlteredPoint(x, y);

        Paint mPaint = new Paint();
        mPaint.setColor(Color.CYAN);

        int bitmapColor = bitmap.getPixel(aPoint.x, aPoint.y);
        if (bitmap.getPixel(aPoint.x, aPoint.y) == color) return;
        canvas.drawPoint(x,y,mPaint);



        if (direction == TOP_LEFT) {
            fl(color, x - 1, y, direction);
            fl(color, x, y - 1, direction);
        }
        if (direction == TOP_RIGHT) {
            fl(color, x + 1, y, direction);
            fl(color, x, y - 1, direction);
        }
        if (direction == BOTTOM_LEFT) {
            fl(color, x - 1, y, direction);
            fl(color, x, y + 1, direction);
        }
        if (direction == BOTTOM_RIGHT) {
            fl(color, x + 1, y, direction);
            fl(color, x, y + 1, direction);
        }
    }




    class AlteredPoint{
        private int x, y;
        AlteredPoint(int x, int y){
            this.x = x * scale;
            this.y = y * scale;
        }
    }





}

