package com.pedro.mandelbrot;

import java.awt.*;

/**
 * Created by pierre on 29/02/2016.
 */
public class MandelbrotColorizer {

    public static Color computeColor(double y, double x) {
        if (x==0.0)
            return Color.BLACK;
        double c = y/x;
        return new Color((int)(c*15000), false);
    }
}
