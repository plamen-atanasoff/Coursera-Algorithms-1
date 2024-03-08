package percolation;

public class Percolation {
    private int[] grid;
    private int[] size;
    private int n;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid input argument");
        }

        grid = new int[n * n];
        size = new int[n * n];
        numberOfOpenSites = 0;
        this.n = n;

        for (int i = 0; i < n * n; i++) {
            grid[i] = -1;
            size[i] = 1;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        int pos = (row - 1) * n + (col - 1);
        openFromZeroInd(pos);
    }

    private void openFromZeroInd(int i) {
        if (isOpen(i)) {
            return;
        }

        grid[i] = i;
        numberOfOpenSites++;

        int row = i / n;
        int col = i % n;

        int[][] moves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] move : moves) {
            int neighbourSiteRow = row + move[0];
            int neighbourSiteCol = col + move[1];

            if (neighbourSiteRow < 0 || neighbourSiteRow >= n) {
                continue;
            }
            if (neighbourSiteCol < 0 || neighbourSiteCol >= n) {
                continue;
            }
            if (!isOpen(neighbourSiteRow * n + neighbourSiteCol)) {
                continue;
            }

            union(row * n + col, neighbourSiteRow * n + neighbourSiteCol);
        }
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("invalid input coordinates");
        }
    }

    private int root(int i) {
        while (i != grid[i]) {
            i = grid[i];
        }

        return i;
    }

    private void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);

        if (!isOpen(p) || !isOpen(q)) {
            return;
        }

        if (rootP == rootQ) {
            return;
        }

        // attach the smaller tree to the bigger one
        if (size[rootP] < size[rootQ]) {
            grid[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            grid[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }

        // keep the element from first row(if exists) at root
        if (rootP / n == 0) {
            grid[rootP] = rootP;
            grid[rootQ] = rootP;
        } else if (rootQ / n == 0) {
            grid[rootP] = rootQ;
            grid[rootQ] = rootQ;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return isOpen((row - 1) * n + (col - 1));
    }

    private boolean isOpen(int i) {
        return grid[i] != -1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        int pos = (row - 1) * n + (col - 1);
        return isFull(pos);
    }

    private boolean isFull(int i) {
        if (!isOpen(i)) {
            return false;
        }

        return root(i) / n == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (isFull((n - 1) * n + i)) {
                return true;
            }
        }

        return false;
    }

//    private void print() {
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(grid[i * n + j]);
//            }
//            System.out.print('\n');
//        }
//    }

    // test client (optional)
//    public static void main(String[] args) {
//        int n = 3;
//        percolation.Percolation percolationSystem = new percolation.Percolation(n);

//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            String message = scanner.nextLine();
//
//            if ("quit".equals(message)) {
//                scanner.close();
//                break;
//            }
//
//            try {
//                if (message.startsWith("open")) {
//                    String[] tokens = message.split(" ");
//                    percolationSystem.open(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
//                } else if (message.startsWith("isOpen")) {
//                    String[] tokens = message.split(" ");
//                    System.out.println(percolationSystem.isOpen(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
//                } else if (message.startsWith("isFull")) {
//                    String[] tokens = message.split(" ");
//                    System.out.println(percolationSystem.isFull(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
//                } else if (message.startsWith("numberOfOpenSites")) {
//                    System.out.println(percolationSystem.numberOfOpenSites());
//                } else if (message.startsWith("percolates")) {
//                    System.out.println(percolationSystem.percolates());
//                } else if (message.startsWith("print")) {
//                    percolationSystem.print();
//                } else {
//                    System.out.println("unknown command");
//                }
//                System.out.print('\n');
//            } catch (Exception e) {
//                System.out.println(e.toString());
//            }
//        }
//    }
}
