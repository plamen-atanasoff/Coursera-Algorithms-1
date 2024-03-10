import eight.puzzle.Board;
import eight.puzzle.Solver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolverTest {
    @Test
    void testIsSolvableCase1() {
        int[][] tiles2 = {{0,2,3},{4,1,6},{7,8,5}};
        int[][] tiles3 = {{8,6,7},{2,5,4},{3,0,1}};
        Board boardSolvable1 = new Board(tiles3);

        Solver solver = new Solver(boardSolvable1);

        Iterable<Board> solution = solver.solution();
        assert solution != null;
        for (Board board : solution) {
            System.out.println(board);
        }

        System.out.println(solver.moves());

        assertTrue(solver.isSolvable());
    }

    @Test
    void testIsSolvableCase2() {
        int[][] tiles5 = {{1,2,3,4},{5,6,0,8},{9,11,7,12},{13,14,15,10}};
        int[][] tiles6 = {{15,14,8,12},{10,11,9,13},{2,6,5,1},{3,7,4,0}};
        Board boardSolvable3 = new Board(tiles6);

        Solver solver = new Solver(boardSolvable3);

        assertTrue(solver.isSolvable());
    }

    @Test
    void testIsSolvableCase3() {
        int[][] tiles6 = {{1,0,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,2}};
        Board boardSolvable4 = new Board(tiles6);

        Solver solver = new Solver(boardSolvable4);

        assertTrue(solver.isSolvable());
    }

//    @Test
//    void testIsUnsolvableCase1() {
//        int[][] tiles1 = {{1,2,3},{4,0,6},{7,8,5}};
//        eight.puzzle.Board boardUnsolvable1 = new eight.puzzle.Board(tiles1);
//
//        eight.puzzle.Solver solver = new eight.puzzle.Solver(boardUnsolvable1);
//
//        assertFalse(solver.isSolvable());
//    }

//    @Test
//    void testIsUnsolvableCase2() {
//        int[][] tiles3 = {{1,2,3,4},{5,6,7,8},{0,10,11,12},{13,14,15,9}};
//        eight.puzzle.Board boardUnsolvable2 = new eight.puzzle.Board(tiles3);
//
//        eight.puzzle.Solver solver = new eight.puzzle.Solver(boardUnsolvable2);
//
//        assertFalse(solver.isSolvable());
//    }

//    @Test
//    void testIsUnsolvableCase3() {
//        int[][] tiles4 = {{1,2,3,4},{0,6,7,8},{5,10,11,12},{13,14,15,9}};
//        eight.puzzle.Board boardUnsolvable3 = new eight.puzzle.Board(tiles4);
//
//        eight.puzzle.Solver solver = new eight.puzzle.Solver(boardUnsolvable3);
//
//        assertFalse(solver.isSolvable());
//    }

//    @Test
//    void testSolutionBehavesCorrectly() {
//        int[][] tiles5 = {{1,2,3,4},{5,6,0,8},{9,11,7,12},{13,14,15,10}};
//        eight.puzzle.Board boardSolvable3 = new eight.puzzle.Board(tiles5);
//
//        eight.puzzle.Solver res = new eight.puzzle.Solver(boardSolvable3);
//        Iterable<eight.puzzle.Board> solution = res.solution();
//
//        assert solution != null;
//        for (eight.puzzle.Board board : solution) {
//            System.out.print(board);
//        }
//    }
}
