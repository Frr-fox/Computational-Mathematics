package DateStructure;

import Exceptions.CantCastToTriangle;
import Exceptions.NoSolutions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class LinearSystemOfEquations {
    @Getter
    private List<MethodsExecutable> listOfEquations = new ArrayList<>();
    @Getter
    private final int size;

    public LinearSystemOfEquations(ArrayList<MethodsExecutable> list) {
        listOfEquations = list;
        this.size = list.size();
    }

    public LinearSystemOfEquations(int a) {
        this.size = a;
    }

    public void addEquation(MethodsExecutable equation) {
        listOfEquations.add(equation);
    }

    public int size() {
        return listOfEquations.size();
    }

    public Double getElement(int i, int j) {
        return listOfEquations.get(i).getCoefficient(j);
    }

    public void setElement(int i, int j, Double value) {
        Equation oldEquation = (Equation) listOfEquations.get(i);
        oldEquation.setCoefficient(j, value);
        listOfEquations.set(i, oldEquation);
    }

    private void swapEquations(int i, int k) {
        MethodsExecutable newOne = new Equation(new ArrayList<>(getListOfEquations().get(i).getListOfCoefficient()), getListOfEquations().get(i).getEqualValue());
        getListOfEquations().set(i, getListOfEquations().get(k));
        getListOfEquations().set(k, newOne);
    }

    public Double[] solve() {
        Double[] x = new Double[size];
        for (int i = 0; i < size; i++) {
            x[i] = 0d;
        }
        try {
            castToTriangleMatrix();
            int i, j;
            for (i = size - 1; i >= 0; i--) {
                double sum = 0d;
                for (j = size - 1; j > i; j--) {
                    sum += getElement(i, j) * x[j];
                }
                x[i] = (getListOfEquations().get(i).getEqualValue() - sum)/getElement(i, j);
            }
            printVectorOfUnknowns(x);
            return x;
        } catch (CantCastToTriangle e) {
            System.out.println("Система не имеет решений или имеет бесконечное множество решений");
            return null;
        } catch (NoSolutions e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Double calculateDeterminant() {
        try {
            int countOfSwap = castToTriangleMatrix();
            System.out.println("Количество перестановок " + countOfSwap);
            Double determinant = 1d;
            for (int i = 0; i < size; i++) {
                determinant *= getElement(i, i);
            }
            determinant *= Math.pow(-1, countOfSwap);
            return determinant;
        } catch (CantCastToTriangle | NoSolutions e) {
            return 0d;
        }
    }

    public void printDeterminant() {
        Double determinant = calculateDeterminant();
        System.out.println("Определитель матрицы равен " + determinant);
    }

//    private Double calculateMinor() {}

    private int castToTriangleMatrix() throws CantCastToTriangle, NoSolutions {
        boolean flag = false;
        int countOfSwap = 0;
        for (int i = 0; i < size - 1; i++) {
            if (getElement(i, i) == 0) {
                for (int k = i + 1; k < size; k++) {
                    if (getElement(k, i) != 0) {
                        swapEquations(i, k);
                        flag = true;
                        break;
                    }
                }
                if (!flag) throw new CantCastToTriangle();
                countOfSwap++;
            }
            for (int k = i + 1; k < size; k++) {
                Double c = -getElement(k, i) / getElement(i, i);
                MethodsExecutable newOne = new Equation(new ArrayList<>(getListOfEquations().get(i).getListOfCoefficient()),
                        getListOfEquations().get(i).getEqualValue());
                newOne.multiply(c);
                getListOfEquations().get(k).add(newOne);
            }
        }
        for (int i = 0; i < size; i++) {
            if (listOfEquations.get(i).equalsToZero() && listOfEquations.get(i).getEqualValue() != 0) throw new NoSolutions();
        }
        return countOfSwap;
    }

    public void printTriangleMatrix() {
        try {
            printDeterminant();
            System.out.println("\nВывод получившейся треугольной матрицы:");
            printMatrix();
        } catch (CantCastToTriangle | NoSolutions e) {
//            printDeterminant();
        }
    }

    public void printVectorOfUnknowns(Double[] x) {
        System.out.println("Решение системы: ");
        for (int i = 0; i < size; i++) {
            System.out.println("x[" + (i + 1) +"] = " + x[i]);
        }
    }

    public void printResidualVector(Double[] x) {
        Double[] r = new Double[size];
        for (int i = 0; i < size; i++) {
            Double sum = 0d;
            for (int j = 0;  j < size; j++) {
                sum += listOfEquations.get(i).getListOfCoefficient().get(j)*x[j];
            }
            r[i] = getListOfEquations().get(i).getEqualValue() - sum;
        }
        System.out.println("Вектор невязок: ");
        for (int i = 0; i < size; i++) {
            System.out.println("r[" + (i + 1) +"] = " + r[i]);
        }
    }

    public void printBasicMatrix() {
        System.out.println("\nВывод изначальной матрицы: ");
        printMatrix();
    }

    private void printMatrix() {
        for (MethodsExecutable equation: listOfEquations) {
            for (Double k: equation.getListOfCoefficient()) {
                System.out.printf("%10.2e", k);
            }
            System.out.printf(" | %10.2e", equation.getEqualValue());
            System.out.println();
        }
    }

    public void clear() {
        this.listOfEquations.clear();
    }
}
