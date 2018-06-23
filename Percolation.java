import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// import edu.princeton.cs.algs4.StdRandom;
// import java.util.Arrays;

public class Percolation {
    private boolean[][] sites;
    private final WeightedQuickUnionUF unionFind;
    private final int virtualTop;
    private final int virtualBottom;
    private final int sideLength;
    private int numberOpen;

    public Percolation(int n) {
        if (n <= 0) {
          throw new IllegalArgumentException("argument must be a positive integer");
        }
        sites = new boolean[n][n];
        virtualTop = 0;
        virtualBottom = n * n + 1;
        sideLength = n;
        unionFind = new WeightedQuickUnionUF(n * n + 2);

        // initialize virtual virtualTop (id = 0) connected to top row
        for (int i = 1; i <= n; i++) {
            unionFind.union(i, virtualTop);
        }
        // initialize virtual virtualBottom (id= 17) connected to bottom row
        for (int i = n * n; i > n * n - n; i--) {
            unionFind.union(i, virtualBottom);
        }
        numberOpen = 0;
    }

    public void open(int row, int col) {

        validateIndicies(row, col);
        if (!isOpen(row, col)) {
            sites[row - 1][col - 1] = true;
            numberOpen++;
            int cell = getIdFromLocation(row, col);

             // check if adjacent are open, if so, make a union
            if (row - 1 > 0 && isOpen(row - 1, col)) {
              unionFind.union(cell, getIdFromLocation(row - 1, col));
            }
            if (row + 1 <= sideLength && isOpen(row + 1, col)) {
              unionFind.union(cell, getIdFromLocation(row + 1, col));
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
              unionFind.union(cell, getIdFromLocation(row, col - 1));
            }
            if (col + 1 <= sideLength && isOpen(row, col + 1)) {
              unionFind.union(cell, getIdFromLocation(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validateIndicies(row, col);

        return sites[row - 1][col - 1];
    }

    public boolean isFull(int row, int col)  {
        validateIndicies(row, col);
        if (isOpen(row, col)) {
          int id = getIdFromLocation(row, col);
          return unionFind.connected(id, virtualTop);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numberOpen;
    }

    public boolean percolates() {
        // numberOpen >= 1;
        return numberOpen >= 1 && unionFind.connected(virtualTop, virtualBottom);
    }

    private int getIdFromLocation(int row, int col) {
        return (row - 1) * sites.length + col;
    }

    private void validateIndicies(int row, int col) {

      // row and column indicies should be between 0 and sideLength
      if (row > sideLength || row <= 0) {
        throw new IllegalArgumentException("Can't compute row index " + row + ", index is out of bounds.");
      }
      if (col > sideLength || col <= 0) {
        throw new IllegalArgumentException("Can't compute col index " + col + ", index is out of bounds.");
      }
    }

    // public static void main(String[] args) {
    //     Percolation mesh = new Percolation(8);
    //     mesh.open(3, 1); // 17
    //     mesh.open(2, 1); // 9
    //     mesh.open(2, 5); // 13
    //     mesh.open(2, 8); //16
    //     System.out.println(mesh.isFull(2, 5));
    //     System.out.println(mesh.isFull(2, 8));
    //       // mesh.open(StdRandom.uniform(1, mesh.sideLength + 1), StdRandom.uniform(1, mesh.sideLength + 1));
    //       // mesh.open(StdRandom.uniform(1, mesh.sideLength + 1), StdRandom.uniform(1, mesh.sideLength + 1));
    //       // mesh.open(StdRandom.uniform(1, mesh.sideLength + 1), StdRandom.uniform(1, mesh.sideLength + 1));
    //       // mesh.open(StdRandom.uniform(1, mesh.sideLength + 1), StdRandom.uniform(1, mesh.sideLength + 1));
    //       // mesh.open(StdRandom.uniform(1, mesh.sideLength + 1), StdRandom.uniform(1, mesh.sideLength + 1));
    //       for (boolean[] site : mesh.sites) {
    //           System.out.println(Arrays.toString(site));
    //       }
    // //     System.out.println("full? " + mesh.isFull(3, 1));
    // //     System.out.println("open? " + mesh.isOpen(3, 1));
    // //     // System.out.println("full? " + mesh.isFull(2, 2));
    // //     // System.out.println("open? " + mesh.isOpen(2, 2));
    // //     // System.out.println(mesh.unionFind.connected(mesh.virtualTop, 1));
    // //     System.out.println(mesh.numberOfOpenSites());
    // //     System.out.println(mesh.percolates());
    // }
}
