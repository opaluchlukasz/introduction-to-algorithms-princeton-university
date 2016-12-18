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

        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(copy);
            Point current = copy[i];

            Arrays.sort(copy, current.slopeOrder());

            int start = 1;
            for (int end = 2; end < copy.length; end++) {
                // first point is always the point for which sorted, since slope from same point gives minus infinity
                // we need to find at least three more points with same slope value
                double firstSlope = copy[0].slopeTo(copy[start]);

                while (end < copy.length && firstSlope == copy[start].slopeTo(copy[end])) {
                    end++;
                }
                if (end - start >= 3) {
                    if(copy[0].compareTo(copy[start]) < 0) {
                        // We checked that considered point is less than first found point, so it is first point of the segment
                        // and since sorting is stable we can use last found point as the end of the segment
                        // This also protects us from saving same segment more than once
                        lineSegments.add(new LineSegment(copy[0], copy[end - 1]));
                    }
                }
                // looking for another set of collinear points (omitting already processed)
                start = end;
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

        FastCollinearPoints fastCollinearPoints3 = new FastCollinearPoints(new Point[]{
                new Point(10000 ,0),
                new Point(8000 ,2000),
                new Point(2000 ,8000),
                new Point(0  ,10000),
                new Point(20000 ,0),
                new Point(18000 ,2000),
                new Point(2000 ,18000),
                new Point(10000 ,20000),
                new Point(30000 ,0),
                new Point(0 ,30000),
                new Point(20000 ,10000),
                new Point(13000 ,0),
                new Point(11000 ,3000),
                new Point(5000 ,12000),
                new Point(9000 ,6000)
        });
        assert fastCollinearPoints3.numberOfSegments() == 4;
    }
}
