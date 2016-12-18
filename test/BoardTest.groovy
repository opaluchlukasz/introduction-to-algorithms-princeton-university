import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class BoardTest extends Specification {

    @Subject Board board

    def 'should return false when board out of order'(int[][] blocks) {
        given:
        board = new Board(blocks)

        expect:
        !board.goal

        where:
        blocks << [
                [[1, 3], [2, 0]],
                [[1, 3, 2], [4, 5, 6], [7, 8, 0]],
                [[1, 2, 3], [4, 5, 6], [7, 0, 8]]
        ]
    }

    @Unroll
    def 'should return true when board in order for #blocks.length-dimensional array'(int[][] blocks) {
        given:
        board = new Board(blocks)

        expect:
        board.goal

        where:
        blocks << [
                [[1]],
                [[1, 2], [3, 0]],
                [[1, 2, 3], [4, 5, 6], [7, 8, 0]]
        ]
    }

    def 'should return all neighbours for board'(int[][] blocks, List<Board> expected) {
        given:
        board = new Board(blocks)

        when:
        def neighbors = board.neighbors()

        then:
        expect neighbors, containsInAnyOrder(expected.toArray())

        where:
        blocks                            | expected
        [[1, 3, 2], [4, 0, 6], [7, 8, 5]] | [new Board([[1, 3, 2], [0, 4, 6], [7, 8, 5]] as int[][]),
                                             new Board([[1, 3, 2], [4, 6, 0], [7, 8, 5]] as int[][]),
                                             new Board([[1, 0, 2], [4, 3, 6], [7, 8, 5]] as int[][]),
                                             new Board([[1, 3, 2], [4, 8, 6], [7, 0, 5]] as int[][])]
        [[1, 0], [2, 3]]                  | [new Board([[0, 1], [2, 3]] as int[][]),
                                             new Board([[1, 3], [2, 0]] as int[][])]
        [[0, 1], [2, 3]]                  | [new Board([[1, 0], [2, 3]] as int[][]),
                                             new Board([[2, 1], [0, 3]] as int[][])]
        [[0]]                             | []
    }

    def 'should calculate manhattan distance'(int[][] blocks, int expected) {
        given:
        board = new Board(blocks)

        expect:
        board.manhattan() == expected

        where:
        blocks                            | expected
        [[1, 2, 3], [4, 5, 6], [7, 8, 0]] | 0
        [[1, 2], [0, 3]]                  | 1
        [[1, 0], [3, 2]]                  | 1
        [[8, 1, 3], [4, 0, 2], [7, 6, 5]] | 10
    }

    def 'should return string representation of the board'() {
        given:
        board = new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][])

        expect:
        board.toString() == '3\r\n1 2 3\r\n4 5 6\r\n7 8 0'
    }

    def 'should return twin board'(int[][] blocks, int[][] expected) {
        given:
        board = new Board(blocks)

        expect:
        board.twin() == new Board(expected)

        where:
        blocks                            | expected
        [[1, 2, 3], [4, 5, 6], [7, 8, 0]] | [[2, 1, 3], [4, 5, 6], [7, 8, 0]]
        [[0, 0, 0], [0, 0, 0], [0, 1, 2]] | [[0, 0, 0], [0, 0, 0], [0, 2, 1]]
    }

    def 'should return hamming metric'(int[][] blocks, int expected) {
        given:
        board = new Board(blocks)

        expect:
        board.hamming() == expected

        where:
        blocks                            | expected
        [[1, 2, 3], [4, 5, 6], [7, 8, 0]] | 0
        [[2, 1, 3], [4, 5, 6], [7, 8, 0]] | 2
        [[2, 1, 3], [4, 8, 6], [7, 5, 0]] | 4
        [[0, 8, 7], [1, 2, 3], [4, 5, 6]] | 8
    }
}
