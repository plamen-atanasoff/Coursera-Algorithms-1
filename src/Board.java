import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Board {
    private final int[][] tiles;
    private final int size;
    private int blankSquarePos;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.tiles = new int[size][size];
        copyTiles(tiles);

        this.blankSquarePos = getBlankSquarePos();
    }

    private Board(Board other) {
        this.size = other.size;
        this.tiles = new int[size][size];
        copyTiles(other.tiles);

        this.blankSquarePos = other.blankSquarePos;
    }

    private int getBlankSquarePos() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    return i * size + j;
                }
            }
        }

        throw new RuntimeException("there is no 0 tile");
    }

    private void copyTiles(int[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, tiles.length);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(size).append("\n");
        for (int i = 0; i < size; i++) {
            str.append(" ");
            for (int j = 0; j < size; j++) {
                str.append(tiles[i][j]).append("  ");
            }
            str.append("\n");
        }
        str.append("\n");

        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int ctr = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int sum = size * i + j;

                if (tiles[i][j] != sum + 1) {
                    ctr++;
                }
            }
        }

        // empty space is last
        if (tiles[size - 1][size - 1] == 0) {
            ctr--;
        }

        return ctr;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int destX, destY;
                if (tiles[i][j] == 0) {
                    destX = destY = size - 1;
                } else {
                    destX = (tiles[i][j] - 1) % size;
                    destY = (tiles[i][j] - 1) / size;
                }

                sum += Math.abs(j - destX) + Math.abs(i - destY);
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    return tiles[i][j] == 0;
                }

                if (tiles[i][j] != (i * size + j) + 1) {
                    return false;
                }
            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (!(y instanceof Board other)) {
            return false;
        }

        return size == other.size && isEqualTo(other);
    }

    private boolean isEqualTo(Board other) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int x = blankSquarePos % size;
        int y = blankSquarePos / size;

        int itSize;
        if (inCorner(x, y)) {
            itSize = 2;
        } else if (toWall(x, y)) {
            itSize = 3;
        } else {
            itSize = 4;
        }

        Board[] neighbours = new Board[itSize];
        fillNeighbours(neighbours, x, y);

        return new Neighbours(neighbours);
    }

    private boolean inCorner(int x, int y) {
        return (x == 0 && y == 0) || (x == size - 1 && y == 0)
            || (x == 0 && y == size - 1) || (x == size - 1 && y == size - 1);
    }

    private boolean toWall(int x, int y) {
        return (x == 0) || (y == 0) || (x == size - 1) || (y == size - 1);
    }

    private static class Neighbours implements Iterable<Board> {
        private final Board[] neighbours;
        private final int size;

        public Neighbours(Board[] neighbours) {
            this.neighbours = neighbours;
            this.size = neighbours.length;
        }

        @Override
        public Iterator<Board> iterator() {
            return new Iterator<>() {
                private int curPos = 0;

                @Override
                public boolean hasNext() {
                    return curPos < size;
                }

                @Override
                public Board next() {
                    return neighbours[curPos++];
                }
            };
        }
    }

    private void fillNeighbours(Board[] neighbours, int x, int y) {
        int[][] moves = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int cur = 0;

        for (int[] move : moves) {
            int dx = x + move[0];
            int dy = y + move[1];

            if (dx >= 0 && dx < size && dy >= 0 && dy < size) {
                neighbours[cur++] = createNeighbour(x, y, dx, dy);
            }
        }
    }

    private Board createNeighbour(int x1, int y1, int x2, int y2) {
        Board neighbour = new Board(this);

        neighbour.tiles[y1][x1] = tiles[y2][x2];
        neighbour.tiles[y2][x2] = tiles[y1][x1];

        return neighbour;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(this);

        int x1 = StdRandom.uniformInt(0 ,size);
        int y1 = StdRandom.uniformInt(0 ,size);
        int x2 = StdRandom.uniformInt(0 ,size);
        int y2 = StdRandom.uniformInt(0 ,size);

        twin.tiles[y1][x1] = tiles[y2][x2];
        twin.tiles[y2][x2] = tiles[y1][x1];

        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        Board board = new Board(tiles);

        System.out.println(board);
        System.out.println(board.twin());
    }
}