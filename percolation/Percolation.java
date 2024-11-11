import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte BLOCKED = 0;
    private static final byte OPEN = 1;
    private static final byte CONNECTED_TO_BOTTOM = 2;
    private static final byte CONNECTED_TO_TOP = 4;
    private int size;
    private WeightedQuickUnionUF weightedQuickUnionUf;
    private int numberOfOpenSites;
    private byte[] states;
    private boolean percolates;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.numberOfOpenSites = 0;
        this.weightedQuickUnionUf = new WeightedQuickUnionUF(n*n);
        this.states = new byte[n*n];
        this.percolates = false;

        for (int i = 0; i < n*n; i++) {
            if (i < n && i >= n*(n-1)) {
                states[i] = CONNECTED_TO_TOP | CONNECTED_TO_BOTTOM;
            }
            else if (i < n) {
                states[i] = CONNECTED_TO_TOP;
            }
            else if (i >= n*(n-1)) {
                states[i] = CONNECTED_TO_BOTTOM;
            }
            else {
                states[i] = BLOCKED;
            }
        }
    }

    public void open(int row, int col) {
        if (!isValidIndex(col, row)) {
            throw new IllegalArgumentException();
        }
        if (!isOpen(row, col)) {
            byte[] neighborRoots = new byte[]{-1, -1, -1, -1};
            byte rootState = states[weightedQuickUnionUf.find(xyTo1D(col, row))];
            rootState = (byte) (rootState | 1);

            if (isValidIndex(col, row + 1) && isOpen(row + 1, col)) {
                neighborRoots[0] = states[weightedQuickUnionUf.find(xyTo1D(col, row + 1))];
                rootState = (byte) (rootState | neighborRoots[0]);
                states[xyTo1D(col, row + 1)] = rootState;
                weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col, row + 1));
                states[weightedQuickUnionUf.find(xyTo1D(col, row))] = rootState;
            }
            if (isValidIndex(col, row - 1) && isOpen(row - 1, col)) {
                neighborRoots[1] = states[weightedQuickUnionUf.find(xyTo1D(col, row - 1))];
                rootState = (byte) (rootState | neighborRoots[1]);
                states[xyTo1D(col, row - 1)] = rootState;
                weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col, row - 1));
                states[weightedQuickUnionUf.find(xyTo1D(col, row))] = rootState;
            }
            if (isValidIndex(col + 1, row) && isOpen(row, col + 1)) {
                neighborRoots[2] = states[weightedQuickUnionUf.find(xyTo1D(col + 1, row))];
                rootState = (byte) (rootState | neighborRoots[2]);
                states[xyTo1D(col+1, row)] = rootState;
                weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col + 1, row));
                states[weightedQuickUnionUf.find(xyTo1D(col, row))] = rootState;
            }
            if (isValidIndex(col - 1, row) && isOpen(row, col - 1)) {
                neighborRoots[3] = states[weightedQuickUnionUf.find(xyTo1D(col - 1, row))];
                rootState = (byte) (rootState | neighborRoots[3]);
                states[xyTo1D(col-1, row)] = rootState;
                weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col - 1, row));
                states[weightedQuickUnionUf.find(xyTo1D(col, row))] = rootState;
            }

            states[xyTo1D(col, row)] = rootState;

            if (rootState == 7) {
                this.percolates = true;
            }

            numberOfOpenSites++;
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isValidIndex(col, row)) {
            throw new IllegalArgumentException();
        }
        return (states[xyTo1D(col, row)] & 1) != 0;
    }

    public boolean isFull(int row, int col) {
        if (!isValidIndex(col, row)) {
            throw new IllegalArgumentException();
        }
        return (states[weightedQuickUnionUf.find(xyTo1D(col, row))] & 5) == 5;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return this.percolates;
    }

    private int xyTo1D(int x, int y) {
        return (y-1)*size + (x-1);
    }

    private boolean isValidIndex(int x, int y) {
        return x <= size && y <= size && x > 0 && y > 0;
    }
}
