import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;

public class PointSET {

    private Set<Point2D> points = new TreeSet<>();

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        if (!points.contains(point)) {
            points.add(point);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D point) {
        return points.contains(point);
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rectangle) {
        return points.stream().filter(rectangle::contains).collect(toList());
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D point) {
        Optional<Point2D> nearest = points.stream().sorted(comparingDouble(o -> o.distanceTo(point))).findFirst();
        return nearest.orElse(null);
    }
}
