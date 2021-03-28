package Exceptions;

public class NotExistingVariant extends RuntimeException {
    public NotExistingVariant() {
        super("Введенный вами вариант не доступен, выберите число из предложенного списка");
    }
}
