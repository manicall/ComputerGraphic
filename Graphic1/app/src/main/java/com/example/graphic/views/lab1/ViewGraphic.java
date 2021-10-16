package com.example.graphic.views.lab1;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ViewGraphic extends View {
    private static float scale = 50;  // масштаб
    AbstractGraphic graphic;

    public void setGraphic(AbstractGraphic graphic) {
        this.graphic = graphic;
    }

    private ScaleGestureDetector mScaleDetector;

    public ViewGraphic(Context context, AbstractGraphic graphic) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        setGraphic(graphic);
    }
    // отрисовывающий метод
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // закрашиваем холст белым цветом
        GraphicCreator.setWhiteBackground(canvas);
        GraphicCreator.createAxes(canvas);
        GraphicCreator.createDashes(canvas, scale);
        graphic.createGraphic(canvas, scale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        mScaleDetector.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            Log.d("MyApp", Float.toString(detector.getScaleFactor()));
            // Don't let the object get too small or too large
            scale = Math.max(25f, Math.min(scale, 250.0f));
            invalidate();
            return true;
        }
    }
}
