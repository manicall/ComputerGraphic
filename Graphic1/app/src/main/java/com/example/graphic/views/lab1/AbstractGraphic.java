package com.example.graphic.views.lab1;

import android.graphics.Canvas;

public abstract class AbstractGraphic {
    private static float step; // шаг
    private static float begin; // начало
    private static float end;   // конец

    public abstract void createGraphic(Canvas canvas, float scale);
}
