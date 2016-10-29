package com.sergon146.drawer.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.sergon146.drawer.util.Const;

import java.io.Serializable;

/**
 * Created by Sergon146 on 017 17.10.16.
 */

public class Line implements Drawable, Serializable {
    private Point p1;
    private Point p2;
    private boolean choose;
    private int id;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        choose = false;
    }

    public Line(Line line){
        p1 = new Point(line.p1);
        p2 = new Point(line.p2);
        id = line.id;
    }

    public Line(double maxX, double maxY, int id) {
        p1 = new Point(Math.random() * maxX, (Math.random() * (maxY)));
        p2 = new Point(Math.random() * maxX, (Math.random() * (maxY)));
        this.id = id;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isChoose() {
        return choose;
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

    public void scaleAll(double scale) {
        p1.scale(scale);
        p2.scale(scale);
    }

    public void rotateAll(double angle) {
        p1.rotate(angle);
        p2.rotate(angle);
    }

    @Override
    public void localSacle(boolean zoom) {
        double posX = (p1.getX()+p2.getX())/2;
        double posY = (p1.getY()+p2.getY())/2;
        p1.shift(-posX, -posY);
        p2.shift(-posX, -posY);
        if (zoom)
            scaleAll(Const.scale);
        else
            scaleAll(-Const.scale);
        p1.shift(posX, posY);
        p2.shift(posX, posY);

    }

    @Override
    public void localRotate(boolean rotate) {
        double posX = (p1.getX()+p2.getX())/2;
        double posY = (p1.getY()+p2.getY())/2;
        p1.shift(-posX, -posY);
        p2.shift(-posX, -posY);
        if (rotate)
            rotateAll(Const.angle);
        else
            rotateAll(-Const.angle);
        p1.shift(posX, posY);
        p2.shift(posX, posY);
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
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
    public void shift(Point delta) {
        p1.shift(delta.getX(), delta.getY());
        p2.shift(delta.getX(), delta.getY());
    }

    @Override
    public Drawable morf(double t, Drawable d) {
        Line line = (Line) d;
        Line newLine = new Line (p1.morf(t, line.p1), p2.morf(t, line.p2));
        newLine.id = line.id;
        return newLine;
    }


}