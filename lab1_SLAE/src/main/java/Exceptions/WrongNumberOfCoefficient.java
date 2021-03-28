package Exceptions;

public class WrongNumberOfCoefficient extends RuntimeException {
    public WrongNumberOfCoefficient(int n) {
        super("Неверное количество чисел в строке. Количество коэффициентов должно быть " + n);
    }
}
