package com.pedro.mandelbrot.impl;

import com.pedro.mandelbrot.Point;
import rx.Observable;

import static com.pedro.mandelbrot.MandelbrotData.*;
import static com.pedro.mandelbrot.MandelbrotScaler.x2a;
import static com.pedro.mandelbrot.MandelbrotScaler.y2b;
import static com.pedro.mandelbrot.Point.computePoint;
import static rx.Observable.just;
import static rx.Observable.range;

/**
 * Created by pierre on 29/02/2016.
 */
public class TestRx {

    public static void main(String[] args) {




        Observable<Point> obs = range(1, CANVAS_WIDTH * CANVAS_HEIGHT)
                .map(n -> computePoint(x2a(n / CANVAS_HEIGHT), y2b(n % CANVAS_HEIGHT)));


/*        Observable<Double> obs = just(A_STEP)
                .repeat()
                .scan(A_MIN, (acc, a) -> acc + a)
                .takeWhile(val -> val < A_MAX)
                .repeat(CANVAS_WIDTH);// génère 1,2,3,4,1,2,3,4,1,2,3,4...
*/
/*
                just(B_STEP)
                        .repeat()
                        .scan(B_MIN, (acc, b) -> acc + b),
                Point::computePoint
        );*/

       obs.subscribe(pt-> System.out.println(pt.toString()));


        //doOnNext( pt -> System.out.println(pt.toString())).doOnCompleted(()->System.out.println("Fin"));
    }
}
