package com.example.graphic.views.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.ArrayList;

public class Rhombus extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path();

    public Rhombus(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        createRhombus(canvas,
                new RhombusParameters(200, 60),
                GraphicCreator.getCenter(canvas).getX(),
                GraphicCreator.getCenter(canvas).getY());
    }

    private void createRhombus(Canvas canvas, RhombusParameters rhombusParameters,  float x, float y){
        float a = rhombusParameters.getSideSize();
        int k = 2;
        if (a < 4) return; // прерывание рекурсии
        mPath.reset();
        mPath.moveTo(x, y);
        mPath.lineTo(x - rhombusParameters.getSmallDiagonal(), y);
        mPath.lineTo(x, y - rhombusParameters.getBigDiagonal());
        mPath.lineTo(x + rhombusParameters.getSmallDiagonal(), y);
        mPath.lineTo(x, y + rhombusParameters.getBigDiagonal());
        mPath.lineTo(x - rhombusParameters.getSmallDiagonal(), y);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        createRhombus(canvas,  new RhombusParameters(a / 2, 60),  x - k * a, y);
        createRhombus(canvas,  new RhombusParameters(a / 2, 60),  x, y - k * a);
        createRhombus(canvas,  new RhombusParameters(a / 2, 60),  x + k * a, y);
        createRhombus(canvas,  new RhombusParameters(a / 2, 60),  x, y + k * a);
    }

    class RhombusParameters{
        private float sideSize;
        private float angleDegreeA; // угол альфа в градусах
        private float angleDegreeB; // угол бета в градусах
        private float bigDiagonal;
        private float smallDiagonal;

        RhombusParameters(float sideSize, float angleDegreeA){
            this.sideSize = sideSize;
            this.angleDegreeA = angleDegreeA;
            this.angleDegreeB = (360 - angleDegreeA*2) / 2 ;

            bigDiagonal = (float) (sideSize * Math.sqrt(2 - 2 * Math.cos(Math.toRadians(angleDegreeB))));
            smallDiagonal = (float) (sideSize * Math.sqrt(2 - 2 * Math.cos(Math.toRadians(angleDegreeA))));
        }

        public float getBigDiagonal() {
            return bigDiagonal;
        }

        public float getSmallDiagonal() {
            return smallDiagonal;
        }

        public float getSideSize() {
            return sideSize;
        }
    }
}
