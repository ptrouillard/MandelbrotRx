package com.pedro.mandelbrot;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pierre on 02/03/2016.
 */
public class MandelbrotScalerTest {

    @Test
    public void testWithDefaultData() {

        MandelbrotScaler mandelbrotScaler =
                MandelbrotScaler.get().reset();

        assertEquals(-2, mandelbrotScaler.XtoA(0), 0.01);
        assertEquals(-1.5, mandelbrotScaler.YtoB(0), 0.01);
        assertEquals(0, mandelbrotScaler.XtoA(400), 0.01);
        assertEquals(0, mandelbrotScaler.YtoB(300), 0.01);
        assertEquals(2, mandelbrotScaler.XtoA(800), 0.01);
        assertEquals(1.5, mandelbrotScaler.YtoB(600), 0.01);


        assertEquals(0, mandelbrotScaler.AtoX(-2), 0.01);
        assertEquals(0, mandelbrotScaler.BtoY(-1.5), 0.01);
        assertEquals(400, mandelbrotScaler.AtoX(0), 0.01);
        assertEquals(300, mandelbrotScaler.BtoY(0), 0.01);
        assertEquals(800, mandelbrotScaler.AtoX(2), 0.01);
        assertEquals(600, mandelbrotScaler.BtoY(1.5), 0.01);

    }

    @Test
    public void testWithMovingCenter() {

        MandelbrotScaler mandelbrotScaler =
                MandelbrotScaler.get().reset().center(0,0);

        assertEquals(0, mandelbrotScaler.XtoA(0), 0.01);
        assertEquals(0, mandelbrotScaler.YtoB(0), 0.01);
        assertEquals(2.0, mandelbrotScaler.XtoA(400), 0.01);
        assertEquals(1.5, mandelbrotScaler.YtoB(300), 0.01);
        assertEquals(4.0, mandelbrotScaler.XtoA(800), 0.01);
        assertEquals(3, mandelbrotScaler.YtoB(600), 0.01);

        assertEquals(0, mandelbrotScaler.AtoX(0), 0.01);
        assertEquals(0, mandelbrotScaler.BtoY(0), 0.01);
        assertEquals(400, mandelbrotScaler.AtoX(2.0), 0.01);
        assertEquals(300, mandelbrotScaler.BtoY(1.5), 0.01);
        assertEquals(800, mandelbrotScaler.AtoX(4.0), 0.01);
        assertEquals(600, mandelbrotScaler.BtoY(3), 0.01);
    }
}