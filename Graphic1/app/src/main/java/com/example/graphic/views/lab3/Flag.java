package com.example.graphic.views.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Flag extends View {



    public Flag(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        FlagParameters flagParameters = new FlagParameters(120, 60);
        createFlag(canvas, flagParameters);

        flagParameters = new FlagParameters(120, 60, 2);
        flagParameters.setPosition(new MyPoint(canvas.getWidth() - flagParameters.getWidth(), canvas.getHeight() - flagParameters.getHeight()));
        createFlag(canvas, flagParameters);
    }

    private void createFlag(Canvas canvas, FlagParameters flagParameters){
        int COUNT_OF_SMALL_RECT = 3;
        float heightOfSmallRect = flagParameters.height / COUNT_OF_SMALL_RECT;

        Paint strokePaint = new Paint();
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);

        for(int i = MyPaint.WHITE; i <= MyPaint.RED; i++){
            float topOfSmallRect = (i - 1) * heightOfSmallRect;
            float bottomOfSmallRect = i * heightOfSmallRect;

            canvas.drawRect(
                    flagParameters.getPosition().getX() ,
                    flagParameters.getPosition().getY() + topOfSmallRect,
                    flagParameters.getPosition().getX() + flagParameters.getWidth(),
                    flagParameters.getPosition().getY() + bottomOfSmallRect,
                    MyPaint.getPaint(i));
        }

        // границы флага
        canvas.drawRect(
                flagParameters.getPosition().getX(),
                flagParameters.getPosition().getY(),
                flagParameters.getPosition().getX() + flagParameters.getWidth(),
                flagParameters.getPosition().getY() + flagParameters.getHeight(),
                strokePaint);

    }
    class FlagParameters{
        private float  width;
        private float  height;
        private MyPoint position;

        FlagParameters(float width, float height, float scale, MyPoint position){
            this(width, height, scale);
            this.position = position;
        }
        FlagParameters(float width, float height, float scale){
            this(width * scale, height * scale);
        }
        FlagParameters(float width, float height){
            this.width = width;
            this.height = height;
            this.position = new MyPoint(0,0);
        }


        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }

        public MyPoint getPosition() {
            return position;
        }

        public void setPosition(MyPoint position) {
            this.position = position;
        }
    }

}
