package Dependencies;

import DateStructure.Equation;
import DateStructure.LinearSystemOfEquations;
import DateStructure.Point;
import Exceptions.CannotBuildFunction;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

public class LogarithmicDependency extends Dependent {

    public LogarithmicDependency(ArrayList<Point> points, BufferedWriter writer) {
        this.dependencyName = "Логарифмическая функция";
        this.points = points;
        this.numberOfPoints = points.size();
        this.writer = writer;
    }

    @Override
    public void buildDependency() {
        /* Вычисление коэффициентов матрицы */
        int numberOfPoints = points.size();
        LinearSystemOfEquations linearSystem = new LinearSystemOfEquations(2);
        double a1 = 0d, b1 = 0d, c1 = 0d, c2 = 0d;
        for (Point value : points) {
            a1 += pow(log(value.getX()), 2);
            b1 += log(value.getX());
            c1 += log(value.getX()) * value.getY();
            c2 += value.getY();
        }
        Equation equation = new Equation(new ArrayList<>(Arrays.asList(a1, b1)), c1);
        linearSystem.addEquation(equation);
        equation = new Equation(new ArrayList<>(Arrays.asList(b1, (double) numberOfPoints)), c2);
        linearSystem.addEquation(equation);
        coefficients = linearSystem.solve();
        if (Double.isNaN(coefficients[0])) {
            setExist(false);
            throw new CannotBuildFunction(dependencyName);
        }
        /* Логарифмическая функция */
        function = (Double x) -> coefficients[0] * log(x) + coefficients[1];
        calculateDeviation(points);
        calculateRootMeanSquareDeviation(numberOfPoints);
    }

    @Override
    void createDependencyFunction() {
        setDependencyFunction(String.format("%.3fln(x)%+.3f", coefficients[0], coefficients[1]));
    }
}
