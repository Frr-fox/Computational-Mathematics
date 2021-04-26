package Dependencies;

import DateStructure.Point;
import Exceptions.CannotBuildFunction;
import Utils.DrawingGraph;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;

import static Utils.StringFormatter.formatStingCenter;

public class FunctionalDependence {
    private ArrayList<Point> points;
    private BufferedWriter writer;

    public FunctionalDependence(ArrayList<Point> points, BufferedWriter writer) {
        this.points = points;
        this.writer = writer;
    }

    public void runDifferentWaysOfApproximation() throws IOException {
        DrawingGraph drawing = new DrawingGraph(points);
        drawing.drawMainFrame();
        ArrayList<Dependent> dependencies = new ArrayList<>(Arrays.asList(new LinearDependency(points, writer),
                new QuadraticDependency(points, writer), new ExponentialDependency(points, writer),
                new LogarithmicDependency(points, writer), new PowerDependency(points, writer)));
        for (Dependent dependency: dependencies) {
            try {
                dependency.buildDependency();
                dependency.createDependencyFunction();
                dependency.printTable();
                dependency.printAddingCharacters();
                drawing.drawDependency(dependency);
            } catch (CannotBuildFunction e) {
                writer.write(e.getMessage());
                writer.flush();
            }
        }
        drawing.showGraph();
        int theMostSuitableDependency = 0;
        double minRootMeanSquareDeviation = dependencies.get(0).getRootMeanSquareDeviation();
        for (int i = 0; i < dependencies.size(); i++) {
            if (dependencies.get(i).getRootMeanSquareDeviation() < minRootMeanSquareDeviation) {
                minRootMeanSquareDeviation = dependencies.get(i).getRootMeanSquareDeviation();
                theMostSuitableDependency = i;
            }
        }
        printResultsOfApproximation(dependencies);
        writer.write(String.format("Аппроксимирующая функция с наименьшим среднеквадратическим отклонением, равным %.3f, φ = %s\n",
                minRootMeanSquareDeviation, dependencies.get(theMostSuitableDependency).getDependencyFunction()));
        writer.flush();
    }

    private void printResultsOfApproximation(ArrayList<Dependent> dependencies) throws IOException {
        printHeader();
        String[] functions = {" φ = ax + b", " φ = ax² + bx + c", " φ = aeᵇᵡ", " φ = a ln(x) + b", " φ = axᵇ"};
        for (int i = 0; i < 5; i++) {
            String empty = "-";
            if (dependencies.get(i).isExist()) {
                if (dependencies.get(i).coefficients.length == 3) {
                    empty = String.format("%.3f", dependencies.get(i).coefficients[2]);
                }
                writer.write(String.format("|%-18s|%11.3f|%11.3f|%11s|%22.3f|%34.3f|\n", functions[i],
                        dependencies.get(i).coefficients[0], dependencies.get(i).coefficients[1],
                        formatStingCenter(empty, 11), dependencies.get(i).deviation,
                        dependencies.get(i).getRootMeanSquareDeviation()));
            } else {
                writer.write(String.format("|%-18s|%11s|%11s|%11s|%22s|%34s|\n", functions[i],
                        formatStingCenter(empty, 11), formatStingCenter(empty, 11),
                        formatStingCenter(empty, 11), formatStingCenter(empty, 22),
                        formatStingCenter(empty, 34)));
            }
            writer.flush();
        }
        writer.write("--------------------------------------------------------------------------------------" +
                "----------------------------\n");
        writer.flush();
    }

    private void printHeader() throws IOException {
        writer.write("Выбор аппроксимирующей функции\n");
        writer.flush();
        writer.write("----------------------------------------------------------------------------------------" +
                "--------------------------\n");
        writer.flush();
        writer.write("|    Вид функции   |     a     |     b     |     c     |   Мера отклонения S  |" +
                "  Среднеквадратичное отклонение δ |\n");
        writer.flush();
        writer.write("----------------------------------------------------------------------------------------" +
                "--------------------------\n");
        writer.flush();
    }
}
