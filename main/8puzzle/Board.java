import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

public class Board {
    private int[][] blocks;

    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = deepCopyTwoDimensionalArray(blocks);
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int metric = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if(blocks[i][j] != 0 && i * dimension() + j + 1 != blocks[i][j]) {
                    metric++;
                }
            }
        }
        return metric;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distanceSum = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && i * dimension() + j + 1 != blocks[i][j]) {
                    int row = (int) Math.ceil((double) blocks[i][j] / dimension()) - 1;
                    distanceSum += Math.abs(i - row);
                    distanceSum += Math.abs(j + 1 - (blocks[i][j] - (row * dimension())));
                }
            }
        }
        return distanceSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && i * dimension() + j + 1 != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twin = deepCopyTwoDimensionalArray(blocks);
        Integer firstX = null;
        int firstY = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0) {
                    if(firstX == null) {
                        firstX = i;
                        firstY = j;
                    } else {
                        swap(twin, firstX, firstY, i, j);
                        return new Board(twin);
                    }
                }
            }
        }
        return new Board(twin);
    }

    private void swap(int[][] twin, int x1, int y1, int x2, int y2) {
        int temp = twin[x1][y1];
        twin[x1][y1] = twin[x2][y2];
        twin[x2][y2] = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        return Arrays.deepEquals(blocks, board.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> boards = new LinkedList<>();
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    if(i > 0) {
                        int[][] newOne = deepCopyTwoDimensionalArray(blocks);
                        newOne[i][j] = newOne[i - 1][j];
                        newOne[i - 1][j] = 0;
                        boards.add(new Board(newOne));
                    }
                    if (i < dimension() - 1) {
                        int[][] newOne = deepCopyTwoDimensionalArray(blocks);
                        newOne[i][j] = newOne[i + 1][j];
                        newOne[i + 1][j] = 0;
                        boards.add(new Board(newOne));
                    }
                    if (j > 0) {
                        int[][] newOne = deepCopyTwoDimensionalArray(blocks);
                        newOne[i][j] = newOne[i][j - 1];
                        newOne[i][j - 1] = 0;
                        boards.add(new Board(newOne));
                    }
                    if (j < dimension() - 1) {
                        int[][] newOne = deepCopyTwoDimensionalArray(blocks);
                        newOne[i][j] = newOne[i][j + 1];
                        newOne[i][j + 1] = 0;
                        boards.add(new Board(newOne));
                    }
                }
            }
        }
        return boards;
    }

    // string representation of this board (in the output format specified below)
    @Override
    public String toString() {
        return Arrays
                .stream(blocks)
                .map(array -> Arrays.stream(array).mapToObj(Integer::toString).collect(joining(" ")))
                .collect(joining(lineSeparator(), dimension() + lineSeparator(), ""));
    }

    private static int[][] deepCopyTwoDimensionalArray(int[][] input) {
        int[][] result = new int[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
}