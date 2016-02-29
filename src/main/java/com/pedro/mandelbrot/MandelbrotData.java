package com.pedro.mandelbrot;

/**
 * Created by pierre on 29/02/2016.
 */
public abstract class MandelbrotData {

    /**
     * Nombre d'itérations maximum avant de considérer le point comme n'appartenant pas à l'ensemble de MandelbrotSingleThread
     */
    public static int N_MAX = 12;

    /**
     * Taille du canvas
     */
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 600;

    /**
     * Taille du thread pool de traitement
     */
    public static final int THREAD_POOL_SIZE = 5;

    /**
     * Intervalle de choix des points (a,b)
     */
    public static final double A_MIN = -2;
    public static final double A_MAX = 2;
    public static final double B_MIN = -1.5;
    public static final double B_MAX = 1.5;

    /**
     * Calcul de la résolution du calcul en fonction de l'intervalle de choix des points ainsi que
     * de la taille de l'écran
     */
    public static final double A_STEP = (A_MAX - A_MIN) / CANVAS_WIDTH;
    public static final double B_STEP = (B_MAX - B_MIN) / CANVAS_HEIGHT;


    /**
     * Centre de l'écran
     */
    public static final int CANVAS_CENTER_X = CANVAS_WIDTH / 2;
    public static final int CANVAS_CENTER_Y = CANVAS_HEIGHT / 2;

}
