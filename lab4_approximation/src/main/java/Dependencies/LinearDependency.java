package Dependencies;

import DateStructure.Equation;
import DateStructure.LinearSystemOfEquations;
import DateStructure.Point;
import Exceptions.CannotBuildFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

public class LinearDependency extends Dependent {

    public LinearDependency(ArrayList<Point> points, BufferedWriter writer) {
        this.dependencyName = "Линейная функция";
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
            c1 += value.getX() * value.getY();
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
        /* Линейная функция */
        function = (Double x) -> coefficients[0] * x + coefficients[1];
        calculateDeviation(points);
        calculateRootMeanSquareDeviation(numberOfPoints);
    }

    @Override
    void createDependencyFunction() {
        setDependencyFunction(String.format("%.3fx%+.3f", coefficients[0], coefficients[1]));
    }

    @Override
    protected void printAddingCharacters() throws IOException {
        double r = calculatePirsonsCoefficientOfCorrelation();
        writer.write(String.format("Коэффициент корреляции Пирсона равен %.3f\n\n", r));
        writer.flush();
    }

    public double calculatePirsonsCoefficientOfCorrelation() {
        double averageX = 0, averageY = 0;
        for (Point point: points) {
            averageX += point.getX() / numberOfPoints;
            averageY += point.getY() / numberOfPoints;
        }
        double r = 0, numeral = 0, denominator1 = 0, denominator2 = 0;
        for (Point point: points) {
            numeral += (point.getX() - averageX)*(point.getY() - averageY);
            denominator1 += pow((point.getX() - averageX), 2);
            denominator2 += pow((point.getY() - averageY), 2);
        }
        r = numeral / sqrt(denominator1 * denominator2);
        return r;
    }
}
