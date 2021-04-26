package Dependencies;

import DateStructure.Point;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.function.UnaryOperator;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static Utils.StringFormatter.center;

public abstract class Dependent {
    protected String dependencyName;
    @Setter
    @Getter
    protected String dependencyFunction;
    @Setter
    @Getter
    protected boolean isExist = true;

    protected ArrayList<Point> points;
    protected int numberOfPoints;

    protected Double[] coefficients;
    @Getter
    protected UnaryOperator<Double> function;
    double deviation = 1000;
    @Getter
    double rootMeanSquareDeviation = 1000;

    protected BufferedWriter writer;

    abstract void buildDependency();
    abstract void createDependencyFunction();
    protected void printAddingCharacters() throws IOException {}

    protected void calculateDeviation(ArrayList<Point> points) {
        double dev = 0;
        for (Point point: points) {
            dev += pow(point.getY() - function.apply(point.getX()), 2);
        }
        this.deviation = dev;
    }

    protected void calculateRootMeanSquareDeviation(int numberOfPoints) {
        this.rootMeanSquareDeviation = sqrt(deviation / numberOfPoints);
    }

    private StringBuilder printHeader() throws IOException {
        writer.write(dependencyName);
        writer.write("\n");
        writer.flush();
        Formatter header = new Formatter();
        header.format("|");
        center("%s", header, " ", 25);
        header.format("|");
        StringBuilder dividingLine = new StringBuilder("---------------------------");
        for (int i = 1; i <= numberOfPoints; i++) {
            center("%s", header, i, 8);
            header.format("|");
            dividingLine.append("---------");
        }
        header.format("\n");
        dividingLine.append("\n");
        writer.write(String.valueOf(dividingLine));
        writer.flush();
        writer.write(String.valueOf(header));
        writer.flush();
        writer.write(String.valueOf(dividingLine));
        writer.flush();
        return dividingLine;
    }

    private void printLine(String name, ArrayList<Double> data) throws IOException {
        Formatter line = new Formatter();
        line.format("|");
        center("%s", line, name, 25);
        line.format("|");
        for (int i = 0; i < numberOfPoints; i++) {
            line.format(String.format("%8.3f", data.get(i))).format("|");
        }
        line.format("\n");
        writer.write(String.valueOf(line));
        writer.flush();
    }

    protected void printTable() throws IOException {
        StringBuilder dividingLine = printHeader();
        ArrayList<Double> data = new ArrayList<>();
        points.forEach(p -> data.add(p.getX()));
        printLine("x", data);
        data.clear();
        points.forEach(p -> data.add(p.getY()));
        printLine("y", data);
        data.clear();
        points.forEach(p -> data.add(function.apply(p.getX())));
        printLine("φ = " + dependencyFunction, data);
        data.clear();
        points.forEach(p -> data.add(p.getY() - function.apply(p.getX())));
        printLine("ε", data);
        writer.write(String.valueOf(dividingLine));
        writer.flush();
        writer.write("\n");
        writer.flush();
    }
}
