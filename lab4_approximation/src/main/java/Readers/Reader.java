package Readers;

import DateStructure.Point;
import Exceptions.NotUniquePoint;
import Exceptions.TooLittleData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader implements Fillable {

    @Override
    public ArrayList<Point> fill(BufferedReader scanner, boolean readFromConsole) {
        int n = 0;
        boolean type = false;
        int minNumber = 12;
        while (!type) {
            try {
                System.out.printf("Введите количество точек (не менее %d): ", minNumber);
                n = Integer.parseInt(scanner.readLine().trim());
                if (n < minNumber) throw new TooLittleData(minNumber);
                type = true;
            } catch (NumberFormatException | IOException | NullPointerException e) {
                if (readFromConsole) {
                    System.out.println("Введенные значения некорректны. Повторите попытку");
                } else {
                    System.out.println("Данные в файле некорректны");
                    return null;
                }
            } catch (TooLittleData e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Введите значения x и y через пробел (например, 1.3 5)");
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            type = false;
            while (!type) {
                try {
                    System.out.print("Точка №" + i + ": ");
                    String line1 = scanner.readLine().trim();
                    line1 = line1.replaceAll("\\s+", " ").replaceAll(",", ".");
                    String[] line = line1.split(" ");
                    if (line.length != 2) throw new NumberFormatException();
                    Point newPoint = new Point(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
                    if (!checkUniquePoints(points, newPoint)) throw new NotUniquePoint();
                    points.add(newPoint);
                    type = true;
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Введенные вами значения некорректны. Повторите попытку");
                } catch (NotUniquePoint e) {
                    System.out.println(e.getMessage());
                } catch (NullPointerException e) {
                    System.out.println("Данные в файле некорректны");
                    return null;
                }
            }
        }
        System.out.println("\n");
        return points;
    }

    private boolean checkUniquePoints(ArrayList<Point> points, Point newPoint) {
        for (Point point : points) {
            if (newPoint.equals(point)) return false;
        }
        return true;
    }
}
