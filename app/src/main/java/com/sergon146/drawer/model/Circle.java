package com.sergon146.drawer.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.sergon146.drawer.util.Const;

import java.io.Serializable;

/**
 * Created by sergon on 18.10.16.
 */

public class Circle implements Drawable, Serializable {
    private double r;
    private Point c;
    private boolean choose;

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
        return getR() + rad >= (Math.sqrt(Math.pow(touch.getX() - getC().getX(), 2) + Math.pow(touch.getY() - getC().getY(), 2)));
    }

    @Override
    public void shift(Point delta) {
        c.shift(delta.getX(), delta.getY());
    }

    public boolean isChoose() {
        return choose;
    }

    @Override
    public void globalScale(boolean zoom) {
        double scale = Const.scale;

        if (zoom) {
            c.scale(scale);
            r *= (1 + scale);
        } else {
            c.scale(-scale);
            r *= (1 - scale);
        }

    }

    @Override
    public void globalRotate(boolean rotate) {
        double angle = Const.angle;

        if (rotate) {
            c.rotate(angle);
        } else {
            c.rotate(-angle);
        }

    }

    @Override
    public void localSacle(boolean zoom) {
        double scale = Const.scale;

        if (zoom) {
            r *= (1 + scale);
        } else {
            r *= (1 - scale);
        }
    }

    @Override
    public void localRotate(boolean rotate) {
    //do nothing
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }


}