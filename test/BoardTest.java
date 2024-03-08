import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    @Test
    void testBoardGivesExpectedHammingForSolvedPuzzle() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);

        assertEquals(0, board.hamming());
    }

    @Test
    void testBoardGivesExpectedHammingForUnsolvedPuzzle() {
        int[][] tiles = {{2, 3, 1}, {4, 5, 6}, {0, 7, 8}};
        Board board = new Board(tiles);

        assertEquals(6, board.hamming());
    }

    @Test
    void testBoardGivesExpectedManhattanForSolvedPuzzle() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);

        assertEquals(0, board.manhattan());
    }

    @Test
    void testBoardGivesExpectedManhattanForUnsolvedPuzzle() {
        int[][] tiles = {{2, 3, 1}, {4, 5, 6}, {0, 7, 8}};
        Board board = new Board(tiles);

        assertEquals(8, board.manhattan());
    }

    @Test
    void testIsGoalReturnsCorrectlyForSolvedPuzzle() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(tiles);

        assertTrue(board.isGoal());
    }

    @Test
    void testIsGoalReturnsCorrectlyForUnsolvedPuzzle() {
        int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        Board board = new Board(tiles);

        assertFalse(board.isGoal());
    }

    @Test
    void testNeighboursReturnsCorrectIterable2Neighbours() {
        int[][] tiles = {{0, 2, 3}, {4, 5, 6}, {7, 8, 1}};
        Board board = new Board(tiles);
        int[][] tiles1 = {{2, 0, 3}, {4, 5, 6}, {7, 8, 1}};
        Board neighbour1 = new Board(tiles1);
        int[][] tiles2 = {{4, 2, 3}, {0, 5, 6}, {7, 8, 1}};
        Board neighbour2 = new Board(tiles2);

        Board[] neighboursExpected = {neighbour1, neighbour2};

        Iterator<Board> neighbours = board.neighbors().iterator();

        int ctr = 0;
        while (neighbours.hasNext()) {
            Board cur = neighbours.next();
            assertTrue(Arrays.asList(neighboursExpected).contains(cur));
            ctr++;
        }

        assertEquals(2, ctr);
    }

    @Test
    void testNeighboursReturnsCorrectIterable3Neighbours() {
        int[][] tiles = {{1, 0, 3}, {4, 5, 6}, {7, 8, 2}};
        Board board = new Board(tiles);
        int[][] tiles1 = {{0, 1, 3}, {4, 5, 6}, {7, 8, 2}};
        Board neighbour1 = new Board(tiles1);
        int[][] tiles2 = {{1, 3, 0}, {4, 5, 6}, {7, 8, 2}};
        Board neighbour2 = new Board(tiles2);
        int[][] tiles3 = {{1, 5, 3}, {4, 0, 6}, {7, 8, 2}};
        Board neighbour3 = new Board(tiles3);

        Board[] neighboursExpected = {neighbour1, neighbour2, neighbour3};

        Iterator<Board> neighbours = board.neighbors().iterator();

        int ctr = 0;
        while (neighbours.hasNext()) {
            Board cur = neighbours.next();
            assertTrue(Arrays.asList(neighboursExpected).contains(cur));
            ctr++;
        }

        assertEquals(3, ctr);
    }

    @Test
    void testNeighboursReturnsCorrectIterable4Neighbours() {
        int[][] tiles = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
        Board board = new Board(tiles);
        int[][] tiles1 = {{1, 0, 3}, {4, 2, 6}, {7, 8, 5}};
        Board neighbour1 = new Board(tiles1);
        int[][] tiles2 = {{1, 2, 3}, {4, 6, 0}, {7, 8, 5}};
        Board neighbour2 = new Board(tiles2);
        int[][] tiles3 = {{1, 2, 3}, {4, 8, 6}, {7, 0, 5}};
        Board neighbour3 = new Board(tiles3);
        int[][] tiles4 = {{1, 2, 3}, {0, 4, 6}, {7, 8, 5}};
        Board neighbour4 = new Board(tiles4);

        Board[] neighboursExpected = {neighbour1, neighbour2, neighbour3, neighbour4};

        Iterator<Board> neighbours = board.neighbors().iterator();

        int ctr = 0;
        while (neighbours.hasNext()) {
            Board cur = neighbours.next();
            assertTrue(Arrays.asList(neighboursExpected).contains(cur));
            ctr++;
        }

        assertEquals(4, ctr);
    }
}
