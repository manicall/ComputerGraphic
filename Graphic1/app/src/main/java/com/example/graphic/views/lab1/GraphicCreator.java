package com.example.graphic.views.lab1;

import android.graphics.Canvas;
import android.graphics.Paint;

public class GraphicCreator {
    final static float LOW_OFFSET = 10;
    final static float MEDIUM_OFFSET = 25;
    final static float LARGE_OFFSET = 250;
    final static float FIXED_OFFSET = 7.5f;
    final static float MEDIUM_TEXT_SIZE = 50;


    public static void setWhiteBackground(Canvas canvas) {
        Paint mPaint = MyPaint.getWhitePaint();
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(mPaint);
    }

    public static void createAxes(Canvas canvas) {
        createAxeX(canvas);
        createAxeY(canvas);
    }


    private static void createAxeX(Canvas canvas) {
        MyPoint center = getCenter(canvas);
        Paint mPaint = MyPaint.getBlackPaint();
        mPaint.setTextSize(MEDIUM_TEXT_SIZE);
        canvas.drawText("X", canvas.getWidth() - MEDIUM_OFFSET, center.getY() - MEDIUM_OFFSET, mPaint);
        for (float x = getLeft(); x < canvas.getWidth(); x++) {
            canvas.drawPoint(x, center.getY(), MyPaint.getBlackPaint());
        }
    }

    private static void createAxeY(Canvas canvas) {
        MyPoint center = getCenter(canvas);

        Paint mPaint = MyPaint.getBlackPaint();
        mPaint.setTextSize(MEDIUM_TEXT_SIZE);

        canvas.drawText("Y", center.getX() + LOW_OFFSET, getTop(), mPaint);
        for (float y = getTop(); y < getBottom(canvas); y++) {
            canvas.drawPoint(center.getX(), y, MyPaint.getBlackPaint());
        }
    }

    public static void createDashes(Canvas canvas, float scale) {
        createRightDashes(canvas, scale);
        createUpDashes(canvas, scale);
        createLeftDashes(canvas, scale);
        createDownDashes(canvas, scale);
    }

    private static void createLeftDashes(Canvas canvas, float scale) {
        MyPoint center = getCenter(canvas);
        for (float x = center.getX(); x > getLeft(); x -= scale) {
            canvas.drawLine(x, center.getY() - LARGE_OFFSET / scale - FIXED_OFFSET, x,
                    center.getY() + LARGE_OFFSET / scale + FIXED_OFFSET, MyPaint.getBlackPaint());
        }
    }

    private static void createUpDashes(Canvas canvas, float scale) {
        MyPoint center = getCenter(canvas);
        for (float y = center.getY(); y > getTop(); y -= scale) {
            canvas.drawLine(center.getX() - LARGE_OFFSET / scale - FIXED_OFFSET, y,
                    center.getX() + LARGE_OFFSET / scale + FIXED_OFFSET, y, MyPaint.getBlackPaint());
        }
    }

    private static void createRightDashes(Canvas canvas, float scale) {
        MyPoint center = getCenter(canvas);
        for (float x = center.getX(); x < getRight(canvas); x += scale) {
            canvas.drawLine(x, center.getY() - LARGE_OFFSET / scale - FIXED_OFFSET, x,
                    center.getY() + LARGE_OFFSET / scale + FIXED_OFFSET, MyPaint.getBlackPaint());
        }
    }

    private static void createDownDashes(Canvas canvas, float scale) {
        MyPoint center = getCenter(canvas);
        for (float y = center.getY(); y < getBottom(canvas); y += scale) {
            canvas.drawLine(center.getX() - LARGE_OFFSET / scale - FIXED_OFFSET, y,
                    center.getX() + LARGE_OFFSET / scale + FIXED_OFFSET, y, MyPaint.getBlackPaint());
        }
    }

    public static MyPoint getCenter(Canvas canvas) {
        return new MyPoint(canvas.getWidth() / 2, canvas.getHeight() / 2);
    }

    public static float getTop() {
        return 50;
    }

    public static float getBottom(Canvas canvas) {
        return canvas.getHeight();
    }

    public static float getLeft() {
        return 0;
    }

    public static float getRight(Canvas canvas) {
        return canvas.getWidth();
    }
}
