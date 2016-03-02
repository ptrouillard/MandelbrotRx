package com.pedro.mandelbrot.impl;

import com.pedro.mandelbrot.MandelbrotCanvas;
import com.pedro.mandelbrot.MandelbrotScaler;
import com.pedro.mandelbrot.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.pedro.mandelbrot.MandelbrotColorizer.computeColor;
import static com.pedro.mandelbrot.MandelbrotComputation.inMandelbrotSet;
import static com.pedro.mandelbrot.MandelbrotData.*;
import static com.pedro.mandelbrot.Point.computePoint;
import static rx.subjects.BehaviorSubject.*;


/**
 * Created by pierre on 28/02/2016.
 */
public class MandelbrotRx implements Supplier<Observable<Point>> {

    private MandelbrotCanvas canvas = null;

    public MandelbrotScaler scaler = null;

    public static Logger logger = LoggerFactory.getLogger(MandelbrotRx.class);

    public static void main(String[] args) {

        new MandelbrotRx()
                .paintMandelbrot();
    }

    private MandelbrotRx() {
        scaler = MandelbrotScaler.get();
        canvas = new MandelbrotCanvas(this, scaler);
    }

    public MandelbrotRx paintMandelbrot() {

        JFrame frame = new JFrame();
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT+25);

        frame.getContentPane().add(canvas);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // resize
                Dimension size = e.getComponent().getSize();
                int newWidth = size.width;
                int newHeight = size.height-25;
                logger.info("newWidth={}, newHeight={}", newWidth, newHeight);
                scaler.withCanvasSize(newWidth, newHeight);
            }
        });
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                java.awt.Point locationOnScreen = e.getPoint();
                scaler.center(locationOnScreen.x, locationOnScreen.y);
                canvas.repaint();
            }
        });


        BehaviorSubject<MouseWheelEvent> mouseWheelEvents = create();
        frame.addMouseWheelListener(e ->  {
            mouseWheelEvents.onNext(e);

        });

        // observer mouse wheel events
        mouseWheelEvents
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(e -> {
            if (e.getWheelRotation() > 0)
                scaler.zoom();
            else
                scaler.unzoom();
            canvas.repaint();
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
                return pt.withColor(computeColor(y, x));
            }
        }
        return pt;
    }

    @Override
    public Observable<Point> get() {
        //  génère l'ensemble des couples de points (a,b)
        // en partant de la liste des pixels (range de int)
        Observable<Point> points = range(1, scaler.getCanvasWidth() * scaler.getCanvasHeight())
                .map(n -> computePoint(scaler.XtoA(n / scaler.getCanvasHeight()), scaler.YtoB(n % scaler.getCanvasHeight())))
                .map(pt -> mandelbrot(pt)); // calcule mandelbrot pour cette série de points
        return points;
    }
}
