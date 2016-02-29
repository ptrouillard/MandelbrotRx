package com.pedro.mandelbrot.impl;

import com.pedro.mandelbrot.*;
import org.omg.CORBA.TIMEOUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.pedro.mandelbrot.MandelbrotColorizer.computeColor;
import static com.pedro.mandelbrot.MandelbrotComputation.inMandelbrotSet;
import static com.pedro.mandelbrot.MandelbrotData.*;

/**
 * Created by pierre on 28/02/2016.
 */
public class MandelbrotMultiThread {

    public static Stack<com.pedro.mandelbrot.Point> points = new Stack<com.pedro.mandelbrot.Point>();

    public static Logger logger = LoggerFactory.getLogger(MandelbrotMultiThread.class);

    public static void main(String[] args) {

        logger.info("open window");

        new MandelbrotMultiThread()
                .computeMandelbrot()
                .paintMandelbrot();

    }

    public MandelbrotMultiThread paintMandelbrot() {
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

        //logger.info("starts mandelbrot computation");

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

    public MandelbrotMultiThread computeMandelbrot() {

        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
        newFixedThreadPool.submit( new MandelbrotMaster(newFixedThreadPool));
        return this;
    }

    /**
     * ce worker lance les itérations sur tous les points (a,b)
     */
    public class MandelbrotMaster implements Runnable {

        ExecutorService newFixedThreadPool = null;

        public MandelbrotMaster(ExecutorService newFixedThreadPool) {
            this.newFixedThreadPool = newFixedThreadPool;
        }

        public void run() {

            logger.info("creating all tasks for mandelbrot computation");
            for (double a=A_MIN; a <A_MAX; a+= A_STEP) {
                for (double b=MandelbrotData.B_MIN; b <B_MAX; b+= B_STEP) {
                    newFixedThreadPool.submit( new MandelbrotSlave(a, b));
                }
            }


            logger.info("creating termination task");
            newFixedThreadPool.submit(new MandelbrotStopper(newFixedThreadPool));
        }
    }

    /**
     * ce worker arrête le travail du pool (poison)
     */
    public class MandelbrotStopper implements Runnable {

        ExecutorService newFixedThreadPool = null;

        public MandelbrotStopper(ExecutorService newFixedThreadPool) {
            this.newFixedThreadPool = newFixedThreadPool;
        }

        public void run() {

            newFixedThreadPool.shutdown();
            try {
                newFixedThreadPool.awaitTermination(5, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Computation task ended, closing the pool.");
        }
    }

    public class MandelbrotSlave implements Runnable {

        private double a,b;

        public MandelbrotSlave(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            mandelbrot(com.pedro.mandelbrot.Point.computePoint(a, b));
        }
    }


}
