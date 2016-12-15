
public class LineSegment {
    private final Point p;
    private final Point q;

    // constructs the line segment between points p and q
    public LineSegment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    // draws this line segment
    public void draw() {

    }

    // string representation
    @Override
    public String toString() {
        return "LineSegment{" +
                "p=" + p +
                ", q=" + q +
                '}';
    }
}
