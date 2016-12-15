import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> lineSegments = new LinkedList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Point[] copy = points.clone();
        Arrays.sort(copy);

        assertNoDuplicates(copy);

        List<Point> partials = new LinkedList<>();

        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(copy);
            Point current = copy[i];

            if (partials.stream().noneMatch(point -> point.compareTo(current) == 0)) {
                Arrays.sort(copy, copy[i].slopeOrder());

                for (int j = 1; j < copy.length - 2; j++) {
                    // first point is always the point for which sorted, since slope from same point gives minus infinity
                    // we need to find three more point with same slope value
                    double firstSlope = copy[0].slopeTo(copy[j]);
                    double secondSlope = copy[j].slopeTo(copy[j + 1]);
                    double thirdSlope = copy[j + 1].slopeTo(copy[j + 2]);
                    if(Double.compare(firstSlope, secondSlope) == 0 &&
                            Double.compare(firstSlope, thirdSlope) == 0) {
                        partials.add(copy[0]);
                        partials.add(copy[j]);
                        partials.add(copy[j + 1]);
                        partials.add(copy[j + 2]);

                        Point[] segment = new Point[] {copy[0], copy[j], copy[j + 1], copy[j + 2]};
                        Arrays.sort(segment);
                        lineSegments.add(new LineSegment(segment[0], segment[3]));
                        break;
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    private static void assertNoDuplicates(Point[] copy) {
        for (int i = 0; i < copy.length - 1; i++) {
            if (copy[i].compareTo(copy[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicates found");
            }
        }
    }

    public static void main(String[] args) {
        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(new Point[]{
                new Point(19304, 1901),
                new Point(6017, 8990),
                new Point(12323 , 1901),
                new Point(19989 , 8990),
                new Point(1392 , 3377),
                new Point(9931, 14850),
                new Point(6319 , 8990),
                new Point(4855 , 1901),
                new Point(2898 , 3377),
                new Point(11318 , 2580),
                new Point(7074 , 8990),
                new Point(11028 , 2580),
                new Point(6175 , 2580),
                new Point(3034 , 1901),
                new Point(11033, 14850),
                new Point(17583, 14850),
                new Point(16139, 2580),
                new Point(8594, 3377),
                new Point(16761 , 3377),
                new Point(3030, 14850)});
        assert fastCollinearPoints.numberOfSegments() == 5;
    }
}
