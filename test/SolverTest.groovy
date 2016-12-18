import spock.lang.Specification
import spock.lang.Unroll

class SolverTest extends Specification {

    private static final Board SOLVABLE_BOARD = new Board([[0, 1, 3], [4, 2, 5], [7, 8, 6]] as int[][])
    private static final Board SOLVED_BOARD = new Board([[1, 2], [3, 0]] as int[][])
    private static final Board UNSOLVABLE_BOARD_1 = new Board([[2, 1], [3, 0]] as int[][])
    private static final Board UNSOLVABLE_BOARD_2 = new Board([[1, 2, 3], [4, 5, 6], [8, 7, 0]] as int[][])

    def 'should return true for solvable puzzle'() {
        when:
        Solver solver = new Solver(SOLVABLE_BOARD)

        then:
        solver.solvable
    }

    def 'should return false for unsolvable puzzle'() {
        when:
        Solver solver = new Solver(board)

        then:
        !solver.solvable

        where:
        board << [UNSOLVABLE_BOARD_1, UNSOLVABLE_BOARD_2]
    }

    @Unroll
    def 'should return number of moves fo solution #desc'(Board board, int expected, String desc) {
        when:
        Solver solver = new Solver(board)

        then:
        solver.moves() == expected

        where:
        board              | expected | desc
        SOLVED_BOARD       | 0        | 'when board already solved'
        SOLVABLE_BOARD     | 4        | 'when board is solvable'
        UNSOLVABLE_BOARD_1 | -1       | 'when board is unsolvable'
    }

    def 'should return problem solution'(Board board, List<Board> expected) {
        when:
        Solver solver = new Solver(board)

        then:
        solver.solution() == expected

        where:
        board                                  | expected
        new Board([[1, 0], [3, 2]] as int[][]) | [new Board([[1, 0], [3, 2]] as int[][]),
                                                  new Board([[1, 2], [3, 0]] as int[][])]
        SOLVABLE_BOARD                         | [new Board([[0, 1, 3], [4, 2, 5], [7, 8, 6]] as int[][]),
                                                  new Board([[1, 0, 3], [4, 2, 5], [7, 8, 6]] as int[][]),
                                                  new Board([[1, 2, 3], [4, 0, 5], [7, 8, 6]] as int[][]),
                                                  new Board([[1, 2, 3], [4, 5, 0], [7, 8, 6]] as int[][]),
                                                  new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][])]

    }
}
