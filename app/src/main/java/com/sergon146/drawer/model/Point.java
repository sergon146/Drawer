package com.sergon146.drawer.model;


import java.io.Serializable;

/**
 * Created by Sergon146 on 017 17.10.16.
 */

public class Point implements Serializable {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public float getX() {
        return (float) x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public float getY() {
        return (float) y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void scale(double scale) {
        x *= (1 + scale);
        y *= (1 + scale);
    }

    public void rotate(double angle) {

        x = x * Math.cos(angle) + y * Math.sin(angle);
        y = -x * Math.sin(angle) + y * Math.cos(angle);
    }

    public void shift(double x1, double y1) {
        x += x1;
        y += y1;
    }


}
