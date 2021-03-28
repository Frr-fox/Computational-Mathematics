package Exceptions;

public class TooManyEquations extends RuntimeException {
    public TooManyEquations() {
        super("Количество уравнений не может превышать 20");
    }
}
