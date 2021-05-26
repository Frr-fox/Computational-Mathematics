package DataStructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Point {
    @Getter
    double x;
    @Getter
    double y;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            Point newPoint = (Point) obj;
            return (newPoint.getX() == this.getX() && newPoint.getY() == this.getY());
        } else return false;
    }
}
