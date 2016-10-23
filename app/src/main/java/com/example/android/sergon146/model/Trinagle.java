package com.example.android.sergon146.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by sergon on 18.10.16.
 */

public class Trinagle implements Drawable {
    private Point p1;
    private Point p2;
    private Point p3;

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

    public Point getP3() {
        return p3;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
    }

    public Trinagle(Point p1, Point p2, Point p3) {
        p1 = this.p1;
        p2 = this.p2;
        p3 = this.p3;
    }

    public Trinagle(int maxX, int maxY){
        p1 = new Point(Math.random() * maxX, 190 +  (Math.random() * (maxY - 190)));
        p2 = new Point(Math.random() * maxX, 190 +  (Math.random() * (maxY - 190)));
        p3 = new Point(Math.random() * maxX, 190 +  (Math.random() * (maxY - 190)));

    }

    @Override
    public void draw(Canvas canvas, Paint p) {

        canvas.drawLine(getP1().getX(), getP1().getY(), getP2().getX(), getP2().getY(), p);
        canvas.drawLine(getP2().getX(), getP2().getY(), getP3().getX(), getP3().getY(), p);
        canvas.drawLine(getP3().getX(), getP3().getY(), getP1().getX(), getP1().getY(), p);
    }

    private boolean isTouchNearLine(Point touch, float rad, Point p1, Point p2){
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

    @Override
    public boolean isNearTouch(Point touch, float rad) {
        if (isTouchNearLine(touch, rad, getP1(), getP2()))
            return true;
        else if (isTouchNearLine(touch, rad, getP2(), getP3()))
            return true;
        else if (isTouchNearLine(touch, rad, getP3(), getP1()))
            return true;
        else return false;
    }
}