package com.pedro.mandelbrot;

import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


public class PointTest {

    @Test
    public void test_computePoint() {
        Point pt = Point.computePoint(1.0, 2.0);
        assertNotNull(pt);
        assertEquals("A is not equals to 1.0", 1.0, pt.getA());
        assertEquals("A is not equals to 1.0", 2.0, pt.getB());
    }

    @Test
    public void test_withColor() {
        Point pt = Point.computePoint(1.0, 2.0);
        pt.withColor(Color.BLUE);
        assertEquals("Color is not BLUE", Color.BLUE, pt.getC());
    }

    @Test
    public void test_toString() {
        Point pt = Point.computePoint(1.0, 2.0);
        pt.withColor(Color.BLUE);
        assertEquals("toString is not OK", "Point{a=1.0, b=2.0, c=java.awt.Color[r=0,g=0,b=255]}", pt.toString());
    }
}
