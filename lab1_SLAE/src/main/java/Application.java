import DateStructure.Algorithm;
import DateStructure.LinearSystemOfEquations;
import Exceptions.NotExistingVariant;
import Readers.Reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        Reader reader = new Reader();
        System.out.println("Программа для решения системы линейных алгебраических уравнений СЛАУ методом Гаусса\n");
        boolean type = false;
        while (!type) {
            try {
                System.out.println("Выберите источник ввода коэффициентов уравнений:" +
                        "\n1 - с консоли" +
                        "\n2 - из файла");
                int i = Integer.parseInt(scanner.readLine().trim());
                switch (i) {
                    case 1:
                        break;
                    case 2:
                        System.out.println("Введите путь к файлу. При вводе Enter будет считан файл, указанный по умолчанию");
                        String filepath = scanner.readLine().trim();
                        if (filepath.equals("")) {
                            filepath = "C:\\Users\\olesy\\Documents\\STUDY\\Вычислительная_математика\\lab1\\src\\main\\resources\\input";
                        }
                        scanner = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));;
                        break;
                    default:
                        throw new NotExistingVariant();
                }
                type = true;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод данных. Повторите попытку");
            } catch (NotExistingVariant e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LinearSystemOfEquations linearSystem = reader.fill(scanner);
        if (linearSystem != null) {
            Algorithm algorithm = new Algorithm(linearSystem);
            algorithm.solve();
        }
    }
}
