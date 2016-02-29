package com.pedro.mandelbrot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.awt.*;

import static com.pedro.mandelbrot.MandelbrotScaler.a2x;
import static com.pedro.mandelbrot.MandelbrotScaler.b2y;

import static com.pedro.mandelbrot.MandelbrotData.*;

/**
 * Created by pierre on 29/02/2016.
 */
public class MandelbrotCanvasWithSubject extends Canvas {

    public static Logger logger = LoggerFactory.getLogger(MandelbrotCanvasWithSubject.class);

    public static Observable<Point> points = null;

    public MandelbrotCanvasWithSubject(Observable<Point> points) {
        setBackground (Color.BLACK);
        setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.points = points;
    }

    public void paint (Graphics g) {
        Graphics2D g2;
        g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.drawString("(0,0)", a2x(0), b2y(0));
        g2.drawString("(-1,0)", a2x(-1), b2y(0));
        g2.drawString("(1,0)", a2x(1.0), b2y(0.0));
        g2.drawString("(0,1)", a2x(0), b2y(1));
        g2.drawString("(0,-1)", a2x(0), b2y(-1));
        g2.drawRect(1,1,799,599);

        points
                .subscribe(
                    pt -> {
                        if (pt != null) {
                            logger.info("paint {}", pt);
                            g2.setColor(pt.getC());
                            g2.drawRect(a2x(pt.getA()), b2y(pt.getB()), 1, 1);
                        }
                    }
                )
        ;
    }

}
