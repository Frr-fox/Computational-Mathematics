package Readers;

import DateStructure.Equation;
import DateStructure.LinearSystemOfEquations;
import Exceptions.TooManyEquations;
import Exceptions.WrongNumberOfCoefficient;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Reader implements Fillable {

    @Override
    public LinearSystemOfEquations fill(BufferedReader scanner) {
        int n = 0;
        boolean type1 = false;
        while (!type1) {
            try {
                System.out.print("Введите количество уравнений в системе: ");
                n = Integer.parseInt(scanner.readLine().trim());
                if (n < 2) throw new NumberFormatException();
                if (n > 20) throw new TooManyEquations();
                type1 = true;
            } catch (NumberFormatException | IOException e) {
                System.out.println("Введенные вами значения некорректны. Повторите попытку");
            } catch (TooManyEquations e) {
                System.out.println(e.getMessage());
            }
        }
        LinearSystemOfEquations linearSystem = new LinearSystemOfEquations(n);
        boolean type = false;
        while (!type) {
            try {
                System.out.println("Введите коэффициенты уравнений: ");
                for (int i = 0; i < n; i++) {
                    ArrayList<Double> list = new ArrayList<>();
                    String line1 = scanner.readLine();
                    line1 = line1.replaceAll("\\s+", " ");
                    String[] line = line1.split(" ");
                    List<String> list1 = Arrays.asList(line);
                    list1.forEach(d -> list.add(Double.parseDouble(d)));
                    Double equalValue = 0d;
                    for (Double aDouble : list) {
                        equalValue = aDouble;
                    }
                    list.remove(list.size() - 1);
                    Equation equation = new Equation(list, equalValue);
                    if (equation.size() != linearSystem.getSize()) {
                        throw new WrongNumberOfCoefficient(n+1);
                    }
                    linearSystem.addEquation(equation);
                }
                type = true;
            } catch (NumberFormatException | IOException e) {
                System.out.println("Введенные вами значения некорректны. Повторите попытку");
                linearSystem.clear();
            } catch (WrongNumberOfCoefficient e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Данные в файле некорректны");
                return null;
            }
        }
        return linearSystem;
    }
}
