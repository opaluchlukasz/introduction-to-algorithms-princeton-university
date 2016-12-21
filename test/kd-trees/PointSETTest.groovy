import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import spock.lang.Specification

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class PointSETTest extends Specification {

    def 'should return true for empty set'() {
        expect:
        new PointSET().empty
    }

    def 'should return false for non-empty set'() {
        given:
        PointSET pointSET = new PointSET()
        pointSET.insert(new Point2D(1d, 1d))

        expect:
        !pointSET.empty
    }

    def 'should return points within rectangle'(List<Point2D> points, List<Point2D> expected) {
        given:
        PointSET pointSET = new PointSET()
        points.forEach { pointSET.insert(it) }

        when:
        Iterable<Point2D> within = pointSET.range(new RectHV(-1d, -1d, 1d, 1d))

        then:
        expect within, containsInAnyOrder(expected.toArray())

        where:
        points                                               | expected
        []                                                   | []
        [new Point2D(2d, 2d)]                                | []
        [new Point2D(-1.1d, -1.1d), new Point2D(1.1d, 1.1d)] | []
        [new Point2D(-1d, -1d), new Point2D(-1d, 1d),
         new Point2D(1d, -1d), new Point2D(1d, 1d)]          | [new Point2D(-1d, -1d), new Point2D(-1d, 1d),
                                                                new Point2D(1d, -1d), new Point2D(1d, 1d)]
        [new Point2D(-1d, 0d), new Point2D(0d, 1d),
         new Point2D(1d, 0d), new Point2D(0d, -1d)]          | [new Point2D(-1d, 0d), new Point2D(0d, 1d),
                                                                new Point2D(1d, 0d), new Point2D(0d, -1d)]
    }

    def 'should return nearest point'(List<Point2D> points, Point2D expected) {
        given:
        PointSET pointSET = new PointSET()
        points.forEach { pointSET.insert(it) }

        when:
        Point2D nearest = pointSET.nearest(new Point2D(1d, 1d))

        then:
        nearest == expected

        where:
        points                                       | expected
        []                                           | null
        [new Point2D(2d, 2d)]                        | new Point2D(2d, 2d)
        [new Point2D(1d, 1d), new Point2D(2d, 2d)]   | new Point2D(1d, 1d)
        [new Point2D(4d, 4d), new Point2D(-1d, -1d)] | new Point2D(-1d, -1d)
    }
}
