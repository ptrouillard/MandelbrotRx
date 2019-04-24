# MandelbrotRx

Demo of a javaRx implementation of the "Mandelbrot" fractal set

Basic implementation using single thread and "java concurrent" multithreading are also shown (but do not work very well)

Note that the Rx implementation is really faster comparing to single threaded and multi threaded versions.

Use descartes to detect hole in unit tests

https://github.com/STAMP-project/pitest-descartes

mvn clean package
mvn org.pitest:pitest-maven:mutationCoverage -DmutationEngine=descartes
