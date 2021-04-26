package Dependencies;

import DateStructure.Equation;
import DateStructure.LinearSystemOfEquations;
import DateStructure.Point;
import Exceptions.CannotBuildFunction;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.pow;

public class QuadraticDependency extends Dependent {

    public QuadraticDependency(ArrayList<Point> points, BufferedWriter writer) {
        this.dependencyName = "Квадратичная функция";
        this.points = points;
        this.numberOfPoints = points.size();
        this.writer = writer;
    }

    @Override
    public void buildDependency() {
        int numberOfPoints = points.size();
        /* Вычисление коэффициентов матрицы */
        LinearSystemOfEquations linearSystem = new LinearSystemOfEquations(3);
        double a1 = 0d, b1 = 0d, c1 = 0d, d1 = 0d, a2 = 0d, d2 = 0d, a3 = 0d, d3 = 0d;
        for (Point point : points) {
            a1 += pow(point.getX(), 2);
            b1 += point.getX();
            d1 += point.getY();
            a2 += pow(point.getX(), 3);
            d2 += point.getX() * point.getY();
            a3 += pow(point.getX(), 4);
            d3 += pow(point.getX(), 2) * point.getY();
        }
        Equation equation = new Equation(new ArrayList<>(Arrays.asList(a1, b1, (double) numberOfPoints)), d1);
        linearSystem.addEquation(equation);
        equation = new Equation(new ArrayList<>(Arrays.asList(a2, a1, b1)), d2);
        linearSystem.addEquation(equation);
        equation = new Equation(new ArrayList<>(Arrays.asList(a3, a2, a1)), d3);
        linearSystem.addEquation(equation);
        coefficients = linearSystem.solve();
        if (Double.isNaN(coefficients[0])) {
            setExist(false);
            throw new CannotBuildFunction(dependencyName);
        }
        /* Квадратичная функция */
        function = (Double x) -> coefficients[0] * pow (x, 2) + coefficients[1] * x + coefficients[2];
        calculateDeviation(points);
        calculateRootMeanSquareDeviation(numberOfPoints);
    }

    @Override
    void createDependencyFunction() {
        setDependencyFunction(String.format("%.3fx²%+.3fx%+.3f", coefficients[0], coefficients[1], coefficients[2]));
    }
}
