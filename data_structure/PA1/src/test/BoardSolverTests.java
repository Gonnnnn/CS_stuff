import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

public class BoardSolverTests {
    @Test
    @Score(1)
    public void testSolver1() {
        IBoard board = new Board(2, 2, 
            new int[][] {
                { },
                { }
            },
            new int[][] {
                { 1 },
                { }
            });
        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            IBoardSolver solver = new BoardSolver();
            IBoard solved = null;
            solved = solver.solve(board);
            assertThat(solved, is(nullValue()));
        });
    }

    @Test
    @Score(1)
    public void testSolver2() {
        IBoard board = new Board(2, 2, 
            new int[][] {
                { 1 },
                { 1 },
            },
            new int[][] {
                { 1 },
                { 1 },
            });

        CellState[][] answer1 = new CellState[][] {
            { CellState.EMPTY , CellState.FILLED },
            { CellState.FILLED , CellState.EMPTY },
        };

        CellState[][] answer2 = new CellState[][] {
            { CellState.FILLED , CellState.EMPTY },
            { CellState.EMPTY , CellState.FILLED },
        };
        IBoardSolver solver = new BoardSolver();

        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            IBoard solved;
            solved = solver.solve(board);

            boolean isAnswer1;
            boolean isAnswer2;

            isAnswer1 = true;
            for (int r = 0; r < board.getHeight(); r++) {
                for (int c = 0; c < board.getWidth(); c++) {
                    if (solved.getCellState(r, c) != answer1[r][c]) { isAnswer1 = false; break; }
                }
            }

            isAnswer2 = true;
            for (int r = 0; r < board.getHeight(); r++) {
                for (int c = 0; c < board.getWidth(); c++) {
                    if (solved.getCellState(r, c) != answer2[r][c]) { isAnswer2 = false; break; }
                }
            }

            assertThat(isAnswer1 || isAnswer2, is(true));
        });
    }

    @Test
    @Score(1)
    public void testSolver3() {
        IBoard board = new Board(7, 7, 
            new int[][] {
                {},
                { 2 },
                { 1, 1 },
                { 1, 1 },
                { 1, 1 },
                { 2 },
                {}
            },
            new int[][] {
                {},
                { 1, 1 },
                { 1, 1, 1 },
                { 1, 1 },
                { 1, 1 },
                { 1 },
                {},
            });

        CellState[][] answer = new CellState[][] {
            { CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY },
            { CellState.EMPTY , CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.EMPTY },
            { CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.FILLED, CellState.EMPTY },
            { CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.FILLED, CellState.EMPTY },
            { CellState.EMPTY , CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.EMPTY },
            { CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.FILLED, CellState.EMPTY , CellState.EMPTY , CellState.EMPTY },
            { CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY , CellState.EMPTY },
        };
        IBoardSolver solver = new BoardSolver();

        assertTimeoutPreemptively(Duration.ofSeconds(5), () -> {
            IBoard solved;
            solved = solver.solve(board);
            for (int r = 0; r < board.getHeight(); r++) {
                for (int c = 0; c < board.getWidth(); c++) {
                    assertThat(solved.getCellState(r, c), is(answer[r][c]));
                }
            }
        });
    }
}
