package optimization;

import javafx.util.Pair;

import java.util.function.DoubleFunction;

import static java.lang.Math.*;

public class FunctionMinimum {

    private final static double GOLDEN_RATIO = (1 + sqrt(5)) / 2;

    public static Pair<Double, Double> findMinGoldenRatio(DoubleFunction<Double> func, double a, double b, double precision) {
        do {
            double x1 = b - (b - a) / GOLDEN_RATIO;
            double x2 = a + (b - a) / GOLDEN_RATIO;
            double y1 = func.apply(x1);
            double y2 = func.apply(x2);
            if (y1 >= y2)
                a = x1;
            else
                b = x2;
        } while (abs(b - a) >= precision);
        double xMin = (a + b) / 2;
        return new Pair<>(xMin, func.apply(xMin));
    }

    public static Pair<Double, Double> findMinParabola(DoubleFunction<Double> func, double a, double b, double precision) {
        double x1 = a;
        double x2 = (a + b) / 2;
        double x3 = b;
        double prevMinX, minX = Double.MIN_VALUE;
        Pair<Double, Double> parabolaMin;
        do {
            prevMinX = minX;
            parabolaMin = getParabolaMin(func, x1, x2, x3);
            minX = parabolaMin.getKey();
            if (minX >= x2){
                x1 = x2;
                x2 = minX;
            }
            else {
                x3 = x2;
                x2 = minX;
            }
        } while (abs(prevMinX - minX) >= precision);
        return new Pair<>(minX, func.apply(minX));
    }

    private static Pair<Double, Double> getParabolaMin(DoubleFunction<Double> func, double x1, double x2, double x3) {
        double y1 = func.apply(x1);
        double y2 = func.apply(x2);
        double y3 = func.apply(x3);
        double delta = (x2 - x1) * (x3 - x1) * (x3 - x2);
        double a = ((x3 - x2) * y1 - (x3 - x1) * y2 + (x2 - x1) * y3) / delta;
        double b = (-(x3 * x3 - x2 * x2) * y1 + (x3 * x3 - x1 * x1) * y2 - (x2 * x2 - x1 * x1) * y3) / delta;
        double c = (x2 * x3 * (x3 - x2) * y1 - x1 * x3 * (x3 - x1) * y2 + x1 * x2 * (x2 - x1) * y3) / delta;
        return new Pair<>(-b/(2*a), - b*b/(4*a) + c);
    }

    public static void main(String[] args) {
        Pair<Double, Double> minimumGoldenRatio = findMinGoldenRatio((x) -> log(1 + x * x) - sin(x), 0, PI / 4, 0.001);
        System.out.println("MinX = " + minimumGoldenRatio.getKey() + "; MinY = " + minimumGoldenRatio.getValue());
        Pair<Double, Double> minimumParabola = findMinParabola((x) -> log(1 + x * x) - sin(x), 0, PI / 4, 0.001);
        System.out.println("MinX = " + minimumParabola.getKey() + "; MinY = " + minimumParabola.getValue());
    }

}
