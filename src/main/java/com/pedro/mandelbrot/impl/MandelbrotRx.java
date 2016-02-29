package com.pedro.mandelbrot.impl;

import com.pedro.mandelbrot.MandelbrotCanvasWithSubject;
import com.pedro.mandelbrot.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.pedro.mandelbrot.MandelbrotColorizer.computeColor;
import static com.pedro.mandelbrot.MandelbrotComputation.inMandelbrotSet;
import static com.pedro.mandelbrot.MandelbrotData.*;
import static com.pedro.mandelbrot.MandelbrotScaler.x2a;
import static com.pedro.mandelbrot.MandelbrotScaler.y2b;
import static com.pedro.mandelbrot.Point.computePoint;
import static rx.subjects.BehaviorSubject.*;


/**
 * Created by pierre on 28/02/2016.
 */
public class MandelbrotRx {

    public static Observable<Point> points = null;

    public static Logger logger = LoggerFactory.getLogger(MandelbrotRx.class);

    public static void main(String[] args) {

        //logger.info("open window");
        new MandelbrotRx()
                .computeMandelbrot()
                .paintMandelbrot();
    }

    public MandelbrotRx paintMandelbrot() {
        JFrame frame = new JFrame();
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT+25);
        MandelbrotCanvasWithSubject canvas = new MandelbrotCanvasWithSubject(points);

        frame.getContentPane().add(canvas);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                //logger.info("closing window");
                System.exit(0);
            }
        });

        frame.setVisible(true);
        return this;
    }


    public Point mandelbrot(com.pedro.mandelbrot.Point pt) {

        double x = 0;
        double y = 0;

        for (int n=0; n< N_MAX; n++) {
            double x2 = x*x;
            double y2 = y*y;
            x = x2 - y2 + pt.getA();
            y = 2 * x * y + pt.getB();
            if (!inMandelbrotSet(x, y)) {
                continue;
            }
            else {
                //logger.info("find a point to draw: {}", pt);
                return com.pedro.mandelbrot.Point.drawingPoint(pt.getA(), pt.getB(), computeColor(y, x));
            }
        }

        return null;
    }

    public MandelbrotRx computeMandelbrot() {

        //  génère l'ensemble des couples de points (a,b)
        // en partant de la liste des pixels (range de int)
        points = range(1, CANVAS_WIDTH * CANVAS_HEIGHT)
                .map(n -> computePoint(x2a(n / CANVAS_HEIGHT), y2b(n % CANVAS_HEIGHT)))
                .map(pt -> mandelbrot(pt)); // calcule mandelbrot pour cette série de points

        return this;
    }

}
