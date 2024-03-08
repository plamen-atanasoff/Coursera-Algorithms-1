package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] percolationThresholdsPerTrial;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("input arguments are not valid");
        }
        percolationThresholdsPerTrial = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation percolationSystem = new Percolation(n);

            while (!percolationSystem.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);

                if (percolationSystem.isOpen(row, col)) {
                    continue;
                }

                percolationSystem.open(row, col);
            }

            percolationThresholdsPerTrial[i] = (double) percolationSystem.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholdsPerTrial);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholdsPerTrial);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = StdStats.mean(percolationThresholdsPerTrial);
        double stddev = StdStats.stddev(percolationThresholdsPerTrial);
        double sqrtTrials = Math.sqrt(trials);

        return mean - (CONFIDENCE_95 * stddev / sqrtTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = StdStats.mean(percolationThresholdsPerTrial);
        double stddev = StdStats.stddev(percolationThresholdsPerTrial);
        double sqrtTrials = Math.sqrt(trials);

        return mean + (CONFIDENCE_95 * stddev / sqrtTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int width = 25;
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

//        int n = 2;
//        int trials = 10000;
//        percolation.PercolationStats stats = new percolation.PercolationStats(n, trials);

        double mean = stats.mean();
        double stddev = stats.stddev();
        double confidenceLo = stats.confidenceLo();
        double confidenceHi = stats.confidenceHi();

        System.out.printf("%-" + width + "s = %.16f%n", "mean", mean);
        System.out.printf("%-" + width + "s = %.16f%n", "stddev", stddev);
        System.out.printf("%-" + width + "s = [%.16f, %.16f]%n", "95% confidence interval", confidenceLo, confidenceHi);
    }
}
