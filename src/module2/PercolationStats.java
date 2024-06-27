package module2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] means;

    // perform independent trials on an n-by-n grid
    public PercolationStats(final int n, final int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        this.means = new double[trials];

        for (int t = 0; t < trials; ++t) {
            final Percolation p = new Percolation(n);
            int counter = 0;
            do {
                final int row = StdRandom.uniformInt(n) + 1;
                final int col = StdRandom.uniformInt(n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    ++counter;
                }
            } while (!p.percolates());
            this.means[t] = ((double) counter) / ((double) (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.means);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.means);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - CONFIDENCE_95 * this.stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + CONFIDENCE_95 * this.stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);
        final int t = Integer.parseInt(args[1]);
        final PercolationStats stats = new PercolationStats(n, t);
        StdOut.printf("mean                    = %f%n", stats.mean());
        StdOut.printf("stddev                  = %f%n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]%n", stats.confidenceLo(), stats.confidenceHi());
    }
}
