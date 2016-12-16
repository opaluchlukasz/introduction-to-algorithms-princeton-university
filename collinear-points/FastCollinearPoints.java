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

        List<String> alreadySaved = new LinkedList<>();

        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(copy);
            Point current = copy[i];

            Arrays.sort(copy, current.slopeOrder());

            for (int j = 1; j < copy.length - 2; j++) {
                // first point is always the point for which sorted, since slope from same point gives minus infinity
                // we need to find at least three more points with same slope value
                double firstSlope = copy[0].slopeTo(copy[j]);
                int k = 0;
                while (j < copy.length - 1 && firstSlope == copy[j].slopeTo(copy[j + 1])) {
                    j++;
                    k++;
                }
                if (k >= 2) {
                    LinkedList<Point> segments = new LinkedList<>();
                    segments.add(copy[0]);
                    for (int l = 0; l <= k; l++) {
                        segments.add(copy[j - l]);
                    }

                    segments.sort(Point::compareTo);
                    LineSegment lineSegment = new LineSegment(segments.getFirst(), segments.getLast());
                    if (!alreadySaved.contains(lineSegment.toString())) {
                        alreadySaved.add(lineSegment.toString());
                        lineSegments.add(lineSegment);
                    }
                    break;
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

        fastCollinearPoints = new FastCollinearPoints(new Point[]{
                new Point(5, 5),
                new Point(2, 8),
                new Point(5, 3),
                new Point(1, 9),
                new Point(6, 0),
                new Point(8, 5),
                new Point(5, 2),
                new Point(1, 5),
                new Point(3, 7),
                new Point(5, 6)
        });
        assert fastCollinearPoints.numberOfSegments() == 2;
    }
}
