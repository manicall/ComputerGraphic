package com.example.lab2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View{


    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int SCALE = 10;
        final int CENTER_X = canvas.getWidth() / 2;
        final int CENTER_Y = canvas.getHeight() / 2;
        final int RIGHT = canvas.getWidth();
        final int BOTTOM = canvas.getHeight();



        super.onDraw(canvas);
        canvas.drawLine(CENTER_X, 0, CENTER_X, BOTTOM, new Paint());
        canvas.drawLine(0, CENTER_Y, RIGHT, CENTER_Y, new Paint());



        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.scale(SCALE, SCALE);

        for (int i = BOTTOM / 2; i > 0; i--)
            canvas.drawLine(-1, -i, 1, -i, new Paint());

        Render render = new Render(canvas);
        //render.firstСurve(0.1f);
        //render.secondCurve(1);
        render.thirdCurve(5,110);
        //render.example(48,32);

    }
}
