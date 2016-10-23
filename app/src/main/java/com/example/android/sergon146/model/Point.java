package com.example.android.sergon146.model;

/**
 * Created by Sergon146 on 017 17.10.16.
 */

public class Point {
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


}
