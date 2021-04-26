package Exceptions;

public class CannotBuildFunction extends RuntimeException {
    public CannotBuildFunction(String dependencyFunction) {super(dependencyFunction + " не существует в заданных границах\n");}
}
