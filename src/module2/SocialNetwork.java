package module2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SocialNetwork {
    private final int[] parents;
    private final int[] sizes;
    private int count;
    private int latestTimestamp;

    public SocialNetwork(final int n) {
        this.parents = new int[n];
        this.sizes = new int[n];
        for (int i = 0; i < n; ++i) {
            this.parents[i] = i;
            this.sizes[i] = 1;
        }

        this.count = 0;
        this.latestTimestamp = 0;
    }

    private int find(int node) {
        while (node != this.parents[node]) {
            node = this.parents[node];
        }
        return node;
    }

    public int getLatestTimestamp() {
        return this.latestTimestamp;
    }

    private void validate(int member) {
        if (member < 0 || member > this.parents.length) {
            throw new IllegalArgumentException("Member's ID must be in [1; N].");
        }
    }

    public void addLink(int member1, int member2, int timestamp) {
        this.validate(member1);
        this.validate(member2);

        if (timestamp <= 0) {
            throw new IllegalArgumentException("Timestamp must be greater than 1");
        }

        final int root1 = this.find(member1 - 1);
        final int root2 = this.find(member2 - 1);

        if (root1 == root2) return;

        if (this.sizes[root1] >= this.sizes[root2]) {
            this.parents[root2] = root1;
            this.sizes[root1] += this.sizes[root2];
        } else {
            this.parents[root1] = root2;
            this.sizes[root2] += this.sizes[root1];
        }
        ++this.count;
        this.latestTimestamp = timestamp;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isConnected() {
        return this.count == (this.parents.length - 1);
    }

    public static void main(String[] args) {
        final int n = Integer.parseInt(StdIn.readString());

        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 1");
        }

        final int m = Integer.parseInt(StdIn.readString());

        if (m <= 0) {
            throw new IllegalArgumentException("M must be greater than 1");
        }

        final SocialNetwork network = new SocialNetwork(n);

        for (int i = 0; i < m; ++i) {
            final int timestamp = Integer.parseInt(StdIn.readString());
            final int member1 = Integer.parseInt(StdIn.readString());
            final int member2 = Integer.parseInt(StdIn.readString());
            network.addLink(member1, member2, timestamp);

            if (!network.isConnected()) continue;

            StdOut.printf("Earliest timestamp: %d.%n", network.getLatestTimestamp());
            break;
        }

        if (!network.isConnected()) {
            StdOut.println("Network is not fully connected.");
        }
    }
}
