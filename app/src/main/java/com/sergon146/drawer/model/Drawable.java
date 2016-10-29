package com.sergon146.drawer.model;

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
    int getId();
    Drawable morf(double t, Drawable d);
}