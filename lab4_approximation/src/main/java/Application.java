import DateStructure.Point;
import Dependencies.FunctionalDependence;
import Exceptions.NotExistingVariant;
import Readers.Reader;

import java.io.*;
import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws IOException {
        BufferedReader scannerText = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        Reader reader = new Reader();
        boolean readFromConsole = true, writeToConsole = true;
        writer.write("Программа для аппроксимации функции методом наименьших квадратов\n\n");
        boolean type = false;
        while (!type) {
            try {
                writer.write("Выберите источник ВВОДА исходных данных:" +
                        "\n1 - с консоли" +
                        "\n2 - из файла\n");
                writer.write("Ваш выбор: ");
                writer.flush();
                int i = Integer.parseInt(scannerText.readLine().trim());
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        writer.write("Введите путь к файлу. При вводе Enter будет считан файл, указанный по умолчанию: ");
                        writer.flush();
                        String filepath = scannerText.readLine().trim();
                        if (filepath.equals("")) {
                            filepath = "C:\\Users\\olesy\\Documents\\STUDY\\Computational_Mathematics\\lab4_approximation\\src\\main\\resources\\input";
                        }
                        scanner = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
                        readFromConsole = false;
                        break;
                    default:
                        throw new NotExistingVariant();
                }
                type = true;
            } catch (NumberFormatException e) {
                writer.write("Некорректный ввод данных. Повторите попытку\n");
                writer.flush();
            } catch (NotExistingVariant e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            } catch (FileNotFoundException e) {
                writer.write("Не удается найти указанный файл\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.flush();
        type = false;
        while (!type) {
            try {
                writer.write("Выберите способ ВЫВОДА результата:" +
                        "\n1 - в консоль" +
                        "\n2 - в файл\n");
                writer.write("Ваш выбор: ");
                writer.flush();
                int i = Integer.parseInt(scannerText.readLine().trim());
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        writer.write("Введите путь к файлу. При вводе Enter запись будет производиться в файл, указанный по умолчанию: ");
                        writer.flush();
                        String filepath = scannerText.readLine().trim();
                        if (filepath.equals("")) {
                            filepath = "C:\\Users\\olesy\\Documents\\STUDY\\Computational_Mathematics\\lab4_approximation\\src\\main\\resources\\output";
                        }
                        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath)));
                        writeToConsole = false;
                        break;
                    default:
                        throw new NotExistingVariant();
                }
                type = true;
                writer.flush();
            } catch (NumberFormatException e) {
                writer.write("Некорректный ввод данных. Повторите попытку\n");
                writer.flush();
            } catch (NotExistingVariant e) {
                writer.write(e.getMessage() + "\n");
                writer.flush();
            } catch (FileNotFoundException e) {
                writer.write("Не удается найти указанный файл\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Point> points = reader.fill(scanner, readFromConsole);
        if (points == null) return;
        FunctionalDependence functionalDependence = new FunctionalDependence(points, writer);
        functionalDependence.runDifferentWaysOfApproximation();
        if (!writeToConsole) {
            System.out.println("Результаты записаны в файл\n");
        }
        writer.close();
        scanner.close();
        scannerText.close();
    }
}
