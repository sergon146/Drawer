package com.sergon146.drawer.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.sergon146.drawer.util.Const;

import java.io.Serializable;

/**
 * Created by sergon on 18.10.16.
 */

public class Trinagle implements Drawable, Serializable {
    private Point p1;
    private Point p2;
    private Point p3;
    private boolean choose;

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

    public Trinagle(int maxX, int maxY) {
        p1 = new Point(Math.random() * maxX, 190 + (Math.random() * (maxY - 190)));
        p2 = new Point(Math.random() * maxX, 190 + (Math.random() * (maxY - 190)));
        p3 = new Point(Math.random() * maxX, 190 + (Math.random() * (maxY - 190)));

    }

    @Override
    public void draw(Canvas canvas, Paint p) {

        canvas.drawLine(getP1().getX(), getP1().getY(), getP2().getX(), getP2().getY(), p);
        canvas.drawLine(getP2().getX(), getP2().getY(), getP3().getX(), getP3().getY(), p);
        canvas.drawLine(getP3().getX(), getP3().getY(), getP1().getX(), getP1().getY(), p);
    }

    private boolean isTouchNearLine(Point touch, float rad, Point p1, Point p2) {
        float a = (p2.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p2.getY() - p1.getY()) * (p2.getY() - p1.getY());
        float b = 2 * ((p2.getX() - p1.getX()) * (p1.getX() - touch.getX()) + (p2.getY() - p1.getY()) * (p1.getY() - touch.getY()));
        float c = touch.getX() * touch.getX() + touch.getY() * touch.getY() + p1.getX() * p1.getX()
                + p1.getY() * p1.getY() - 2 * (touch.getX() * p1.getX() + touch.getY() * p1.getY()) - rad * rad;

        if (-b < 0) {
            return (c < 0);
        }

        if (-b < (2 * a)) {
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

    @Override
    public void shift(Point delta) {
        p1.shift(delta.getX(), delta.getY());
        p2.shift(delta.getX(), delta.getY());
        p3.shift(delta.getX(), delta.getY());

    }

    public boolean isChoose() {
        return choose;
    }

    public void scaleAll(double scale) {
        p1.scale(scale);
        p2.scale(scale);
        p3.scale(scale);
    }

    public void rotateAll(double angle) {
        p1.rotate(angle);
        p2.rotate(angle);
        p3.rotate(angle);
    }

    @Override
    public void globalScale(boolean zoom) {
        double scale = Const.scale;

        if (zoom)
            scaleAll(scale);
        else
            scaleAll(-scale);

    }

    @Override
    public void globalRotate(boolean rotate) {
        double angle = Const.angle;

        if (rotate)
            rotateAll(angle);
        else
            rotateAll(-angle);


    }

    @Override
    public void localSacle(boolean zoom) {
        double posX = (p1.getX()+p2.getX())/2;
        double posY = (p1.getY()+p2.getY())/2;
        p1.shift(-posX, -posY);
        p2.shift(-posX, -posY);
        p3.shift(-posX, -posY);
        if (zoom)
            scaleAll(Const.scale);
        else
            scaleAll(-Const.scale);
        p1.shift(posX, posY);
        p2.shift(posX, posY);
        p3.shift(posX, posY);

    }

    @Override
    public void localRotate(boolean rotate) {
        double posX = (p1.getX()+p2.getX()+p3.getX())/3;
        double posY = (p1.getY()+p2.getY()+p3.getY())/3;
        p1.shift(-posX, -posY);
        p2.shift(-posX, -posY);
        p3.shift(-posX, -posY);
        if (rotate)
            rotateAll(Const.angle);
        else
            rotateAll(-Const.angle);
        p1.shift(posX, posY);
        p2.shift(posX, posY);
        p3.shift(posX, posY);
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}