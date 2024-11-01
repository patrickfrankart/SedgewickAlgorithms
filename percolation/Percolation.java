import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private WeightedQuickUnionUF weightedQuickUnionUf;
    private int numberOfOpenSites;
    private int[] openSites;
    private int[] fullSites;
    private boolean percolates;
    private NotAStack stack;


    public Percolation(int n){
        this.size = n;
        this.numberOfOpenSites = 0;
        this.weightedQuickUnionUf = new WeightedQuickUnionUF(n*n);
        this.openSites = new int[n*n];
        this.fullSites = new int[n*n];
        this.percolates = false;
        this.stack = new NotAStack();
        for(Integer entry : openSites){
            entry = -1;
        }
        for(int i = 0; i < n*n; ++i){
            fullSites[i] = -1;
        }
    }

    public void open(int row, int col){
        if(!isValidIndex(col, row)){
            throw new IllegalArgumentException();
        }
        openSites[xyTo1D(col, row)] = 1;
//        System.out.println(col + " " + row + " " + xyTo1D(col, row));



        if(isValidIndex(col, row+1) && isOpen(row+1, col)){
            weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col, row+1));
            if(isFull(row+1, col)){
                fullSites[xyTo1D(col, row)] = weightedQuickUnionUf.find(xyTo1D(col, row+1));
                fillNeighbors(row, col);
            }
        }
        if(isValidIndex(col, row-1) && isOpen(row-1, col)){
            weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col, row-1));
            if(isFull(row-1, col)){
                fullSites[xyTo1D(col, row)] = weightedQuickUnionUf.find(xyTo1D(col, row-1));
                fillNeighbors(row, col);
            }
        }
        if(isValidIndex(col+1, row) && isOpen(row, col+1)){
            weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col+1, row));
            if(isFull(row, col+1)){
                fullSites[xyTo1D(col, row)] = weightedQuickUnionUf.find(xyTo1D(col+1, row));
                fillNeighbors(row, col);
            }
        }
        if(isValidIndex(col-1, row) && isOpen(row, col-1)){
            weightedQuickUnionUf.union(xyTo1D(col, row), xyTo1D(col-1, row));
            if(isFull(row, col-1)){
                fullSites[xyTo1D(col, row)] = weightedQuickUnionUf.find(xyTo1D(col-1, row));
                fillNeighbors(row, col);
            }
        }

        if(xyTo1D(col, row) < size){
            fullSites[xyTo1D(col, row)] = weightedQuickUnionUf.find(xyTo1D(col, row));

        }

        if(fullSites[xyTo1D(col, row)] >= 0){
            fillNeighbors(row, col);
        }

        if(xyTo1D(col, row) >= size*(size-1) && xyTo1D(col, row) <= size*size &&
                fullSites[xyTo1D(col, row)] >= 0){
            percolates = true;
        }
        numberOfOpenSites++;
    }

    public boolean isOpen(int row, int col){
        if(!isValidIndex(col, row)){
//            System.out.println(col + " " + row);
            throw new IllegalArgumentException();
        }
        return openSites[xyTo1D(col, row)] == 1;
    }

    public boolean isFull(int row, int col){
//        System.out.println(row + " " + col);
        if(!isValidIndex(col, row)){
//            System.out.println(col + " " + row);
            throw new IllegalArgumentException();
        }
        return fullSites[xyTo1D(col, row)] >= 0;
    }

    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }

    public boolean percolates(){
        return this.percolates;
    }

    private int xyTo1D(int x, int y){
        return (y-1)*size + (x-1);
    }

    private int[] oneDToXy(int site){
        int[] coords = new int[2];
        coords[0] = (site % size) + 1;
        coords[1] = (site / size) + 1;
        return coords;
    }

    private boolean isValidIndex(int x, int y){
        return x <= size && y <= size && x > 0 && y > 0;
    }

    private void fillNeighbors(int row, int col) {
        stack.push(xyTo1D(col, row));
//        System.out.println("In fillNEigbors");

        while (stack.isEmpty() == false) {
            int site = stack.peek();
            stack.pop();
            int[] coords = oneDToXy(site);
            int colCopy = coords[0];
            int rowCopy = coords[1];
//            System.out.println("Popped " + site);

            if (fullSites[site] < 0) {
                fullSites[site] = weightedQuickUnionUf.find(site);
                if (site >= size * (size - 1) && site <= size * size) {
//                System.out.println("In fill");
                    percolates = true;
                }
            }
//            System.out.println("site= " + xyTo1D(colCopy, rowCopy) + " " + isValidIndex(colCopy, rowCopy) + " rowCopy: " + rowCopy + " colCopy: " + colCopy + " size: " + size + " coords[1]: " + coords[1] + " coords[0] " + coords[0] + fullSites[xyTo1D(colCopy, rowCopy+1)] + " " + !isFull(rowCopy, colCopy));
//            System.out.println(isValidIndex(colCopy, rowCopy+1) + " " + isOpen(rowCopy+1, colCopy) + " " + !isFull(rowCopy+1, col) + " " + fullSites[xyTo1D(colCopy, rowCopy+1)]);
            if (isValidIndex(colCopy, rowCopy + 1) && isOpen(rowCopy + 1, colCopy) && !isFull(rowCopy + 1, colCopy)) {
                stack.push(xyTo1D(colCopy, rowCopy + 1));
//                System.out.println("pushed " + xyTo1D(colCopy, rowCopy+1));
            }
            if (isValidIndex(colCopy, rowCopy - 1) && isOpen(rowCopy - 1, colCopy) && !isFull(rowCopy - 1, colCopy)) {
                stack.push(xyTo1D(colCopy, rowCopy - 1));
//                System.out.println("pushed " + xyTo1D(colCopy, rowCopy-1));
            }
            if (isValidIndex(colCopy + 1, rowCopy) && isOpen(rowCopy, colCopy + 1) && !isFull(rowCopy, colCopy + 1)) {
                stack.push(xyTo1D(colCopy + 1, rowCopy));
//                System.out.println("pushed " + xyTo1D(colCopy+1, rowCopy));
            }
            if (isValidIndex(colCopy - 1, rowCopy) && isOpen(rowCopy, colCopy - 1) && !isFull(rowCopy, colCopy - 1)) {
                stack.push(xyTo1D(colCopy - 1, rowCopy));
//                System.out.println("pushed " + xyTo1D(colCopy-1, rowCopy));
            }

        }
    }

    public static void main(String[] args){
        //isValidIndexTest();
//        oneDToXyTest();
    }

    private static void isValidIndexTest(){
        Percolation percolation = new Percolation(20);
        System.out.println(percolation.isValidIndex(1, 1));
        System.out.println(percolation.isValidIndex(20, 1));
    }

    private static void oneDToXyTest(){
        Percolation percolation = new Percolation(6);
        int[] coords6 = percolation.oneDToXy(6);
        System.out.println("coords[0]: " + coords6[0] + " coords[1]: " + coords6[1]);
        int[] coords12 = percolation.oneDToXy(12);
        System.out.println("coords[0]: " + coords12[0] + " coords[1]: " + coords12[1]);
    }

    private class NotAStack {
        private static final int INIT_CAPCITY = 8;

        private int[] a;
        private int n;

        public NotAStack() {
            a = new int[INIT_CAPCITY];
            n = 0;
        }

        public boolean isEmpty(){
            return n == 0;
        }

        public int size() {
            return n;
        }

        private void resize(int capacity) {
            assert capacity >= n;

            int[] copy = new int[capacity];
            for(int i = 0; i < n; i++){
                copy[i] = a[i];
            }
            a = copy;
        }

        public void push(int item){
            if (n == a.length) resize(2*a.length);
            a[n++] = item;
        }

        public int pop(){
//            if (isEmpty()) return;
            int item = a[n-1];
//            a[n-1] = null;
            n--;

            if(n > 0 && n == a.length/4) resize(a.length/2);
            return item;
        }

        public int peek(){
//            if(isEmpty()) return;
            return a[n-1];
        }
    }
}
