package DateStructure;

import java.util.List;
import java.util.ListIterator;

public interface MethodsExecutable {
    void multiply(Double a);
    void add(MethodsExecutable a);
    int size();
    Double getCoefficient(int a);
    void setCoefficient(int a, Double value);
    Double getEqualValue();
    void setEqualValue(Double a);
    ListIterator<Double> getIterator();
    List<Double> getListOfCoefficient();
    boolean equalsToZero();
}
