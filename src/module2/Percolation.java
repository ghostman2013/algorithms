package module2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final boolean[] states;
    private int openSitesCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        final int length = n * n;
        this.uf = new WeightedQuickUnionUF(length + 2);
        this.states = new boolean[length];
        this.openSitesCount = 0;
    }

    private void validate(final int row, final int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException();
        }
    }

    private int getIndex(final int row, final int col) {
        return (row - 1) * this.n + (col - 1);
    }

    private boolean getState(final int row, final int col) {
        final int index = getIndex(row, col);
        return this.states[index];
    }

    private int getRoot(final int row, final int col) {
        final int p = this.getIndex(row, col) + 1;
        return this.uf.find(p);
    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {
        this.validate(row, col);

        final int index = this.getIndex(row, col);

        if (this.states[index]) return; // Already open

        this.states[index] = true;
        ++this.openSitesCount;

        // Top root
        if (row == 1) {
            this.uf.union(0, index + 1);
        }
        // Top
        if (row > 1 && this.getState(row - 1, col)) {
            this.uf.union(index + 1, index - this.n + 1);
        }
        // Left
        if (col > 1 && this.getState(row, col - 1)) {
            this.uf.union(index, index + 1);
        }
        // Right
        if (col < this.n && this.getState(row, col + 1)) {
            this.uf.union(index + 1, index + 2);
        }
        // Bottom
        if (row < this.n && this.getState(row + 1, col)) {
            this.uf.union(index + this.n + 1, index + 1);
        }
        // Bottom root
        if (row == this.n) {
            this.uf.union(index + 1, this.n * this.n + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        this.validate(row, col);
        return this.getState(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(final int row, final int col) {
        this.validate(row, col);
        final int p = this.getRoot(row, col);
        final int q = this.uf.find(0);
        return p == q;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        final int p = this.uf.find(0);
        final int q = this.uf.find(this.n * this.n + 1);
        return p == q;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(8);

        p.open(2, 1);
        p.open(2, 4);
        p.open(2, 5);
        p.open(2, 6);
        p.open(2, 7);
        p.open(2, 8);

        p.open(1, 3);
        p.open(1, 4);
        p.open(1, 5);

        p.open(3, 1);
        p.open(3, 2);
        p.open(3, 3);
        p.open(3, 6);
        p.open(3, 7);


        p.open(4, 3);
        p.open(4, 4);
        p.open(4, 6);
        p.open(4, 7);
        p.open(4, 8);

        p.open(5, 2);
        p.open(5, 3);
        p.open(5, 4);
        p.open(5, 6);
        p.open(5, 7);

        p.open(6, 2);
        p.open(6, 7);
        p.open(6, 8);

        p.open(7, 1);
        p.open(7, 3);
        p.open(7, 5);
        p.open(7, 6);
        p.open(7, 7);
        p.open(7, 8);

        p.open(8, 1);
        p.open(8, 2);
        p.open(8, 3);
        p.open(8, 4);
        p.open(8, 6);

        StdOut.printf("Does the system percolate? %s%n", p.percolates());
    }
}
