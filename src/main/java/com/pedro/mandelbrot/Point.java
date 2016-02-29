package com.pedro.mandelbrot;

import java.awt.*;

/**
 * Created by pierre on 28/02/2016.
 */
public class Point {

    public static Point computePoint(double a, double b) {
        return new Point(a, b);
    }

    public static Point drawingPoint(double x, double y, Color c) {
        return new Point(x, y, c);
    }

    private Point(double a, double b) {
        this(a,b, Color.WHITE);
    }
    private Point(double a, double b, Color c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    private double a;
    private double b;
    private Color c;

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Color getC() {
        return c;
    }

    @Override
    public String toString() {
        return "Point{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
