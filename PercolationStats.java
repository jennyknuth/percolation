import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
  private static final double VARIANCE_CONSTANT = 1.96;
    private final double[] results;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
          throw new IllegalArgumentException("need arguments greater than zero");
        }
        results = new double[trials];

        for (int i = 0; i < trials; i++) {
          Percolation percolation = new Percolation(n);
          while (!percolation.percolates()) {
            percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
          }
          results[i] = (double) percolation.numberOfOpenSites() / (double) (n * n);
        }
    }

    public double mean() {
      return StdStats.mean(results);
    }

    public double stddev() {
      return StdStats.stddev(results);
    }

    public double confidenceLo() {
      return mean() -  ((VARIANCE_CONSTANT * stddev()) / Math.sqrt(results.length));
    }

    public double confidenceHi() {
      return mean() +  ((VARIANCE_CONSTANT * stddev()) / Math.sqrt(results.length));

    }

    public static void main(String[] args)  {
      PercolationStats threshold = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      System.out.println("mean = " + threshold.mean());
      System.out.println("stddev = " + threshold.stddev());
      System.out.println("95% confidence interval = [" + threshold.confidenceLo() + ", " + threshold.confidenceHi() + "]");
    }
}
