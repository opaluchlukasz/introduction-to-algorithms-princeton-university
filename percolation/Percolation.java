import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] opened;
    private final WeightedQuickUnionUF unionFind;
    private final int totalSize;
    private final int size;

    // create size-by-size grid, with all sites blocked
    public Percolation(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.size = size;
        totalSize = size * size + 2;
        unionFind = new WeightedQuickUnionUF(totalSize);
        opened = new boolean[size][size];
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateBoundaries(row, col);
        opened[row - 1][col - 1] = true;
        unionWithNeighbours(row, col);

        if (row == 1) {
            unionFind.union(findIndex(row, col), 0);
        }

        if (row == size) {
            unionFind.union(findIndex(row, col), totalSize - 1);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateBoundaries(row, col);
        return opened[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateBoundaries(row, col);
        return unionFind.connected(0, findIndex(row, col));
    }

    private int findIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    private void validateBoundaries(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size) {
            throw new IndexOutOfBoundsException(String.format("Size: %d, row: %d, col: %d", size, row, col));
        }
    }

    private void unionWithNeighbours(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            unionFind.union(findIndex(row, col), findIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            unionFind.union(findIndex(row, col), findIndex(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            unionFind.union(findIndex(row, col), findIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            unionFind.union(findIndex(row, col), findIndex(row + 1, col));
        }
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.connected(0, totalSize - 1);
    }

    // client test
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);

        assert !percolation.isFull(1, 1);

        assert !percolation.unionFind.connected(0, 1);
        open(percolation, 1, 1);
        assert percolation.unionFind.connected(0, 1);

        open(percolation, 1, 2);
        assert percolation.unionFind.connected(1, 2);

        open(percolation, 2, 3);
        open(percolation, 3, 5);
        open(percolation, 3, 4);
        open(percolation, 2, 2);

        assert !percolation.isFull(3, 5);
        open(percolation, 3, 3);
        assert percolation.isFull(3, 5);
        open(percolation, 4, 5);

        assert !percolation.percolates();
        open(percolation, 5, 5);
        assert percolation.percolates();

        Percolation percolationOne = new Percolation(1);
        open(percolationOne, 1, 1);

        Percolation percolationThree = new Percolation(3);
        open(percolationThree, 1, 3);
        open(percolationThree, 2, 3);

        open(percolationThree, 3, 3);

        assert percolationThree.percolates();
    }

    private static void open(Percolation percolation, int row, int col) {
        assert !percolation.isOpen(row, col);
        percolation.open(row, col);
        assert percolation.isOpen(row, col);
    }
}
