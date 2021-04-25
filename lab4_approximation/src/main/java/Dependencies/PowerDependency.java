package Dependencies;

import DateStructure.Equation;
import DateStructure.LinearSystemOfEquations;
import DateStructure.Point;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;


import static java.lang.Math.*;

public class PowerDependency extends Dependent {

    public PowerDependency(ArrayList<Point> points, BufferedWriter writer) {
        this.dependencyName = "Степенная зависимость";
        this.points = points;
        this.numberOfPoints = points.size();
        this.writer = writer;
    }

    @Override
    public void buildDependency() {
        int numberOfPoints = points.size();
        /* Вычисление коэффициентов матрицы */
        LinearSystemOfEquations linearSystem = new LinearSystemOfEquations(2);
        double a1 = 0d, b1 = 0d, c1 = 0d, c2 = 0d;
        for (Point value : points) {
            a1 += pow(log(value.getX()), 2);
            b1 += log(value.getX());
            c1 += log(value.getX()) * log(value.getY());
            c2 += log(value.getY());
        }
        Equation equation = new Equation(new ArrayList<>(Arrays.asList(b1, a1)), c1);
        linearSystem.addEquation(equation);
        equation = new Equation(new ArrayList<>(Arrays.asList((double) numberOfPoints, b1)), c2);
        linearSystem.addEquation(equation);
        coefficients = linearSystem.solve();
        coefficients[0] = exp(coefficients[0]);
        /* Степенная функция */
        function = (Double x) -> coefficients[0] * pow(x, coefficients[1]);
        calculateDeviation(points);
        calculateRootMeanSquareDeviation(numberOfPoints);
    }

    @Override
    void createDependencyFunction() {
        setDependencyFunction(String.format("%.3fx^%.3f", coefficients[0], coefficients[1]));
    }
}
