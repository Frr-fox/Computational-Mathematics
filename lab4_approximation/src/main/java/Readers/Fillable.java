package Readers;

import DateStructure.Point;

import java.io.BufferedReader;
import java.util.ArrayList;

@FunctionalInterface
public interface Fillable {
    ArrayList<Point> fill(BufferedReader reader, boolean readFromConsole);
}
