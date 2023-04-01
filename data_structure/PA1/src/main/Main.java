public class Main {
    public static void main(String[] args) {
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

        IBoardSolver solver = new BoardSolver();
        IBoard solved = solver.solve(board);

        showBoard(solved);
    }

    static void showBoard(IBoard board) {
        if (board == null) { System.out.println("No answer!"); return; }
        for (int r = 0; r < board.getHeight(); r++) {
            for (int c = 0; c < board.getWidth(); c++) {
                if (board.getCellState(r, c) == CellState.FILLED) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }
}
