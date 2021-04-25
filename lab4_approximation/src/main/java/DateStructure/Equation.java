package DateStructure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BinaryOperator;

@NoArgsConstructor
public class Equation implements MethodsExecutable {

    @Getter
    private List<Double> listOfCoefficient = new ArrayList<>();
    @Setter
    @Getter
    private Double equalValue;

    public Equation(List<Double> list, Double equalValue) {
        listOfCoefficient = list;
        this.equalValue = equalValue;
    }

    public void multiply(Double a) {
        ListIterator<Double> iter = listOfCoefficient.listIterator();
        while (iter.hasNext()) {
            Double current = iter.next();
            iter.set(current * a);
        }
        equalValue *= a;
    }

    public void add(MethodsExecutable a) {
        ListIterator<Double> iter = getIterator();
        BinaryOperator<Double> function = (x1, x2) -> x1 + x2;
        a.getListOfCoefficient().forEach(d -> {
                iter.set(function.apply(d, iter.next()));
        });
        equalValue += a.getEqualValue();
    }

    public int size() {
        return listOfCoefficient.size();
    }

    public Double getCoefficient(int a) {
        return listOfCoefficient.get(a);
    }

    public void setCoefficient(int a, Double value) {
        listOfCoefficient.set(a, value);
    }

    public ListIterator<Double> getIterator() {
        return listOfCoefficient.listIterator();
    }

    public boolean equalsToZero() {
        Double sum = 0d;
        for (Double a: listOfCoefficient) {
            sum += a;
        }
        return sum == 0;
    }

    @Override
    public String toString() {
        String result = "";
        for (Double a: listOfCoefficient) {
            result += a + " ";
        }
        result += "| " + equalValue;
        return result;
    }
}
