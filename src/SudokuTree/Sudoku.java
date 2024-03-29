package SudokuTree;

public class Sudoku {

    public int[][] board;
    public int size;

    public Sudoku(int[][] board, int size) {
        this.board = new int[size][size];
        this.size = size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public void display() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                // system.out.print(" " + board[i - 1][j - 1]);
                if (j % 3 == 0 && j != size) {
                    // system.out.print(" |");
                }
            }

            // system.out.println();
            if (i % 3 == 0 && i != size) {
                // system.out.println(" ---------------------");
            }
        }

        // system.out.println();
        // system.out.println();
        // system.out.println();
    }

    public boolean isValid() {
        return SudokuChecker.valid_board(this.board);
    }

}
