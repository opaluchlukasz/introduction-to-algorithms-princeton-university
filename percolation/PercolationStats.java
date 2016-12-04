import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static java.lang.Math.*;

public class PercolationStats {

    private final double[] ratios;
    private final int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)  {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Improper input arguments");
        }

        this.trials = trials;
        ratios = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int open = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    open = open + 1;
                }
            }

            ratios[i] = (double) open / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(ratios);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(ratios);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo()  {
        return mean() - 1.96d * stddev() / sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96d * stddev() / sqrt(trials);
    }

    // test client (described below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);

        System.out.println(String.format("mean                    = %f",
                percolationStats.mean()));
        System.out.println(String.format("stddev                  = %f",
                percolationStats.stddev()));
        System.out.println(String.format("95%% confidence interval = %f, %f",
                percolationStats.confidenceLo(), percolationStats.confidenceHi()));

    }
}