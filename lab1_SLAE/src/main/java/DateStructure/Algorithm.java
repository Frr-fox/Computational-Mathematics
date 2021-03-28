package DateStructure;

import java.util.ArrayList;

public class Algorithm {
    LinearSystemOfEquations basicLinearSystem;

    public Algorithm(LinearSystemOfEquations linearSystem) {
        this.basicLinearSystem = linearSystem;
    }

    public void solve(){
        basicLinearSystem.printBasicMatrix();
        LinearSystemOfEquations linearSystem = new LinearSystemOfEquations(new ArrayList<>(basicLinearSystem.getListOfEquations()));
        linearSystem.printTriangleMatrix();
        Double[] x = linearSystem.solve();
        if (x != null) {
            basicLinearSystem.printResidualVector(x);
        }
    }
}
