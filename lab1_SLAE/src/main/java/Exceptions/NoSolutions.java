package Exceptions;

public class NoSolutions extends RuntimeException {
    public NoSolutions() {
        super("Система не имеет решений");
    }
}
