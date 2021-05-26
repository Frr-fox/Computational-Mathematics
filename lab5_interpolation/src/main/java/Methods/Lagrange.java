package Methods;

import DataStructure.Point;

import java.util.ArrayList;

public class Lagrange implements Calculatable {
    private ArrayList<Point> data;
    private String result = "";
    private double l = 0.0;

    public Lagrange(ArrayList<Point> data) {
        this.data = data;
    }

    public double calculate(double x) {
        double ln = 0.0;
        for (int i = 0; i < data.size(); i++) {
            double l = calculatel(x, i);
            addToStringResult(l, i);
            ln += l;
        }
        this.l = ln;
        return ln;
    }

    private double calculatel(double x, int i) {
        double numeral = 1.0;
        double denominator = 1.0;
        for (int j = 0; j < data.size(); j++) {
            if (i != j) {
                numeral *= x - data.get(j).getX();
                denominator *= data.get(i).getX() - data.get(j).getX();
            }
        }
        return (numeral / denominator) * data.get(i).getY();
    }

    private void addToStringResult(double l, int i) {
        result += String.format("l(%d) = %.4f\n", i, l);
    }

    public void printStringResult(double xValue) {
        System.out.println(result);
        System.out.println(String.format("Ln (%.4f) = %.3f\n", xValue, l));
    }
}