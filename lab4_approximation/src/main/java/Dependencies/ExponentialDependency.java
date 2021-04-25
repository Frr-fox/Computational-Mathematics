package Dependencies;

import DateStructure.Equation;
import DateStructure.LinearSystemOfEquations;
import DateStructure.Point;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

public class ExponentialDependency extends Dependent {

    public ExponentialDependency(ArrayList<Point> points, BufferedWriter writer) {
        this.dependencyName = "Экспоненциальная зависимость";
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
            a1 += pow(value.getX(), 2);
            b1 += value.getX();
            c1 += value.getX() * log(value.getY());
            c2 += log(value.getY());
        }
        Equation equation = new Equation(new ArrayList<>(Arrays.asList(b1, a1)), c1);
        linearSystem.addEquation(equation);
        equation = new Equation(new ArrayList<>(Arrays.asList((double) numberOfPoints, b1)), c2);
        linearSystem.addEquation(equation);
        coefficients = linearSystem.solve();
        coefficients[0] = exp(coefficients[0]);
        /* Экспоненциальная функция */
        function = (Double x) -> coefficients[0] * exp(x * coefficients[1]);
        calculateDeviation(points);
        calculateRootMeanSquareDeviation(numberOfPoints);
    }

    @Override
    void createDependencyFunction() {
        setDependencyFunction(String.format("%.3f * exp(%.3fx)", coefficients[0], coefficients[1]));
    }
}
