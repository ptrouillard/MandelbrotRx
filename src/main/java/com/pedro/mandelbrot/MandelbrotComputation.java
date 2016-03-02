package com.pedro.mandelbrot;

/**
 * Created by pierre on 29/02/2016.
 */
public class MandelbrotComputation {
    /**
     * Teste si le point appartient a l'ensemble de mandelbrot
     * @param x
     * @param y
     * @return
     */
    public static boolean inMandelbrotSet(double x, double y) {
        return x*x + y*y > 4.0;
    }
}
