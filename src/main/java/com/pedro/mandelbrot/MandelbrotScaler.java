package com.pedro.mandelbrot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static com.pedro.mandelbrot.MandelbrotData.*;

/**
 * Created by pierre on 29/02/2016.
 */
public class MandelbrotScaler {

    public static Logger logger = LoggerFactory.getLogger(MandelbrotScaler.class);

    private double aMin = 0d, aMax = 0d, bMin = 0d, bMax = 0d;
    private int canvasWidth = 0, canvasHeight = 0;
    private int xMin = 0;
    private int xMax = 0;
    private int yMin = 0;
    private int yMax = 0;
    private Point centerXY = Point.computePoint(0,0);

    private static MandelbrotScaler _instance;
    public static MandelbrotScaler get() {
        if (_instance == null) {
            _instance = new MandelbrotScaler();
        }
        return _instance;
    }

    private MandelbrotScaler() {
        reset();
    }

    public MandelbrotScaler reset() {
        logger.info("scaler reset");
        canvasWidth = CANVAS_WIDTH;
        canvasHeight = CANVAS_HEIGHT;
        aMin = -2;
        aMax = 2;
        bMin = -1.5;
        bMax = 1.5;
        center(CANVAS_WIDTH/2, CANVAS_HEIGHT/2);
        return this;
    }

    private double scaleXtoA() {
        return (aMax - aMin) / (xMax - xMin);
    }

    private double scaleYtoB() {
        return (bMax - bMin) / (yMax - yMin);
    }

    public double XtoA(int x) {
        return scaleXtoA() * (x - centerXY.getA());
    }

    public double YtoB(int y) {
        return scaleYtoB() * (y - centerXY.getB());
    }

    public int AtoX(double a) {
        return (int)(a / scaleXtoA() + centerXY.getA());
    }

    public int BtoY(double b) {
        return (int)(b / scaleYtoB() + centerXY.getB());
    }

    public MandelbrotScaler center(int x, int y) {

        this.centerXY = Point.drawingPoint(x,y, Color.WHITE);
        this.xMin = 0;
        this.yMin = 0;
        this.xMax = canvasWidth;
        this.yMax = canvasHeight;
        return this;
    }

    public MandelbrotScaler zoom() {
        aMin /= 2;
        aMax /= 2;
        bMin /= 2;
        bMax /= 2;
        return this;
    }

    public MandelbrotScaler unzoom() {
        aMin *= 2;
        aMax *= 2;
        bMin *= 2;
        bMax *= 2;
        return this;
    }

    public MandelbrotScaler withCanvasSize(int width, int height) {
        this.canvasWidth = width;
        this.canvasHeight = height;
        return this;
    }

    public int getCanvasWidth() {
        return this.canvasWidth;
    }

    public int getCanvasHeight() {
        return this.canvasHeight;
    }


    @Override
    public String toString() {
        return "MandelbrotScaler{" +
                "aMin=" + aMin +
                ", aMax=" + aMax +
                ", bMin=" + bMin +
                ", bMax=" + bMax +
                '}';
    }
}
