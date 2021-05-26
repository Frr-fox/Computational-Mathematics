package Methods;

import DataStructure.Point;

import java.util.ArrayList;

public class Newton implements Calculatable {
    private ArrayList<Point> data;
    private String result = "";
    private double n = 0.0;

    public Newton(ArrayList<Point> data) {
        this.data = data;
    }

    public double calculate(double x) {
        double n = data.get(0).getY();
        for (int i = 1; i < data.size(); i++) {
            n += calculateDividedDifference(i)*calculateTerm(x, i);
        }
        this.n = n;
        return n;
    }

    private double calculateDividedDifference(int n) {
        double divideDifference = 0.0;
        for (int i = 0; i <= n; i++) {
            double part = data.get(i).getY();
            for (int j = 0; j <= n; j++) {
                if (i != j)
                    part /= data.get(i).getX() - data.get(j).getX();
            }
            divideDifference += part;
        }
        addToStringResult(divideDifference, n);
        return divideDifference;
    }

    private double calculateTerm(double x, int i) {
        double composition = 1.0;
        for (int j = 0; j < i; j++) {
            composition *= x - data.get(j).getX();
        }
        return composition;
    }

    private void addToStringResult(double divideDifference, int n) {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i <= n; i++) {
            if (i != 0) name.append(", ");
            name.append("x").append(i);
        }
        result += String.format("f(%s) = %.4f\n", name, divideDifference);
    }

    public void printStringResult(double xValue) {
        System.out.println(result);
        System.out.printf("Nn (%.3f) = %.4f\n", xValue, n);
    }
}
