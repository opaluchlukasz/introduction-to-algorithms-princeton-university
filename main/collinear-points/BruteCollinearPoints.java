import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lineSegments = new LinkedList<>();

    // finds all line segments containing 4 or more points
    public BruteCollinearPoints(Point[] points) {
        Point[] copy = points.clone();
        Arrays.sort(copy);

        assertNoDuplicates(copy);

        for (int i = 0; i < copy.length - 3; i++) {
            for (int j = i + 1; j < copy.length - 2; j++) {
                Point first = copy[i];
                Point second = copy[j];
                double slopeIJ = first.slopeTo(second);
                for (int k = j + 1; k < copy.length - 1; k++) {
                    Point third = copy[k];
                    double slopeJK = second.slopeTo(third);
                    if(slopeIJ == slopeJK) {
                        for (int l = k + 1; l < copy.length; l++) {
                            Point forth = copy[l];
                            double slopeKL = third.slopeTo(forth);
                            if(slopeIJ == slopeKL) {
                                lineSegments.add(new LineSegment(first, forth));
                            }
                        }
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
        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(new Point[]{
                new Point(19304 , 1901),
                new Point(6017 , 8990),
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
                new Point(16139 , 2580),
                new Point(8594, 3377),
                new Point(16761 , 3377),
                new Point(3030, 14850)});
        assert bruteCollinearPoints.numberOfSegments() == 5;
    }
}
