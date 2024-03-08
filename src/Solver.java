import edu.princeton.cs.algs4.MinPQ;

public final class Solver {
    private final int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (!isSolvable(initial)) {
            moves = -1;
        } else {
            moves = 0;
        }
    }

    public static boolean isSolvable(Board initial) {
        int boardDimension = initial.dimension();
        int elementsCount = boardDimension * boardDimension;
        int[] permutation = new int[elementsCount];
        for (int i = 0, j = 1; i < elementsCount; i++) {
            int row = i / boardDimension;
            int col = i % boardDimension;

            if (initial.tiles[row][col] == 0) {
                continue;
            }

            permutation[j++] = initial.tiles[row][col];
        }
        int invCount = countInversions(permutation);

        if (boardDimension % 2 == 1) {
            return invCount % 2 == 0;
        } else {
            int blankTileRow = initial.blankSquarePos / boardDimension;
            if ((boardDimension - blankTileRow) % 2 == 1) {
                return invCount % 2 == 0;
            } else {
                return invCount % 2 == 1;
            }
        }
    }

    private static int countInversions(int[] permutation) {
        int count = 0;
        for (int i = 1; i < permutation.length; i++) {
            for (int j = i + 1; j < permutation.length; j++) {
                if (permutation[i] > permutation[j]) {
                    count++;
                }
            }
        }
        System.out.println(count);
        return count;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles1 = {{1,2,3},{4,0,6},{7,8,5}};
        int[][] tiles2 = {{0,2,3},{4,1,6},{7,8,5}};
        int[][] tiles3 = {{1,2,3,4},{5,6,7,8},{0,10,11,12},{13,14,15,9}};
        int[][] tiles4 = {{1,2,3,4},{0,6,7,8},{5,10,11,12},{13,14,15,9}};
        int[][] tiles5 = {{1,2,3,4},{5,6,0,8},{9,11,7,12},{13,14,15,10}};
        int[][] tiles6 = {{1,0,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,2}};
        Board boardUnsolvable1 = new Board(tiles1);
        Board boardSolvable1 = new Board(tiles2);
        Board boardUnsolvable2 = new Board(tiles3);
        Board boardUnsolvable3 = new Board(tiles4);
        Board boardSolvable3 = new Board(tiles5);
        Board boardSolvable4 = new Board(tiles6);

        System.out.println(isSolvable(boardSolvable4));
    }
}