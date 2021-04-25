package Exceptions;

public class TooLittleData extends RuntimeException {
    public TooLittleData(int minNumber) {
        super("Количество точек должно быть не менее " + minNumber);
    }
}
