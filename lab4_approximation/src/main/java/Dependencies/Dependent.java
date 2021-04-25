package Dependencies;

import DateStructure.Point;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.function.UnaryOperator;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public abstract class Dependent {
    protected String dependencyName;
    @Setter
    @Getter
    protected String dependencyFunction;

    protected ArrayList<Point> points;
    protected int numberOfPoints;

    protected Double[] coefficients;
    @Getter
    protected UnaryOperator<Double> function;
    double deviation = 0;
    @Getter
    double rootMeanSquareDeviation;

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

    private void center(String fmtStr, Formatter fmt, Object obj, int width) throws IOException {
        String str;
        try {
            Formatter tmp = new Formatter();
            tmp.format(fmtStr, obj);
            str = tmp.toString();
        } catch(IllegalFormatException exc) {
            writer.write("Неверный запрос формата\n");
            writer.flush();
            fmt.format("");
            return;
        }
        int dif = width - str.length();
        if(dif < 0) {
            fmt.format(str);
            return;
        }
        char[] pad = new char[dif/2];
        Arrays.fill(pad, ' ');
        fmt.format(new String(pad));
        fmt.format(str);
        pad = new char[width-dif/2-str.length()];
        Arrays.fill(pad, ' ');
        fmt.format(new String(pad));
    }
}
