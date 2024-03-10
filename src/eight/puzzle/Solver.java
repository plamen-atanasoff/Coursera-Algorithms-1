package eight.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public final class Solver {
    private final int MAX_ITERATIONS = 100000;
    private final int moves;
    private final Node finalNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial is null");
        }

//        if (!isSolvable(initial)) {
//            moves = -1;
//            finalNode = null;
//        } else {
//            finalNode = solve(initial);
//
//            assert finalNode != null;
//
//            moves = finalNode.moves;
//        }

        finalNode = solve(initial);

        if (finalNode == null) {
            moves = -1;
        } else {
            moves = finalNode.moves;
        }
    }

    private Node solve(Board initial) {
        Node startNode = new Node(initial, initial.manhattan());
        startNode.moves = 0;
        startNode.priority = 0;

        MinPQ<Node> minPQ = new MinPQ<>();
//        List<eight.puzzle.Board> passedBoards = new LinkedList<>();

        minPQ.insert(startNode);
//        passedBoards.add(initial);

        int iterations = 0;
//        eight.puzzle.Board previous = initial;
        while (!minPQ.isEmpty() && iterations < MAX_ITERATIONS) {
            Node closestNode = minPQ.delMin();

//            System.out.print("current:\n" + closestNode.board);

            if (closestNode.board.isGoal()) {
                return closestNode;
            }

//            System.out.print("neighbours:\n");

            Iterable<Board> neighbours = closestNode.board.neighbors();
            for (Board neighbour : neighbours) {
                if (closestNode.parent != null && neighbour.equals(closestNode.parent.board)/*neighbour.equals(previous)*//* || passedBoards.contains(neighbour)*/) {
                    continue;
                }

//                System.out.print(neighbour);

                Node neighbourNode = new Node(neighbour, neighbour.manhattan());

//                if (!contains(neighbourNode, minPQ) && !passedNodes.contains(neighbourNode)*/) {
                    neighbourNode.parent = closestNode;
                    neighbourNode.moves = closestNode.moves + 1;
                    neighbourNode.priority = neighbourNode.moves + neighbourNode.heuristic;

//                System.out.println("priority: " + neighbourNode.priority);

                    minPQ.insert(neighbourNode);
//                }
//                passedBoards.add(neighbour);
            }

//            previous = closestNode.board;

            iterations++;
        }

        return null;
    }

//    private Node solveConcurrent(eight.puzzle.Board initial) {
//        eight.puzzle.Board twin = initial.twin();
//        System.out.println(twin);
//
//        Node startNodeInitial = new Node(initial, initial.manhattan());
//        Node startNodeTwin = new Node(twin, twin.manhattan());
//
//        MinPQ<Node> minPQInitial = new MinPQ<>();
//        Set<eight.puzzle.Board> passedBoardsInitial = new HashSet<>();
//        MinPQ<Node> minPQTwin = new MinPQ<>();
//        Set<eight.puzzle.Board> passedBoardsTwin = new HashSet<>();
//
//        minPQInitial.insert(startNodeInitial);
//        passedBoardsInitial.add(initial);
//        minPQTwin.insert(startNodeTwin);
//        passedBoardsTwin.add(twin);
//
//        eight.puzzle.Board previousInitial = initial;
//        eight.puzzle.Board previousTwin = twin;
//        while (!minPQInitial.isEmpty() || !minPQTwin.isEmpty()) {
//            Node closestNodeInitial = minPQInitial.delMin();
//            Node closestNodeTwin = minPQTwin.delMin();
//
////            System.out.print("current:\n" + closestNodeInitial.board);
////            System.out.print("current:\n" + closestNodeTwin.board);
//
//
//            if (closestNodeInitial.board.isGoal()) {
//                return closestNodeInitial;
//            } else if (closestNodeTwin.board.isGoal()) {
//                return null; // initial board is unsolvable
//            }
//
////            System.out.print("neighboursInitial:\n");
////            System.out.print("neighboursTwin:\n");
//
//            Iterable<eight.puzzle.Board> neighboursInitial = closestNodeInitial.board.neighbors();
//            checkNeighbours(false, neighboursInitial, previousInitial, closestNodeInitial, passedBoardsInitial, minPQInitial);
//            Iterable<eight.puzzle.Board> neighboursTwin = closestNodeTwin.board.neighbors();
//            checkNeighbours(false, neighboursTwin, previousTwin, closestNodeTwin, passedBoardsTwin, minPQTwin);
//
//            previousInitial = closestNodeInitial.board;
//            previousTwin = closestNodeTwin.board;
//        }
//
//        return null;
//    }
//
//    private void checkNeighbours(boolean twin, Iterable<eight.puzzle.Board> neighbours, eight.puzzle.Board previous, Node closestNode, Set<eight.puzzle.Board> passedBoards, MinPQ<Node> minPQ) {
//        for (eight.puzzle.Board neighbour : neighbours) {
//            if (neighbour.equals(previous) || passedBoards.contains(neighbour)) {
//                continue;
//            }
//
//            Node neighbourNode = new Node(neighbour, neighbour.manhattan());
//
//            neighbourNode.parent = closestNode;
//            neighbourNode.moves = closestNode.moves + 1;
//            neighbourNode.priority = neighbourNode.moves + neighbourNode.heuristic;
//
//            if (twin) {
//                System.out.print(neighbour);
//                System.out.println("priority: " + neighbourNode.priority);
//            }
//
//            minPQ.insert(neighbourNode);
//            passedBoards.add(neighbour);
//        }
//    }

    private boolean contains(Node node, MinPQ<Node> queue) {
        for (Node curNode : queue) {
            if (node.equals(curNode)) {
                return true;
            }
        }

        return false;
    }

//    private static boolean isSolvable(eight.puzzle.Board initial) {
//        int boardDimension = initial.dimension();
//        int elementsCount = boardDimension * boardDimension;
//        int[] permutation = new int[elementsCount];
//        for (int i = 0, j = 1; i < elementsCount; i++) {
//            int row = i / boardDimension;
//            int col = i % boardDimension;
//
//            if (initial.tiles[row][col] == 0) {
//                continue;
//            }
//
//            permutation[j++] = initial.tiles[row][col];
//        }
//        int invCount = countInversions(permutation);
//
//        if (boardDimension % 2 == 1) {
//            return invCount % 2 == 0;
//        } else {
//            int blankTileRow = initial.blankSquarePos / boardDimension;
//            if ((boardDimension - blankTileRow) % 2 == 1) {
//                return invCount % 2 == 0;
//            } else {
//                return invCount % 2 == 1;
//            }
//        }
//    }

//    private static int countInversions(int[] permutation) {
//        int count = 0;
//        for (int i = 1; i < permutation.length; i++) {
//            for (int j = i + 1; j < permutation.length; j++) {
//                if (permutation[i] > permutation[j]) {
//                    count++;
//                }
//            }
//        }
//        System.out.println(count);
//        return count;
//    }

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
        if (!isSolvable()) {
            return null;
        }

        List<Board> solution = new LinkedList<>();

        Node lastNode = finalNode;
        while(lastNode.parent != null) {
            solution.add(0, lastNode.board);
            lastNode = lastNode.parent;
        }
        solution.add(0, lastNode.board);

        return solution;
    }

    private static class Node implements Comparable<Node> {
        final Board board;
        Node parent = null;
        int priority = -1;
        int moves = -1;
        int heuristic;

        Node(Board board, int heuristic) {
            this.board = board;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(priority, other.priority);
        }

        @Override
        public boolean equals(Object y) {
            if (y == null) {
                return false;
            }
            if (y == this) {
                return true;
            }
            if (!(y instanceof Node)) {
                return false;
            }

            Node other = (Node) y;

            return board.equals(other.board);
        }

//        @Override
//        public final int hashCode() {
//            return board.toString().hashCode();
//        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}