import DataStructure.Point;
import Methods.Lagrange;
import Methods.Newton;
import Utils.Graph;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.UnaryOperator;

import static java.lang.Math.*;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /* Выбор истояника исходных данных */
        System.out.println("Программа для решения задачи интерполяции функции");
        boolean check = false;
        int chosenMethod = 0;
        while (!check) {
            try {
                System.out.println("Выберите источник исходных данных:\n" +
                        "1) Набор данных (таблица x,y)\n" +
                        "2) На основе функций");
                System.out.print("Ваш выбор: ");
                String line = scanner.nextLine();
                if (!line.equals("")) {
                    chosenMethod = Integer.parseInt(line.trim());
                }
                if (chosenMethod < 1 || chosenMethod > 2) {
                    throw new IllegalArgumentException();
                }
                check = true;
            } catch (IllegalArgumentException e) {
                System.out.print("Введенные данные некорректы. Введите число от 1 до 2\n");
            }
        }
        double xValue = 0.0;
        ArrayList<Point> data = new ArrayList<>();
        if (chosenMethod == 1) {
            data = fillData();
            xValue = getXValue();
        } else {
            check = false;
            int chosenFunction = 0;
            while (!check) {
                try {
                    System.out.println("Выберите исходную функцию:\n" +
                            "1) sqrt(x)\n" +
                            "2) sin(x)\n" +
                            "3) x^3 + x^2 + 3\n" +
                            "4)e^x\n");
                    System.out.print("Ваш выбор: ");
                    String line = scanner.nextLine();
                    if (!line.equals("")) {
                        chosenFunction = Integer.parseInt(line.trim());
                    }
                    if (chosenFunction < 1 || chosenFunction > 4) {
                        throw new IllegalArgumentException();
                    }
                    check = true;
                } catch (IllegalArgumentException e) {
                    System.out.print("Введенные данные некорректы. Введите число от 1 до 4\n");
                }
            }
            UnaryOperator<Double> function;
            switch (chosenFunction) {
                case 1:
                    function = (x) -> sqrt(x);
                    break;
                case 2:
                    function = (x) -> sin(x);
                    break;
                case 3:
                    function = (x) -> pow(x, 3) + pow(x, 2) + 3;
                    break;
                case 4:
                    function = (x) -> exp(x);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + chosenFunction);
            }
            xValue = getXValue();
            double i = round(function.apply(xValue));
            int t = 0;
            for (double j = i + 0.1; t < 10; j += (xValue + 0.1) * 0.2, t++) {
                if (j == 0) continue;
                data.add(new Point(j, function.apply(j)));
            }
        }
        Lagrange lagrange = new Lagrange(data);
        Newton newton = new Newton(data);
        double l = lagrange.calculate(xValue);
        lagrange.printStringResult(xValue);
        double n = newton.calculate(xValue);
        newton.printStringResult(xValue);
        Graph graph = new Graph(data);
        graph.drawMainFrames();
        graph.drawDependencies(lagrange, newton);
        graph.showGraphs();
    }

    private static ArrayList<Point> fillData()  {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Point> data = new ArrayList();
        boolean check = false;
        int i = 0;
        while (!check) {
            try {
                System.out.println("Введите значения координаты точек в формате: x y\n" +
                        "Для окончания ввода нажмите Enter");
                System.out.printf("Точка №%d: ", i+1);
                String line = scanner.nextLine();
                if (line.equals("")) throw new IllegalArgumentException();
                while (!line.equals("")) {
                    String[] boarders = line.trim().replaceAll(" +", " ").split(" ");
                    if (boarders.length != 2) throw new IllegalArgumentException();
                    double x = Double.parseDouble(boarders[0].replaceAll(",", "."));
                    double y = Double.parseDouble(boarders[1].replaceAll(",", "."));
                    Point newPoint = new Point(x, y);
                    if (!checkUniquePoints(data, newPoint)) throw new NotUniquePoint();
                    data.add(i, newPoint);
                    i++;
                    System.out.printf("Точка №%d: ", i+1);
                    line = scanner.nextLine();
                    if (line.equals("") && i < 2) throw new IllegalArgumentException();
                }
                check = true;
            } catch (NotUniquePoint e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Введенные данные некорректы");
            }
        }
        return data;
    }

    private static boolean checkUniquePoints(ArrayList<Point> points, Point newPoint) {
        for (Point point : points) {
            if (newPoint.equals(point)) return false;
            if (newPoint.getX() == point.getX()) return false;
        }
        return true;
    }

    private static double getXValue() {
        /* Ввод значения аргумента */
        Scanner scanner = new Scanner(System.in);
        boolean check = false;
        double xValue = 0.0;
        while (!check) {
            try {
                System.out.print("Введите значение аргумента: ");
                String line = scanner.nextLine();
                if (line == "") throw new IllegalArgumentException();
                xValue = Double.parseDouble(line.trim());
                check = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Введенные данные некорректы");
            }
        }
        return xValue;
    }
}

