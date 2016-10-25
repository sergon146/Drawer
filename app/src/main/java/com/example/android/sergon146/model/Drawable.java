package com.example.android.sergon146.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by sergon on 18.10.16.
 */

public interface Drawable {

    void draw(Canvas canvas, Paint p);
    boolean isNearTouch(Point touch, float rad);
    void shift(Point delta);
    void setChoose(boolean choose);
    boolean isChoose();
    void globalScale(boolean zoom);
    void globalRotate(boolean rotate);
    void localSacle(boolean zoom);
    void localRotate(boolean rotate);
//    boolean isMouseNear(Point mousePoint, double sens);
//    void scaleSelf(double scale);
//    void rotateSelf(double angle);
//    void scaleGlobal(double scale);
//    void rotateGlobal(double angle);
//    void shift(Point p);
}