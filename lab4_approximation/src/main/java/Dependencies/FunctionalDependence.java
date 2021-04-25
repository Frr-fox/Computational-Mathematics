package Dependencies;

import DateStructure.Point;
import Utils.DrawingGraph;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
            dependency.buildDependency();
            dependency.createDependencyFunction();
            dependency.printTable();
            dependency.printAddingCharacters();
            drawing.drawDependency(dependency);
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
        writer.write(String.format("| φ = ax + b       |%11.3f|%11.3f|%11s|%22.3f|%34.3f|\n", dependencies.get(0).coefficients[0],
                dependencies.get(0).coefficients[1], "     -     ", dependencies.get(0).deviation,
                dependencies.get(0).getRootMeanSquareDeviation()));
        writer.flush();
        writer.write(String.format("| φ = ax² + bx + c |%11.3f|%11.3f|%11.3f|%22.3f|%34.3f|\n", dependencies.get(1).coefficients[0],
                dependencies.get(1).coefficients[1], dependencies.get(1).coefficients[2], dependencies.get(1).deviation,
                dependencies.get(1).getRootMeanSquareDeviation()));
        writer.flush();
        writer.write(String.format("| φ = aeᵇᵡ         |%11.3f|%11.3f|%11s|%22.3f|%34.3f|\n", dependencies.get(2).coefficients[0],
                dependencies.get(2).coefficients[1], "     -     ", dependencies.get(2).deviation,
                dependencies.get(2).getRootMeanSquareDeviation()));
        writer.flush();
        writer.write(String.format("| φ = a ln(x) + b  |%11.3f|%11.3f|%11s|%22.3f|%34.3f|\n", dependencies.get(3).coefficients[0],
                dependencies.get(3).coefficients[1], "     -     ", dependencies.get(3).deviation,
                dependencies.get(3).getRootMeanSquareDeviation()));
        writer.flush();
        writer.write(String.format("| φ = axᵇ          |%11.3f|%11.3f|%11s|%22.3f|%34.3f|\n", dependencies.get(4).coefficients[0],
                dependencies.get(4).coefficients[1], "     -     ", dependencies.get(4).deviation,
                dependencies.get(4).getRootMeanSquareDeviation()));
        writer.flush();
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
