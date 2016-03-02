package com.pedro.mandelbrot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.awt.*;
import java.util.function.Supplier;

import static com.pedro.mandelbrot.MandelbrotData.*;

/**
 * Created by pierre on 29/02/2016.
 */
public class MandelbrotCanvas extends Canvas {

    public static Logger logger = LoggerFactory.getLogger(MandelbrotCanvas.class);

    private Supplier<Observable<Point>> pointSupplier;

    private MandelbrotScaler scaler;

    public MandelbrotCanvas(Supplier<Observable<Point>> pointSupplier, MandelbrotScaler scaler) {
        setBackground (Color.BLACK);
        setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.pointSupplier = pointSupplier;
        this.scaler = scaler;
    }
    public void paint (Graphics g) {
        Graphics2D g2;
        g2 = (Graphics2D) g;

        Observable<Point> points = pointSupplier.get();

        logger.info("points : {}" , points);

        points
                .subscribe(
                    pt -> {
                        if (pt != null) {
                            g2.setColor(pt.getC());
                            g2.drawRect(scaler.AtoX(pt.getA()), scaler.BtoY(pt.getB()), 1, 1);
                        }
                    }
                ).unsubscribe();
        ;

        g2.setColor(Color.BLACK);
        g2.drawString("(0,0)", scaler.AtoX(0), scaler.BtoY(0));
        g2.drawString("(-1,0)", scaler.AtoX(-1), scaler.BtoY(0));
        g2.drawString("(1,0)", scaler.AtoX(1.0), scaler.BtoY(0.0));
        g2.drawString("(0,1)", scaler.AtoX(0), scaler.BtoY(1));
        g2.drawString("(0,-1)", scaler.AtoX(0), scaler.BtoY(-1));

        g2.setColor(Color.WHITE);
        g2.drawString("("+ scaler.getCanvasWidth()+","+ scaler.getCanvasHeight()+")", 10,20);
        g2.drawString(scaler.toString(), 10, 35);
        g2.drawString("Use scroll to zoom/unzoom", 10, 50);
        g2.drawString("Click on screen to change the point of view", 10, 65);
        g2.drawRect(1, 1, getWidth()-1, getHeight()-1);


    }

}
