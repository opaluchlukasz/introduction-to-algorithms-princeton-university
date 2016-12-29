import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import spock.lang.Specification

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class KdTreeTest extends Specification {

    def 'should return true for empty set'() {
        expect:
        new KdTree().empty
    }

    def 'should return false for non-empty set'() {
        given:
        KdTree kdTree = new KdTree()
        kdTree.insert(new Point2D(1, 1))

        expect:
        !kdTree.empty
    }

    def 'should return whether tree contains point'() {
        given:
        KdTree kdTree = new KdTree()
        [new Point2D(0.5, 0.5), new Point2D(0.1, 0.1), new Point2D(0.5, 0.1), new Point2D(0.1, 0.5),
         new Point2D(0.2, 0.4), new Point2D(0.3, 0.5)].forEach { kdTree.insert(it) }

        expect:
        kdTree.contains(point) == expected

        where:
        point                 | expected
        new Point2D(0.5, 0.5) | true
        new Point2D(0.1, 0.1) | true
        new Point2D(0, 0)     | false
        new Point2D(0.5, 0.1) | true
        new Point2D(0.1, 0.5) | true
        new Point2D(0.2, 0.4) | true
        new Point2D(0.3, 0.5) | true
    }

    def 'should contain inserted points'() {
        given:
        KdTree kdTree = new KdTree()
        def points = [new Point2D(0.9723019211910015, 0.33557429634170577),
                     new Point2D(0.8013638213168075, 0.429910816828146),
                     new Point2D(0.06293491389193273, 0.47927145727592557),
                     new Point2D(0.40192475359071134, 0.5632286148614287),
                     new Point2D(0.8990826383852947, 0.7491069587689919),
                     new Point2D(0.42250925581784704, 0.1621354257752713),
        ]
        points.forEach { kdTree.insert(it) }

        expect:
        points.each {
            assert kdTree.contains(it)
        }
    }

    def 'should return points within rectangle'(List<Point2D> points, List<Point2D> expected) {
        given:
        KdTree kdTree = new KdTree()
        points.forEach { kdTree.insert(it) }

        when:
        Iterable<Point2D> within = kdTree.range(new RectHV(0, 0, 0.5, 0.5))

        then:
        expect within, containsInAnyOrder(expected.toArray())

        where:
        points                                           | expected
        []                                               | []
        [new Point2D(2, 2)]                              | []
        [new Point2D(-1, -1), new Point2D(1, 1)]         | []
        [new Point2D(0, 0), new Point2D(0, 0.5),
         new Point2D(0.5, 0), new Point2D(0.5, 0.5)]     | [new Point2D(0, 0), new Point2D(0, 0.5),
                                                            new Point2D(0.5, 0), new Point2D(0.5, 0.5)]
        [new Point2D(0, 0), new Point2D(0, 0.5),
         new Point2D(0.5, 0), new Point2D(0.5, 0.5)]     | [new Point2D(0, 0), new Point2D(0, 0.5),
                                                            new Point2D(0.5, 0), new Point2D(0.5, 0.5)]
        [new Point2D(0.25, 0.25), new Point2D(0.1, 0.1),
         new Point2D(0.51, 0.1), new Point2D(0.1, 0.51)] | [new Point2D(0.25, 0.25), new Point2D(0.1, 0.1)]
    }

    def 'should return nearest point'(List<Point2D> points, Point2D expected) {
        given:
        KdTree kdTree = new KdTree()
        points.forEach { kdTree.insert(it) }

        when:
        Point2D nearest = kdTree.nearest(new Point2D(0.5, 0.5))

        then:
        nearest == expected

        where:
        points                                     | expected
        []                                         | null
        [new Point2D(1, 1)]                        | new Point2D(1d, 1d)
        [new Point2D(0.5, 0.5), new Point2D(1, 1)] | new Point2D(0.5, 0.5)
        [new Point2D(0.1, 0.1), new Point2D(0.2, 0.3),
         new Point2D(0.6, 0.6)]                    | new Point2D(0.6, 0.6)
    }
}
