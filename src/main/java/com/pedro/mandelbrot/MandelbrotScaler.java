package com.pedro.mandelbrot;

import static com.pedro.mandelbrot.MandelbrotData.*;

/**
 * Created by pierre on 29/02/2016.
 */
public class MandelbrotScaler {

    public final static double A_RANGE = A_MAX - A_MIN;
    public final static double B_RANGE = B_MAX - B_MIN;
    public final static double X_SCALE = CANVAS_WIDTH / A_RANGE;
    public final static double Y_SCALE = CANVAS_HEIGHT / B_RANGE;

    public static int a2x(double a) {
        return (int)( X_SCALE * a + CANVAS_CENTER_X);
    }

    public static double x2a(int x) {
        return (x - CANVAS_CENTER_X)/X_SCALE;
    }

    public static int b2y(double b) {
        return (int)( Y_SCALE * b + CANVAS_CENTER_Y);
    }

    public static double y2b(int y) {
        return (y - CANVAS_CENTER_Y)/Y_SCALE;
    }
}
