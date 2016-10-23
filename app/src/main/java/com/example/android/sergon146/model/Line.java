package com.example.android.sergon146.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Sergon146 on 017 17.10.16.
 */

public class Line implements Drawable {
    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(double maxX, double maxY) {
        p1 = new Point(Math.random() * maxX, (Math.random() * (maxY)));
        p2 = new Point(Math.random() * maxX, (Math.random() * (maxY)));
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }


    @Override
    public void draw(Canvas canvas, Paint p) {
        canvas.drawLine(getP1().getX(), getP1().getY(), getP2().getX(), getP2().getY(), p);
    }

    public boolean isNearTouch(Point touch, float rad) {
        Point p1 = new Point(getP1());
        Point p2 = new Point(getP2());

        float a = (p2.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p2.getY() - p1.getY()) * (p2.getY() - p1.getY());
        float b = 2 * ((p2.getX() - p1.getX()) * (p1.getX() - touch.getX()) + (p2.getY() - p1.getY()) * (p1.getY() - touch.getY()));
        float c = touch.getX() * touch.getX()  + touch.getY() * touch.getY() + p1.getX() * p1.getX()
                + p1.getY() * p1.getY() - 2 * (touch.getX() * p1.getX() + touch.getY() * p1.getY()) - rad * rad;

        if ( - b < 0)
        {
            return (c < 0);
        }

        if ( - b < (2 * a))
        {
            return (4 * a * c - b * b < 0);
        }

        return (a + b + c < 0);
    }
}