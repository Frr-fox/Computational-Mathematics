package Readers;

import DateStructure.Equation;
import DateStructure.LinearSystemOfEquations;

import java.io.BufferedReader;

@FunctionalInterface
public interface Fillable {
    LinearSystemOfEquations fill(BufferedReader reader);
}
