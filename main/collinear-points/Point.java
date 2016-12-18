import java.util.Comparator;

import static java.lang.Double.*;
import static java.util.Comparator.comparingDouble;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {

    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {

    }

    // string representation
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    @Override
    public int compareTo(Point that) {
        if (y == that.y && x == that.x) {
            return 0;
        }
        if (y < that.y || (y == that.y && x < that.x)) {
            return -1;
        }
        return 1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (that.x - x == 0) {
            return that.y - y == 0 ? NEGATIVE_INFINITY : POSITIVE_INFINITY;
        }
        if (that.y - y == 0) {
            return 0d;
        }
        return (double) (that.y - y) / (that.x - x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return comparingDouble(this::slopeTo);
    }

    public static void main(String[] args) {
        assert new Point(64, 398).slopeTo(new Point(343, 283)) == -0.4121863799283154d;
        assert new Point(400, 329).slopeTo(new Point(400, 329)) == NEGATIVE_INFINITY;
        assert new Point(428, 363).slopeTo(new Point(18, 363)) == 0;
        assert new Point(6, 9).compareTo(new Point(5, 9)) == 0;
        assert new Point(32614, 4089).compareTo(new Point(13890, 4089)) == new Point(13890, 4089).compareTo(new Point(32614, 4089)) * -1;
    }
}