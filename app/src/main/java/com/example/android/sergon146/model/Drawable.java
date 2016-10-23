package com.example.android.sergon146.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by sergon on 18.10.16.
 */

public interface Drawable {

    void draw(Canvas canvas, Paint p);
    boolean isNearTouch(Point touch, float rad);
//    boolean isMouseNear(Point mousePoint, double sens);
//    void scaleSelf(double scale);
//    void rotateSelf(double angle);
//    void scaleGlobal(double scale);
//    void rotateGlobal(double angle);
//    void shift(Point p);
}