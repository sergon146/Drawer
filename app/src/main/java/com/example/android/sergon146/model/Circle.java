package com.example.android.sergon146.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by sergon on 18.10.16.
 */

public class Circle implements Drawable {
    private double r;
    private Point c;

    public Circle(Point c, double r) {
        c = this.c;
        r = this.r;
    }

    public Circle(int maxX, int maxY) {
        r = 10 + (Math.random() * 60);
        c = new Point(10 + r + (Math.random() * (maxX - r - 10)), 190 + r + (Math.random() * (maxY - 190 - r - 10)));

    }


    public Point getC() {
        return c;
    }

    public void setC(Point c) {
        this.c = c;
    }

    public float getR() {
        return (float) r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        canvas.drawCircle(getC().getX(), getC().getY(), getR(), p);
    }

    @Override
    public boolean isNearTouch(Point touch, float rad) {
        if (getR() + rad >= (Math.sqrt(Math.pow(touch.getX() - getC().getX(), 2) + Math.pow(touch.getY() - getC().getY(), 2))))
            return true;
        else
            return false;
    }


}