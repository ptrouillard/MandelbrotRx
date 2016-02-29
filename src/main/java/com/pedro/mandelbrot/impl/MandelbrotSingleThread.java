package com.pedro.mandelbrot.impl;

import com.pedro.mandelbrot.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

import static com.pedro.mandelbrot.MandelbrotColorizer.computeColor;
import static com.pedro.mandelbrot.MandelbrotComputation.inMandelbrotSet;
import static com.pedro.mandelbrot.MandelbrotData.*;

/**
 * Created by pierre on 28/02/2016.
 */
public class MandelbrotSingleThread {

    public static Stack<com.pedro.mandelbrot.Point> points = new Stack<com.pedro.mandelbrot.Point>();

    public static Logger logger = LoggerFactory.getLogger(MandelbrotSingleThread.class);

    public static void main(String[] args) {

        logger.info("open window");

        new MandelbrotSingleThread()

                .computeMandelbrot().paintMandelbrot()
            ;

    }

    public MandelbrotSingleThread paintMandelbrot() {
        JFrame frame = new JFrame();
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        MandelbrotCanvas canvas = new MandelbrotCanvas(points);

        frame.getContentPane().add(canvas);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                logger.info("closing window");
                System.exit(0);
            }
        });

        frame.setVisible(true);
        return this;
    }


    public void mandelbrot(com.pedro.mandelbrot.Point pt) {

        double x = 0;
        double y = 0;

        logger.info("starts mandelbrot computation");

        for (int n=0; n< N_MAX; n++) {
            double x2 = Math.pow(x, 2.0);
            double y2 = Math.pow(y, 2.0);
            x = x2 - y2 + pt.getA();
            y = 2 * x * y + pt.getB();
            if (!inMandelbrotSet(x, y)) {
                continue;
            }
            else {
                logger.info("find a point to draw: {}", pt);
                Color color = computeColor(y, x);
                points.push(com.pedro.mandelbrot.Point.drawingPoint(pt.getA(), pt.getB(), color));
                break;
            }
        }

    }


    public MandelbrotSingleThread computeMandelbrot() {
        for (double a=A_MIN; a <A_MAX; a+= A_STEP) {
            for (double b=MandelbrotData.B_MIN; b <B_MAX; b+= B_STEP) {
                mandelbrot(com.pedro.mandelbrot.Point.computePoint(a, b));
            }
        }
        return this;
    }

}
