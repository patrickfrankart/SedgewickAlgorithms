import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
    private int n;
    private int trials;
    private double[] results;
    private double mean;


    public PercolationStats(int n, int trials){
        this.n = n;
        this.trials = trials;
        this.results = new double[trials];

        if(n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < trials; ++i){
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()){
                percolation.open(StdRandom.uniformInt(1, n+1), StdRandom.uniformInt(1, n+1));
            }

            results[i] =  ((double)percolation.numberOfOpenSites()/(n*n));
        }

    }

    public double mean(){
        return StdStats.mean(results);
    }

    public double stddev(){
        return StdStats.stddev(results);
    }

    public double confidenceLo(){
        return this.mean() - (1.96*StdStats.var(results) / Math.sqrt(trials));
    }

    public double confidenceHi(){

        return this.mean() + (1.96*StdStats.var(results) / Math.sqrt(trials));
    }

    public static void main(String[] args){
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean                    = %.20f%n", percolationStats.mean());
        System.out.printf("stddev                    = %.20f%n", percolationStats.stddev());
        System.out.printf("%s %s%.20f %s%.20f%s", "95% confidence interval = ", "[", percolationStats.confidenceLo(), ", " ,   percolationStats.confidenceHi(), "]");
    }
}
